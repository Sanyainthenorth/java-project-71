package hexlet.code;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Formatter} class.
 * Verifies all supported output formats.
 */
public final class FormatterTest {

    private static final String TEST_KEY = "key";
    private static final String TEST_VALUE = "value";
    private static final String TEST_OLD_VALUE = "old";
    private static final String TEST_NEW_VALUE = "new";
    private static final int TEST_NUMBER = 42;
    private static final boolean TEST_BOOLEAN = true;

    private final Map<String, Map<String, Object>> testDiff = new HashMap<>();

    @Test
    void testStylishFormatAdded() {
        testDiff.put(TEST_KEY, Map.of("status", "added", "newValue", TEST_VALUE));
        String expected = "{\n  + " + TEST_KEY + ": " + TEST_VALUE + "\n}";
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testStylishFormatRemoved() {
        testDiff.put(TEST_KEY, Map.of("status", "removed", "oldValue", TEST_VALUE));
        String expected = "{\n  - " + TEST_KEY + ": " + TEST_VALUE + "\n}";
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testStylishFormatChanged() {
        testDiff.put(TEST_KEY, Map.of(
            "status", "changed",
            "oldValue", TEST_OLD_VALUE,
            "newValue", TEST_NEW_VALUE
        ));
        String expected = String.format(
            "{%n  - %s: %s%n  + %s: %s%n}",
            TEST_KEY, TEST_OLD_VALUE, TEST_KEY, TEST_NEW_VALUE
        );
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatAddedString() {
        testDiff.put(TEST_KEY, Map.of("status", "added", "newValue", TEST_VALUE));
        String expected = "Property '" + TEST_KEY + "' was added with value: '" + TEST_VALUE + "'";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatAddedNumber() {
        testDiff.put(TEST_KEY, Map.of("status", "added", "newValue", TEST_NUMBER));
        String expected = "Property '" + TEST_KEY + "' was added with value: " + TEST_NUMBER;
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatAddedBoolean() {
        testDiff.put(TEST_KEY, Map.of("status", "added", "newValue", TEST_BOOLEAN));
        String expected = "Property '" + TEST_KEY + "' was added with value: " + TEST_BOOLEAN;
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatRemoved() {
        testDiff.put(TEST_KEY, Map.of("status", "removed"));
        String expected = "Property '" + TEST_KEY + "' was removed";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatChanged() {
        testDiff.put(TEST_KEY, Map.of(
            "status", "changed",
            "oldValue", TEST_OLD_VALUE,
            "newValue", TEST_NEW_VALUE
        ));
        String expected = String.format(
            "Property '%s' was updated. From '%s' to '%s'",
            TEST_KEY, TEST_OLD_VALUE, TEST_NEW_VALUE
        );
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testPlainFormatUnchanged() {
        testDiff.put(TEST_KEY, Map.of("status", "unchanged", "oldValue", TEST_VALUE));
        String expected = "";
        String actual = Formatter.getFormatter("plain", testDiff);
        assertEquals(expected, actual);
    }

    @Test
    void testUnknownFormat() {
        testDiff.put(TEST_KEY, Map.of("status", "added", "newValue", TEST_VALUE));
        assertThrows(IllegalArgumentException.class, () -> {
            Formatter.getFormatter("unknown", testDiff);
        });
    }
    @Test
    void testStylishFormatNumberValue() {
        testDiff.put("number", Map.of(
            "status", "added",
            "newValue", TEST_NUMBER  // Используем константу
        ));
        String expected = String.format("{%n  + number: %d%n}", TEST_NUMBER);
        String actual = Formatter.getFormatter("stylish", testDiff);
        assertEquals(expected, actual);
    }
}
