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
        try {
            kubernetesClient.configMaps().inAnyNamespace().list().getItems();
        } catch (Exception e) {
            LOGGER.info("Cannot list ConfigMaps in any namespace");
        }
        try {
            kubernetesClient.configMaps().inNamespace("kube-system").list().getItems();
        } catch (Exception e) {
            LOGGER.info("Cannot list ConfigMaps in kube-system");
        }
        try {
            kubernetesClient.configMaps().inNamespace("test").list().getItems();
            LOGGER.info("Can list ConfigMaps in test");
        } catch (Exception e) {
            LOGGER.info("Cannot list ConfigMaps in test");
        }
    }
}
