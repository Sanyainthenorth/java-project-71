package hexlet.code.formatters;
import java.util.Collection;
import java.util.Map;

public class Stylish {
    public static String format(Map<String, Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder("{\n");

        for (var entry : diff.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> node = entry.getValue();
            String status = (String) node.get("status");

            switch (status) {
                case "added" -> result.append("  + ").append(key).append(": ")
                                      .append(formatValue(node.get("newValue"))).append("\n");
                case "removed" -> result.append("  - ").append(key).append(": ")
                                        .append(formatValue(node.get("oldValue"))).append("\n");
                case "changed" -> {
                    result.append("  - ").append(key).append(": ")
                          .append(formatValue(node.get("oldValue"))).append("\n");
                    result.append("  + ").append(key).append(": ")
                          .append(formatValue(node.get("newValue"))).append("\n");
                }
                case "unchanged" -> result.append("    ").append(key).append(": ")
                                          .append(formatValue(node.get("oldValue"))).append("\n");
                default -> throw new IllegalArgumentException("Неизвестный статус: " + status);
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String formatValue(Object value) {
        if (value instanceof Map || value instanceof Collection) {
            return value.toString();
        }
        return String.valueOf(value);
    }
}
