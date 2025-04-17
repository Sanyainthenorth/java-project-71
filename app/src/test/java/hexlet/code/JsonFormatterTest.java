package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class JsonFormatterTest {
    private final Map<String, Map<String, Object>> testDiff = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testDiff.clear();
    }

    @Test
    void testJsonFormatChanged() throws JsonProcessingException {
        testDiff.put("key", Map.of(
            "status", "changed",
            "oldValue", "old",
            "newValue", "new"
        ));

        // Ожидаемый результат как ObjectNode
        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "changed");
        innerNode.put("oldValue", "old");
        innerNode.put("newValue", "new");
        expectedNode.set("key", innerNode);

        String actualJson = Formatter.getFormatter("json", testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatAdded() throws JsonProcessingException {
        testDiff.put("key", Map.of(
            "status", "added",
            "newValue", "value"
        ));

        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "added");
        innerNode.put("newValue", "value");
        expectedNode.set("key", innerNode);

        String actualJson = Formatter.getFormatter("json", testDiff);
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
        testDiff.put("user", Map.of(
            "status", "changed",
            "oldValue", Map.of("name", "John", "age", 30),
            "newValue", Map.of("name", "Jane", "age", 25)
        ));

        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", "changed");

        ObjectNode oldValueNode = mapper.createObjectNode();
        oldValueNode.put("name", "John");
        oldValueNode.put("age", 30);
        innerNode.set("oldValue", oldValueNode);

        ObjectNode newValueNode = mapper.createObjectNode();
        newValueNode.put("name", "Jane");
        newValueNode.put("age", 25);
        innerNode.set("newValue", newValueNode);

        expectedNode.set("user", innerNode);

        String actualJson = Formatter.getFormatter("json", testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }
}

