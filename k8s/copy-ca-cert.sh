kubectl get secret root-secret -n cert-manager -o yaml \
| sed "s/namespace: cert-manager/namespace: production/" \
| kubectl apply -n production -f -
