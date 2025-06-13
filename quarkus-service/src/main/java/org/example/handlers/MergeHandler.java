package org.example.handlers;

import com.openai.client.OpenAIClient;
import io.fabric8.kubernetes.api.builder.Builder;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import org.example.mergers.Merger;
import org.example.utils.AIMerger;
import org.example.utils.AnnotationUtil;
import org.jboss.logging.Logger;

import java.util.Map;

import static org.example.utils.AnnotationUtil.getAnnotationValue;
import static org.example.utils.AnnotationUtil.hasAnnotation;

public abstract class MergeHandler<T extends Editable<B> & HasMetadata, B extends Builder<T>> implements Watcher<T> {
    private static final Logger LOGGER = Logger.getLogger(MergeHandler.class);

    @Inject
    AIMerger aiMerger;

    public void handle(T resource) {
        if (hasAnnotation(resource, AnnotationUtil.BASE)) {
            var baseAnnotationValue = getAnnotationValue(resource, AnnotationUtil.BASE);
            var overlayFqdn = resource.getMetadata().getNamespace() + "/" + resource.getMetadata().getName();

            if(!baseAnnotationValue.isBlank()) {

                var base = resolveBase(baseAnnotationValue, resource.getMetadata().getNamespace());

                if (base != null) {
                    var baseFqdn = base.getMetadata().getNamespace() + "/" + base.getMetadata().getName();
                    LOGGER.info("Merging " + baseFqdn + " with " + overlayFqdn);

                    var mergedData = mergeContent(base, resource);

                    update(resource, mergedData);
                } else {
                    LOGGER.error("Cannot find base resource with FQDN " + baseAnnotationValue);
                }
            } else {
                LOGGER.error("Please provide the FQDN of the base resource in the annotation "+AnnotationUtil.BASE +" of resource "+overlayFqdn);
            }
        }
    }

    protected abstract Map<String, String> extractData(T resource);

    protected abstract @Nullable T resolve(String namespace, String name);

    protected abstract void update(T overlay, Map<String, String> mergedData);

    private Map<String, String> mergeContent(T base, T overlay) {
        var baseData = extractData(base);
        var overlayData = extractData(overlay);

        var merger = Merger.forResource(overlay, aiMerger);

        return merger.merge(baseData, overlayData);
    }

    private T resolveBase(String fqdn, String defaultNamespace) {
        var split = fqdn.split("/");
        if (split.length == 2) {
            return resolve(split[0], split[1]);
        } else if(split.length == 1) {
            return resolve(defaultNamespace, split[0]);
        }
        return null;
    }

    @Override
    public void eventReceived(Action action, T resource) {
        if(action == Action.ADDED || action == Action.MODIFIED) {
            handle(resource);
        }
    }

    @Override
    public void onClose(WatcherException e) {
        LOGGER.error(e);
    }
}
