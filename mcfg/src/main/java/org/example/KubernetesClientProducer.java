package org.example;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import jakarta.inject.Singleton;
import jakarta.enterprise.inject.Produces;

@Singleton
public class KubernetesClientProducer {
    @Produces
    public KubernetesClient kubernetesClient() {
        // here you would create a custom client
        return new DefaultKubernetesClient();
    }
}

