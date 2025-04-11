package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ReadParse {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
    public static String readFile(String filepath) throws Exception {

        Path path = Paths.get(filepath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File '" + path + "' does not exist");
        }
        return Files.readString(path);
    }

    public static Map<String, Object> parseJson(String content) throws JsonProcessingException {
        return JSON_MAPPER.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    public static Map<String, Object> parseYaml(String content) throws JsonProcessingException {
        return YAML_MAPPER.readValue(content, new TypeReference<Map<String, Object>>() { });
    }
    // Универсальный метод для автоматического определения формата
    public static Map<String, Object> parse(String content, String format) throws JsonProcessingException {
        return switch (format.toLowerCase()) {
            case "json" -> parseJson(content);
            case "yaml", "yml" -> parseYaml(content);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }

}
