package org.example.mergers;

import io.fabric8.kubernetes.api.model.HasMetadata;

import java.util.Map;

public class FileModeMerger extends Merger {
    protected FileModeMerger(HasMetadata resource) {
        super(resource);
    }

    @Override
    public Map<String, String> merge(Map<String, String> base, Map<String, String> overlay) {
//        TODO implement merger
        return Map.of("affectedMerger", "file");
    }
}
