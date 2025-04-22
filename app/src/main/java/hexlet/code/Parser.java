package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    private static ObjectMapper getMapper(String format) {
        return switch (format.toLowerCase()) {
            case "json" -> new ObjectMapper();
            case "yaml", "yml" -> new ObjectMapper(new YAMLFactory());
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }

    public static Map<String, Object> parse(String content, String format) throws JsonProcessingException {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("File content is empty");
        }
        ObjectMapper mapper = getMapper(format);
        return mapper.readValue(content.trim(), new TypeReference<Map<String, Object>>() { });
    }
}

