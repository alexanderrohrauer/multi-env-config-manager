package org.example;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ConfigMapWatcher implements Watcher<ConfigMap> {
    private static final Logger LOGGER = Logger.getLogger(ConfigMapWatcher.class);

    @Override
    public void eventReceived(Action action, ConfigMap configMap) {
        if(action == Action.ADDED) {
            LOGGER.infof("ConfigMap '%s' added", configMap.getMetadata().getName());
        }

        if(action == Action.MODIFIED) {
            LOGGER.infof("ConfigMap '%s' updated", configMap.getFullResourceName());
        }
    }

    @Override
    public void onClose(WatcherException e) {

    }
}
