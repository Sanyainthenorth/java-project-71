package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormatterTest {
    private static final int TEST_AGE = 42;
    private final Map<String, Map<String, Object>> testDiff = new HashMap<>();

    // Stylish Formatter Tests (остаются без изменений)
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
        testDiff.put("key", Map.of(
            "status", "changed",
            "oldValue", "old",
            "newValue", "new"
        ));
        String expected = "{\n  - key: old\n  + key: new\n}";
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }

    // Plain Formatter Tests (обновленные с кавычками)
    @Test
    void testPlainFormatAddedStringValue() {
        testDiff.put("key", Map.of("status", "added", "newValue", "value"));
        String expected = "Property 'key' was added with value: 'value'";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatAddedNumberValue() {
        testDiff.put("age", Map.of("status", "added", "newValue", TEST_AGE));
        String expected = "Property 'age' was added with value: 42";
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
    void testPlainFormatChangedStrings() {
        testDiff.put("key", Map.of(
            "status", "changed",
            "oldValue", "old",
            "newValue", "new"
        ));
        String expected = "Property 'key' was updated. From 'old' to 'new'";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatChangedMixedTypes() {
        testDiff.put("enabled", Map.of(
            "status", "changed",
            "oldValue", false,
            "newValue", "true"
        ));
        String expected = "Property 'enabled' was updated. From false to 'true'";
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
        assertThrows(IllegalArgumentException.class, () -> {
            Formatter.getFormatter("unknown", testDiff);
        });
    }
}
