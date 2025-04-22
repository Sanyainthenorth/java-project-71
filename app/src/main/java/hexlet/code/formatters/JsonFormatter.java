package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonFormatter implements Formatter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String format(Map<String, Map<String, Object>> diff) {
        try {
            return OBJECT_MAPPER.writeValueAsString(diff);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON formatting error", e);
        }
    }
}
