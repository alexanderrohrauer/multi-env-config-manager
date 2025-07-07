package org.example;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.example.handlers.ConfigMapMergeHandler;
import org.example.handlers.SecretMergeHandler;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WatcherBean {
    private static final Logger LOGGER = Logger.getLogger(WatcherBean.class);

    @Inject
    KubernetesClient kubernetesClient;

    @Inject
    ConfigMapMergeHandler configMapMergeHandler;

    @Inject
    SecretMergeHandler secretMergeHandler;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Watcher started");
        kubernetesClient.configMaps().watch(configMapMergeHandler);
        LOGGER.info("Watching on ConfigMaps");

        kubernetesClient.secrets().watch(secretMergeHandler);
        LOGGER.info("Watching on Secrets");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("Watcher stopped");
    }
}
