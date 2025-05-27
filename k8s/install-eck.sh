helm repo add elastic https://helm.elastic.co ;
helm repo update ;
helm install elastic-operator elastic/eck-operator -n production
