package org.example;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;

@Singleton
public class KubernetesClient {
    @Produces
    public KubernetesClient KubernetesClient() {
        // here you would create a custom client
        return new KubernetesClient();
    }
}
