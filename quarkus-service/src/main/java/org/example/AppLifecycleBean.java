package org.example;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AppLifecycleBean {
    private static final Logger LOGGER = Logger.getLogger(AppLifecycleBean.class);

    @Inject
    KubernetesClient kubernetesClient;

    void onStart(@Observes StartupEvent ev) {
        var configMaps = kubernetesClient.configMaps().inAnyNamespace().list().getItems();
        LOGGER.debug(configMaps);
    }
}
