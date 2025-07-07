package org.example.mergers;

import io.fabric8.kubernetes.api.model.HasMetadata;
import org.example.utils.AIMerger;
import org.example.utils.AnnotationUtil;

import java.util.Map;

import static org.example.utils.AnnotationUtil.getAnnotationValue;

public abstract class Merger {
    protected final HasMetadata resource;

    public Merger(HasMetadata resource) {
        this.resource = resource;
    }

    public abstract Map<String, String> merge(Map<String, String> base, Map<String, String> overlay);

    public static Merger forResource(HasMetadata resource, AIMerger merger) {
        var rawValue = getAnnotationValue(resource, AnnotationUtil.FILE_MODE);
        var isFileMode = Boolean.parseBoolean(rawValue);

        return isFileMode ? new FileModeMerger(resource, merger) : new SimpleMerger(resource);
    }
}
