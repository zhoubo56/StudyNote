
# Opaque

base64 encoding to store secret, crt ...

## HOW TO CREATE

```sh
$ echo -n 'admin' | base64
YWRtaW4=
$ echo -n '1qaz2wsx' | base64
MXFhejJ3c3g=
```

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: mysecret
type: Opaque
data:
  username: YWRtaW4=
  password: MXFhejJ3c3g=
```

## HOW TO USE

volumes

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: secret-volumes-pod
spec:
  containers:
    - name: secret-volumes-container
      image: registry.cn-hangzhou.aliyuncs.com/zhoubo56/busybox:v1
      command: ['sh', '-c', 'cat /etc/secrets/username && sleep 600']
      volumeMounts:
        - name: mysecret
          mountPath: /etc/secrets
          readOnly: true
      imagePullPolicy: IfNotPresents
  volumes:
    - name: mysecret
      secret:
        secretName: mysecret
  imagePullSecrets:
    - name: ali-zhouboV
```

env

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: secret-env-pod
spec:
  containers:
    - name: secret-env-container
      image: registry.cn-hangzhou.aliyuncs.com/zhoubo56/busybox:v1
      command: ['sh', '-c', 'env']
      envFrom:
        - secretRef:
            name: mysecret
      imagePullPolicy: IfNotPresent
  restartPolicy: Never
  imagePullSecrets:
    - name: ali-zhoubo
```
