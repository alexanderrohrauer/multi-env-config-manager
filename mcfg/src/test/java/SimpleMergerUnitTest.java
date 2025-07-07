import io.fabric8.kubernetes.api.model.HasMetadata;
import org.junit.jupiter.api.Test;
import org.example.mergers.SimpleMerger;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimpleMergerUnitTest {
    @Test
    void testMerge_withOverlayOverridesAndAddsKeys(){
        HasMetadata mockResource = mock(HasMetadata.class); // mocking Kubernetes resource
        SimpleMerger merger = new SimpleMerger(mockResource);

        Map<String, String> base = Map.of(
                "key1", "value1",
                "key2", "value2"
        );

        Map<String, String> overlay = Map.of(
                "key2", "overridden",
                "key3", "new"
        );
        Map<String, String> result = merger.merge(base, overlay);
        assertEquals(3, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("overridden", result.get("key2"));
        assertEquals("new", result.get("key3"));
    }
    @Test
    void testMerge_withEmptyOverlay_returnsBase() {
        HasMetadata mockResource = mock(HasMetadata.class);
        SimpleMerger merger = new SimpleMerger(mockResource);

        Map<String, String> base = Map.of("a", "1", "b", "2");
        Map<String, String> overlay = Map.of();

        Map<String, String> result = merger.merge(base, overlay);

        assertEquals(base, result);
    }
    @Test
    void testMerge_withEmptyBase_returnsOverlay() {
        HasMetadata mockResource = mock(HasMetadata.class);
        SimpleMerger merger = new SimpleMerger(mockResource);

        Map<String, String> base = Map.of();
        Map<String, String> overlay = Map.of("x", "42");

        Map<String, String> result = merger.merge(base, overlay);

        assertEquals(1, result.size());
        assertEquals("42", result.get("x"));
    }
}
