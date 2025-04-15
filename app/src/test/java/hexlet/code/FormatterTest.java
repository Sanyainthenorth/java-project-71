package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormatterTest {

    private final Map<String, Map<String, Object>> testDiff = new HashMap<>();

    @Test
    void testStylishFormatAdded() {
        testDiff.put("key", Map.of("status", "added", "newValue", "value"));
        String expected = "{\n  + key: value\n}";
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testStylishFormatRemoved() {
        testDiff.put("key", Map.of("status", "removed", "oldValue", "value"));
        String expected = "{\n  - key: value\n}";
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testStylishFormatChanged() {
        testDiff.put(
            "key", Map.of(
                "status", "changed",
                "oldValue", "old",
                "newValue", "new"
            )
        );
        String expected = "{\n  - key: old\n  + key: new\n}";
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatAdded() {
        testDiff.put("key", Map.of("status", "added", "newValue", "value"));
        String expected = "Property 'key' was added with value: value";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatRemoved() {
        testDiff.put("key", Map.of("status", "removed"));
        String expected = "Property 'key' was removed";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatChanged() {
        testDiff.put(
            "key", Map.of(
                "status", "changed",
                "oldValue", "old",
                "newValue", "new"
            )
        );
        String expected = "Property 'key' was updated. From old to new";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatUnchanged() {
        testDiff.put("key", Map.of("status", "unchanged", "oldValue", "value"));
        String expected = "";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testUnknownFormat() {
        testDiff.put("key", Map.of("status", "added", "newValue", "value"));
        assertThrows(
            IllegalArgumentException.class, () -> {
                Formatter.getFormatter("unknown", testDiff);
            }
        );
    }
}