package org.example.mergers;

import io.fabric8.kubernetes.api.model.HasMetadata;

import java.util.Map;

public class SimpleMerger extends Merger {
    protected SimpleMerger(HasMetadata resource) {
        super(resource);
    }

    @Override
    public Map<String, String> merge(Map<String, String> base, Map<String, String> overlay) {
//        TODO implement merger
        return Map.of("affectedMerger", "simple");
    }
}
