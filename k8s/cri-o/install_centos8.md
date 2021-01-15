
# 使用kubeadm在Centos8上部署kubernetes1.20

Centos8系统发布已有一段时间，不少小伙伴开始上手使用。kubernetes1.20也发布了，今天作者使用kubeadm在Centos8系统上部署kubernetes，cri使用crio

## 1.系统准备

查看系统版本

```sh
cat /etc/centos-release
```

配置网络

```sh
cat /etc/sysconfig/network-scripts/ifcfg-ens33
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=039303a5-c70d-4973-8c91-97eaa071c23d
DEVICE=ens33
ONBOOT=yes
IPADDR=192.168.132.150
NETMASK=255.255.255.0
GATEWAY=192.168.132.1
DNS1=192.168.0.201
```

添加阿里源

```sh
rm -rfv /etc/yum.repos.d/*
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-8.repo
```

配置主机名

```sh
cat /etc/hosts
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6
192.168.132.140 crio-k8s-master
```

关闭防火墙并设置iptables

```sh
systemctl stop firewalld
systemctl disable firewalld

yum install -y iptables-services
systemctl start iptables
systemctl enable iptables
iptables -F
service iptable save
```

关闭swap，注释swap分区

```sh
swapoff -a && sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab
setenforce 0 && sed -i 's/^SELINUX=.*/SELINUX=disabled' /etc/selinux/config
cat /etc/fstab
#/dev/mapper/cl-swap     swap                    swap    defaults        0 0
```

配置内核参数，将桥接的IPv4流量传递到iptables的链

```sh
sudo modprobe overlay
sudo modprobe br_netfilter

# fix kubeadm init issue: [ERROR FileContent--proc-sys-net-bridge-bridge-nf-call-iptables]
echo 1 > /proc/sys/net/bridge/bridge-nf-call-iptables

# 设置必需的 sysctl 参数，这些参数在重新启动后仍然存在。
cat <<EOF | sudo tee /etc/sysctl.d/99-kubernetes-cri.conf
net.bridge.bridge-nf-call-iptables  = 1
net.ipv4.ip_forward                 = 1
net.bridge.bridge-nf-call-ip6tables = 1
EOF

sysctl --system
```

## 2.安装常用包

```sh
yum install bash-completion net-tools gcc -y
```

## 3.使用源安装cri-o

```sh
export OS=CentOS_8
export VERSION=1.18
curl -L -o /etc/yum.repos.d/devel:kubic:libcontainers:stable.repo https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/$OS/devel:kubic:libcontainers:stable.repo
curl -L -o /etc/yum.repos.d/devel:kubic:libcontainers:stable:cri-o:$VERSION.repo https://download.opensuse.org/repositories/devel:kubic:libcontainers:stable:cri-o:$VERSION/$OS/devel:kubic:libcontainers:stable:cri-o:$VERSION.repo

#此步骤可能会比较久，请使用科学上网
yum install cri-o -y
```

添加aliyundocker仓库加速器

```sh
mkdir -p /etc/containers
tee /etc/containers/registries.conf <<-'EOF'
[[registry-mirrors]]
location = "https://fl791z1h.mirror.aliyuncs.com"
EOF

systemctl daemon-reload
systemctl enable crio
systemctl start crio
systemctl status crio
```

## 4.配置crio

添加cni配置

```sh
cat <<EOF | sudo tee /etc/cni/net.d/10-crio-bridge.conf
{
    "cniVersion": "0.3.1",
    "name": "crio",
    "type": "bridge",
    "bridge": "cni0",
    "isGateway": true,
    "ipMasq": true,
    "hairpinMode": true,
    "ipam": {
        "type": "host-local",
        "routes": [
            { "dst": "0.0.0.0/0" },
            { "dst": "1100:200::1/24" }
        ],
        "ranges": [
            [{ "subnet": "10.85.0.0/16" }],
            [{ "subnet": "1100:200::/24" }]
        ]
    }
}
EOF
```

修改crio默认配置

```sh
vi /etc/crio/config.config
```

- 修改 pause_image 为: registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2
重启服务

```sh
systemctl restart crio
systemctl status crio
```

配置crictl

```sh
cat <<EOF | sudo tee /etc/crictl.yaml
runtime-endpoint: unix:///var/run/crio/crio.sock
image-endpoint: unix:///var/run/crio/crio.sock
timeout: 10
debug: false
EOF
```

## 5.安装kubectl、kubelet、kubeadm

添加阿里kubernetes源

```sh
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

yum install kubectl kubelet kubeadm -y
systemctl enable kubelet
systemctl start kubelet
systemctl status kubelet
```

## 6.初始化k8s集群

```sh
kubeadm config print init-defaults > kubeadm-config.yaml

kubeadm init --config=kubeadm-config.yaml --v=5 | tee kubeadm-init.log
```

Node节点加入集群, 找到文件kubeadm-init.log中的 `kubeadm join`

```sh
kubeadm join 192.168.132.150:6443 --token abcdef.0123456789abcdef   --discovery-token-ca-cert-hash sha256:9b45489fc2716e317b889e8d0edcbe1c56dfc9478b441cb3f0c66d79ae802c41  --v=5
```

如果token过期，重新生成token

```sh
kubeadm token create
kubeadm token list
```

根据提示创建kubectl

```sh
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```

执行下面命令，使kubectl可以自动补充

```sh
source <(kubectl completion bash)
```

## 7.安装calico网络

```sh
kubectl apply -f calico.yaml
```

查看pod和node是否正常

```sh
kubectl get pod --all-namespaces
```
