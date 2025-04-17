package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hexlet.code.formatters.JsonFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class JsonFormatterTest {
    private static final String TEST_KEY = "testKey";
    private static final String TEST_OLD_VALUE = "oldValue";
    private static final String TEST_NEW_VALUE = "newValue";
    private static final int TEST_NUMBER_VALUE = 42;
    private final Map<String, Map<String, Object>> testDiff = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testDiff.clear();
    }

    @Test
    void testJsonFormatChanged() throws JsonProcessingException {
        testDiff.put(TEST_KEY, Map.of(
            "status", "changed",
            "oldValue", TEST_OLD_VALUE,
            "newValue", TEST_NEW_VALUE
        ));

        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "changed");
        innerNode.put("oldValue", TEST_OLD_VALUE);
        innerNode.put("newValue", TEST_NEW_VALUE);
        expectedNode.set(TEST_KEY, innerNode);

        String actualJson = JsonFormatter.format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }


    @Test
    void testJsonFormatAdded() throws JsonProcessingException {
        testDiff.put(TEST_KEY, Map.of(
            "status", "added",
            "newValue", TEST_NUMBER_VALUE
        ));

        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "added");
        innerNode.put("newValue", TEST_NUMBER_VALUE);
        expectedNode.set(TEST_KEY, innerNode);

        String actualJson = JsonFormatter.format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatRemoved() throws JsonProcessingException {
        testDiff.put("key", Map.of(
            "status", "removed",
            "oldValue", "value"
        ));

        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "removed");
        innerNode.put("oldValue", "value");
        expectedNode.set("key", innerNode);

        String actualJson = Formatter.getFormatter("json", testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatUnchanged() throws JsonProcessingException {
        testDiff.put("key", Map.of(
            "status", "unchanged",
            "oldValue", "value"
        ));

        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "unchanged");
        innerNode.put("oldValue", "value");
        expectedNode.set("key", innerNode);

        String actualJson = Formatter.getFormatter("json", testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatComplexStructure() throws JsonProcessingException {
        Map<String, Object> oldValueMap = Map.of(
            "name", "John",
            "age", TEST_NUMBER_VALUE
        );

        Map<String, Object> newValueMap = Map.of(
            "name", "Jane",
            "age", TEST_NUMBER_VALUE + 1
        );

        testDiff.put("user", Map.of(
            "status", "changed",
            "oldValue", oldValueMap,
            "newValue", newValueMap
        ));

        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "changed");

        ObjectNode oldNode = mapper.valueToTree(oldValueMap);
        ObjectNode newNode = mapper.valueToTree(newValueMap);

        innerNode.set("oldValue", oldNode);
        innerNode.set("newValue", newNode);
        expectedNode.set("user", innerNode);

        String actualJson = JsonFormatter.format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }
}

