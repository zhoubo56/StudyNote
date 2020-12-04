
# Install Flagger on k8s

ref: <https://docs.flagger.app/install/flagger-install-on-kubernetes>

## Install Flagger with Helm

Add Flagger Helm repository:

```sh
helm repo add flagger https://flagger.app
```

Install Flagger's Canary CRD:

```sh
kubectl apply -f crd.yaml
```

Deploy Flagger for Istio:

```sh
helm upgrade -i flagger flagger/flagger \
--namespace=istio-system \
--set crd.create=false \
--set meshProvider=istio \
--set metricsServer=http://prometheus:9090
```

Deploy Grafana in the istio-system namespace:

```sh
helm upgrade -i flagger-grafana flagger/grafana \
--namespace=istio-system \
--set url=http://prometheus.istio-system:9090 \
--set user=admin \
--set password=admin
```

## Uninstall

To uninstall the Flagger release with Helm run:

```sh
helm delete flagger -n=istio-system
```

To uninstall the Grafana with Helm run:

```sh
helm delete flagger-grafana -n=istio-system
```

Uninstall Flagger's Canary CRD:

```sh
kubectl delete -f crd.yaml
```
