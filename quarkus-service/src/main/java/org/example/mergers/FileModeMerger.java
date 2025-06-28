package org.example.mergers;

import io.fabric8.kubernetes.api.model.HasMetadata;
import org.example.utils.AIMerger;
import org.jboss.logging.Logger;

import java.util.Map;

public class FileModeMerger extends Merger {
    private static final Logger LOGGER = Logger.getLogger(FileModeMerger.class);

    private final AIMerger aiMerger;

    protected FileModeMerger(HasMetadata resource, AIMerger aiMerger) {
        super(resource);
        this.aiMerger = aiMerger;
    }

    @Override
    public Map<String, String> merge(Map<String, String> base, Map<String, String> overlay) {
        for (Map.Entry<String, String> entry : overlay.entrySet()) {
            if (base.containsKey(entry.getKey())) {
                var splitted = entry.getKey().split("\\.");
                if(splitted.length >= 2) {
                    var ext = splitted[splitted.length - 1];
                    var baseContent = base.get(entry.getKey());
                    var overlayContent = entry.getValue();
                    var merged = aiMerger.merge(baseContent, overlayContent, ext);
                    LOGGER.infof("Merged content for key %s:\n%s", entry.getKey(), merged);
                    base.put(entry.getKey(), merged);
                } else {
                    LOGGER.info("Key "+entry.getKey()+" ignored in overlay map since it does not have the form <name>.<ext>");
                }
            } else {
                LOGGER.info("Key "+entry.getKey()+" ignored in overlay map since it has no corresponding entry in the base");
            }
        }
        return base;
    }
}
