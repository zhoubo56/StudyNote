
# kubernetes.io/dockerconfigjson

## HOW TO CREATE

```sh
kubectl create secret docker-registry myregistrykey \
--docker-server=DOCKER_REGISTRY_SERVER \
--docker-username=DOCKER_USER \
--docker-password=DOCKER_PASSWORD
```

## HOW TO USE

``` yaml
containers:
  ...
imagePullSecrets:
  - name: myregistrykey
```
