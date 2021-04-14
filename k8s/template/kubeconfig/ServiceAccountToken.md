
# 基于ServiceAccount Token的kubeconfig

## 前提

* 创建或确定要使用的ServiceAccount，并获取其Token
* 确认Kubernetes API Server的访问入口
* 获取Kubernetes CA的证书

## 配置过程

### 步骤一，创建SA，并获取其Token

```sh
kubectl create serviceaccount developer001

DEV_SECRET=$(kubectl get secret | awk '/^developer001-token/{print $1}')

DEV_TOKEN=$(kubectl get secret $DEV_SECRET -o jsonpath={.data.token} | base64 -d)
```

### 步骤二，创建kubeconfig

```sh
kubectl config set-cluster kubernetes --embed-certs=true --certificate-authority=/etc/kubernetes/pki/ca.crt --server="https://serverhost:6443" --kubeconfig=developer001.config

kubectl config set-credentials developer001 --token=$DEV_TOKEN --kubeconfig=developer001.config

kubectl config set-context developer001@kubernetes --cluster=kubernetes --user=developer001 --kubeconfig=developer001.config

kubectl config use-context developer001@kubernetes --kubeconfig=developer001.config
```
