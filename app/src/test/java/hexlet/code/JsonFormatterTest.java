package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JsonFormatterTest {

    private static final String TEST_KEY = "key";
    private static final String TEST_USER_KEY = "user";
    private static final String TEST_OLD_VALUE = "old";
    private static final String TEST_NEW_VALUE = "new";
    private static final String TEST_VALUE = "value";
    private static final int TEST_AGE_OLD = 30;
    private static final int TEST_AGE_NEW = 25;
    private static final boolean TEST_BOOLEAN = true;

    private Map<String, Map<String, Object>> testDiff;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        testDiff = new HashMap<>();
    }

    @Test
    void testJsonFormatChanged() throws JsonProcessingException {
        testDiff.put(TEST_KEY, Map.of(
            "status", "changed",
            "oldValue", TEST_OLD_VALUE,
            "newValue", TEST_NEW_VALUE
        ));

        ObjectNode expectedNode = createExpectedNode("changed", TEST_OLD_VALUE, TEST_NEW_VALUE);
        String actualJson = FormatterFactory.getFormatter("json").format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatAdded() throws JsonProcessingException {
        testDiff.put(TEST_KEY, Map.of(
            "status", "added",
            "newValue", TEST_VALUE
        ));

        ObjectNode expectedNode = createExpectedNode("added", null, TEST_VALUE);
        String actualJson = FormatterFactory.getFormatter("json").format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatRemoved() throws JsonProcessingException {
        testDiff.put(TEST_KEY, Map.of(
            "status", "removed",
            "oldValue", TEST_VALUE
        ));

        ObjectNode expectedNode = createExpectedNode("removed", TEST_VALUE, null);
        String actualJson = FormatterFactory.getFormatter("json").format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatUnchanged() throws JsonProcessingException {
        testDiff.put(TEST_KEY, Map.of(
            "status", "unchanged",
            "oldValue", TEST_VALUE
        ));

        ObjectNode expectedNode = createExpectedNode("unchanged", TEST_VALUE, null);
        String actualJson = FormatterFactory.getFormatter("json").format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatBooleanValue() throws JsonProcessingException {
        testDiff.put(TEST_KEY, Map.of(
            "status", "added",
            "newValue", TEST_BOOLEAN
        ));

        ObjectNode expectedNode = createExpectedNode("added", null, TEST_BOOLEAN);
        String actualJson = FormatterFactory.getFormatter("json").format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    @Test
    void testJsonFormatComplexStructure() throws JsonProcessingException {
        Map<String, Object> oldValueMap = Map.of(
            "name", "John",
            "age", TEST_AGE_OLD
        );

        Map<String, Object> newValueMap = Map.of(
            "name", "Jane",
            "age", TEST_AGE_NEW
        );

        testDiff.put(TEST_USER_KEY, Map.of(
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
        expectedNode.set(TEST_USER_KEY, innerNode);

        String actualJson = FormatterFactory.getFormatter("json").format(testDiff);
        assertEquals(expectedNode, mapper.readTree(actualJson));
    }

    private ObjectNode createExpectedNode(String status, Object oldValue, Object newValue) {
        ObjectNode expectedNode = mapper.createObjectNode();
        ObjectNode innerNode = mapper.createObjectNode();
        innerNode.put("status", status);

        if (oldValue != null) {
            if (oldValue instanceof String) {
                innerNode.put("oldValue", (String) oldValue);
            } else if (oldValue instanceof Integer) {
                innerNode.put("oldValue", (Integer) oldValue);
            } else if (oldValue instanceof Boolean) {
                innerNode.put("oldValue", (Boolean) oldValue);
            }
        }

        if (newValue != null) {
            if (newValue instanceof String) {
                innerNode.put("newValue", (String) newValue);
            } else if (newValue instanceof Integer) {
                innerNode.put("newValue", (Integer) newValue);
            } else if (newValue instanceof Boolean) {
                innerNode.put("newValue", (Boolean) newValue);
            }
        }

        expectedNode.set(TEST_KEY, innerNode);
        return expectedNode;
    }
}
