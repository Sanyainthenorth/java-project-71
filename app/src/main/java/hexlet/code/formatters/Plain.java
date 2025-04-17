package hexlet.code.formatters;

import java.util.Map;

public class Plain {
    public static String format(Map<String, Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Map<String, Object>> entry : diff.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> node = entry.getValue();
            String status = (String) node.get("status");

            switch (status) {
                case "added":
                    result.append(String.format(
                        "Property '%s' was added with value: %s\n",
                        key, formatValue(node.get("newValue"))
                    ));
                    break;
                case "removed":
                    result.append(String.format("Property '%s' was removed\n", key));
                    break;
                case "changed":
                    result.append(String.format(
                        "Property '%s' was updated. From %s to %s\n",
                        key, formatValue(node.get("oldValue")), formatValue(node.get("newValue"))
                    ));
                    break;
                case "unchanged":
                    break;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }

        return result.toString().trim();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'"; // ← Добавлены кавычки для строк
        }
        if (value instanceof Map || value instanceof Iterable) {
            return "[complex value]";
        }
        return value.toString();
    }
}
