# Multi env config manager

## Installation of kubectl
1. Install `kubectl` CLI client for Kubernetes (https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/)
2. After installed replace the kube-config-file somewhere in your PC's home-folder (mostly located under `$HOME/.kube`) with the file `config` from the GoogleDrive folder `kube_access` 
3. Now you can access kubernetes cluster with the `kubectl` command
4. Alternatively you can use a GUI: the Kubernetes plugin for Intellij or the IDE "Lens" (https://k8slens.dev)

## Usage of web-services
1. Import the ca certificate (`ca.crt`) in GoogleDrive `kube_access` into your browser's trust chain (or operating-system trust chain - depends on your used browser)
2. Access webservice in browser at `https://lab04.ce.uni-linz.ac.at/`
