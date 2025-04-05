# Multi env config manager

## Installation of kubectl
1. Install kubectl (https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/)
2. After installed replace the kube-config-file somewhere in your homefolder (mostly located under `.kube`) with the file `config` from the GoogleDrive folder `kube_access` 
3. Create SSH tunnel (see below and please DISCONNECT after usage)
4. Now you can access kubernetes cluster with the `kubectl` command
5. Alternatively you can use the kubernetes plugin for Intellij or the IDE "Lens" (https://k8slens.dev)

## SSH tunnel for Kubernetes
`ssh lab04.ce.uni-linz.ac.at -L 6443:localhost:6443 -N`
