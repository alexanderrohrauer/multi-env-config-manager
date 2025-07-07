import io.fabric8.kubernetes.api.model.HasMetadata;
import org.example.mergers.FileModeMerger;
import org.example.utils.AIMerger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileModeMergerUnitTest {

    private HasMetadata mockResource;
    private AIMerger mockAIMerger;
    private FileModeMerger fileModeMerger;

    @BeforeEach
    void setup() {
        mockResource = mock(HasMetadata.class);
        mockAIMerger = mock(AIMerger.class);
        fileModeMerger = new FileModeMerger(mockResource, mockAIMerger);
    }

    @Test
    void testMerge_mergeMatchingKeysWithExtension() {
        //create base and overlay map
        Map<String, String> base = new HashMap<>();
        base.put("test.yaml", "base-content");

        Map<String, String> overlay = new HashMap<>();
        overlay.put("test.yaml", "overlay-content");

        when(mockAIMerger.merge("base-content", "overlay-content", "yaml"))
                .thenReturn("merged-content");

        Map<String, String> result = fileModeMerger.merge(base, overlay);

        assertEquals("merged-content", result.get("test.yaml"));

        //Ensure that AiMerger has been executed
        verify(mockAIMerger).merge("base-content", "overlay-content", "yaml");
    }

    @Test
    void testMerge_ignoreKeyWithoutDot() {
        //Map and overlay without a dot (<name>.<ext>)
        Map<String, String> base = new HashMap<>();
        base.put("test", "base");

        Map<String, String> overlay = new HashMap<>();
        overlay.put("test", "overlay");

        Map<String, String> result = fileModeMerger.merge(base, overlay);
        assertEquals("base", result.get("test"));
        //Ensure that AiMerger is not executed
        verify(mockAIMerger, never()).merge(any(), any(), any());
    }

    @Test
    void testMerge_ignoreKeyMissingInBase() {
        Map<String, String> base = new HashMap<>();
        base.put("only-in-base.yaml", "base");

        Map<String, String> overlay = new HashMap<>();
        overlay.put("only-in-overlay.yaml", "overlay");

        Map<String, String> result = fileModeMerger.merge(base, overlay);

        // Only base-key should exist
        assertEquals("base", result.get("only-in-base.yaml"));
        assertNull(result.get("only-in-overlay.yaml")); // not added
        verify(mockAIMerger, never()).merge(any(), any(), any());
    }

    @Test
    void testMerge_multipleValidAndInvalidKeys() {
        Map<String, String> base = new HashMap<>();
        base.put("a.yaml", "baseA");
        base.put("b.txt", "baseB");

        Map<String, String> overlay = Map.of(
                "a.yaml", "overlayA",
                "b.txt", "overlayB",
                "invalid", "ignored",
                "missing.json", "ignored"
        );

        when(mockAIMerger.merge("baseA", "overlayA", "yaml"))
                .thenReturn("mergedA");
        when(mockAIMerger.merge("baseB", "overlayB", "txt"))
                .thenReturn("mergedB");

        Map<String, String> result = fileModeMerger.merge(base, overlay);

        assertEquals("mergedA", result.get("a.yaml"));
        assertEquals("mergedB", result.get("b.txt"));
        assertNull(result.get("invalid"));
        assertNull(result.get("missing.json"));
        verify(mockAIMerger, times(1)).merge("baseA", "overlayA", "yaml");
        verify(mockAIMerger, times(1)).merge("baseB", "overlayB", "txt");
    }
}
