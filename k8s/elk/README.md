
# Kubernetes Observability: Log Aggregation Using ELK Stack

ref: <https://www.magalix.com/blog/kubernetes-observability-log-aggregation-using-elk-stack>

STEPS to run:

```sh
kubectl apply -f rbac.yaml
kubectl apply -f es-statefulst.yaml
kubectl apply -f es-svc.yaml
kubectl apply -f logstash-config.yaml
kubectl apply -f logstash-deployment.yaml
kubectl apply -f logstash-service.yaml
kubectl apply -f filebeat.yaml
kubectl apply -f kibana.yaml
```
