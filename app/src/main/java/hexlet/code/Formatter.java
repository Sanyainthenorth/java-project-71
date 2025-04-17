package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.Map;

public class Formatter {
    public static String getFormatter(String format, Map<String, Map<String, Object>> diff) {
        try {
            return switch (format.toLowerCase()) {
                case "stylish" -> Stylish.format(diff);
                case "plain" -> Plain.format(diff);
                case "json" -> JsonFormatter.format(diff);
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            };
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON processing error", e);
        }
    }
}
