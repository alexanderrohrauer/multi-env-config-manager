package org.example.handlers;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.utils.AnnotationUtil;

import java.util.Map;

@ApplicationScoped
public class ConfigMapMergeHandler extends MergeHandler<ConfigMap, ConfigMapBuilder> {
    private final KubernetesClient kubernetesClient;

    public ConfigMapMergeHandler(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @Override
    protected Map<String, String> extractData(ConfigMap resource) {
        return resource.getData();
    }

    @Nullable
    @Override
    protected ConfigMap resolve(String namespace, String name) {
        return kubernetesClient.configMaps().inNamespace(namespace).withName(name).get();
    }

    @Override
    protected void update(ConfigMap overlay, Map<String, String> mergedData) {
        var annotations = AnnotationUtil.removeAll(overlay);

        kubernetesClient.configMaps().resource(overlay).edit(
                cm -> new ConfigMapBuilder(cm)
                        .withData(mergedData)
                        .editMetadata()
                        .withAnnotations(annotations)
                        .endMetadata()
                        .build()
        );
    }
}
