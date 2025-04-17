package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonFormatter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String format(Map<String, Map<String, Object>> diff) throws JsonProcessingException {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(diff);
    }
}
