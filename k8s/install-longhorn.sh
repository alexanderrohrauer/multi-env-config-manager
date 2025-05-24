kubectl apply -f https://raw.githubusercontent.com/longhorn/longhorn/v1.8.1/deploy/prerequisite/longhorn-iscsi-installation.yaml ;
kubectl apply -f https://raw.githubusercontent.com/longhorn/longhorn/v1.8.1/deploy/prerequisite/longhorn-nfs-installation.yaml ;
helm repo add longhorn https://charts.longhorn.io ;
helm repo update ;
helm install longhorn longhorn/longhorn --namespace longhorn-system --create-namespace --version 1.8.1 ;
