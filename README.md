# Multi env config manager

This tool can be used to merge multiple config maps when used in multiple environments.

# Installation
1. Create namespace `kubectl create namespace multi-env-config-manager`
2. If using OpenAI, create the API-Key with the `Model capabilities` permission set
3. Create the secret from the template: https://github.com/alexanderrohrauer/multi-env-config-manager/blob/main/releases/v1.0-SNAPSHOT/mcfg-secret.example.yaml
4. Optional: Create configuration from the template: https://github.com/alexanderrohrauer/multi-env-config-manager/blob/main/releases/v1.0-SNAPSHOT/mcfg-config.example.yaml
5. Deploy the tool: `kubectl apply -f https://raw.githubusercontent.com/alexanderrohrauer/multi-env-config-manager/refs/heads/main/releases/v1.0-SNAPSHOT/mcfg.yml`

# Usage
1. Create your Base-Config-Map and your Overlay-Config-Map in a Kubernetes YAML manifest (declarative)
2. Add the annotation `"mcfg.io/base": "<base_config_namespace>/<base_config_name>"` to the overlay config
3. If the keys in the config-maps are config-files, apply the Kubernetes annotation `"mcfg.io/file-mode": true`
4. Deploy both Config-Maps. The Overlay-Config-Map should show have the merged content on the cluster.
