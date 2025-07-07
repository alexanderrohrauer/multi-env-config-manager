package org.example.utils;

import io.fabric8.kubernetes.api.model.HasMetadata;
import jakarta.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class AnnotationUtil {
    public static final String DOMAIN = "mcfg.io";
    public static final String BASE = DOMAIN+"/base";
    public static final String FILE_MODE = DOMAIN+"/file-mode";

    public static boolean hasAnnotation(HasMetadata resource, String annotation) {
        return resource.getMetadata().getAnnotations().containsKey(annotation);
    }

    public static @Nullable String getAnnotationValue(HasMetadata resource, String annotation) {
        return resource.getMetadata().getAnnotations().get(annotation);
    }

    public static Map<String, String> removeAll(HasMetadata resource) {
        var res = new HashMap<String, String>();
        for(var entry : resource.getMetadata().getAnnotations().entrySet()) {
            if(!entry.getKey().startsWith(DOMAIN+"/")) {
                res.put(entry.getKey(), entry.getValue());
            }
        }
        return res;
    }
}
