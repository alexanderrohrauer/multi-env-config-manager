helm repo add csi-driver-smb https://raw.githubusercontent.com/kubernetes-csi/csi-driver-smb/master/charts ;
helm install csi-driver-smb csi-driver-smb/csi-driver-smb --namespace kube-system --version v1.17.0
