package org.example.handlers;

import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.utils.AnnotationUtil;

import java.util.Map;

@ApplicationScoped
public class SecretMergeHandler extends MergeHandler<Secret, SecretBuilder> {
    private final KubernetesClient kubernetesClient;

    public SecretMergeHandler(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @Override
    protected Map<String, String> extractData(Secret resource) {
        return resource.getData();
    }

    @Nullable
    @Override
    protected Secret resolve(String namespace, String name) {
        return kubernetesClient.secrets().inNamespace(namespace).withName(name).get();
    }

    @Override
    protected void update(Secret overlay, Map<String, String> mergedData) {
        var annotations = AnnotationUtil.removeAll(overlay);

        kubernetesClient.secrets().resource(overlay).edit(
                cm -> new SecretBuilder(cm)
                        .withData(mergedData)
                        .editMetadata()
                        .withAnnotations(annotations)
                        .endMetadata()
                        .build()
        );
    }
}
