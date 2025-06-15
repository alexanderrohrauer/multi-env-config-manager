package org.example.mergers;

import io.fabric8.kubernetes.api.model.HasMetadata;

import java.util.HashMap;
import java.util.Map;

public class SimpleMerger extends Merger {
    protected SimpleMerger(HasMetadata resource) {
        super(resource);
    }

    @Override
    public Map<String, String> merge(Map<String, String> base, Map<String, String> overlay) {
        // uses everything from the base
        Map<String, String> result = new HashMap<>(base);
        // overwrite base values with overlay values if keys match.
        // (It can also add key with its values which doesn't exist in the base!)
        result.putAll(overlay);
        return result;
    }
}
