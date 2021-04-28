
# NFS

## WHAT IS NFS

Network File System (NFS) is a distributed file system protocol originally developed by Sun Microsystems (Sun) in 1984,[1] allowing a user on a client computer to access files over a computer network much like local storage is accessed. NFS, like many other protocols, builds on the Open Network Computing Remote Procedure Call (ONC RPC) system. The NFS is an open standard defined in a Request for Comments (RFC), allowing anyone to implement the protocol.

Wiki: <https://en.wikipedia.org/wiki/Network_File_System>

## HOW TO CREATE

```sh
yum install -y nfs-common nfs-utils rpcbind

mkdir /nfsdata{1..4}
# chmod 777 /nfsdata{1..4}
chown -R nfsnobody:nfsnobody /nfsdata{1..4}

echo '/nfsdata1 *(rw,no_root_squash,no_all_squash,sync)' >> /etc/exports
echo '/nfsdata2 *(rw,no_root_squash,no_all_squash,sync)' >> /etc/exports
echo '/nfsdata3 *(rw,no_root_squash,no_all_squash,sync)' >> /etc/exports
echo '/nfsdata4 *(rw,no_root_squash,no_all_squash,sync)' >> /etc/exports

systemctl start rpcbind
systemctl start nfs
```

test in othe linux os

```sh
mkdir /test
mount -t nfs 192.168.132.105:/nfsdata4 /test
date > /test/now
umount /test
rm -rf /test
```

## HOW TO USE

all k8s node need install nfs-utls & rpcbind

```sh
yum install -y nfs-utils rpcbind
```
