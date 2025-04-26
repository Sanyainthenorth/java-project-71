package hexlet.code.formatters;

import hexlet.code.DiffEntry;
import hexlet.code.DiffStatus;

import java.util.List;
import java.util.Map;

public final class PlainFormatter implements Formatter {
    @Override
    public String format(List<DiffEntry> diffEntries) {
        StringBuilder result = new StringBuilder();

        for (DiffEntry entry : diffEntries) {
            String key = entry.getKey();
            DiffStatus status = entry.getStatus();

            switch (status) {
                case ADDED:
                    result.append(String.format(
                        "Property '%s' was added with value: %s\n",
                        key, formatValue(entry.getNewValue())
                    ));
                    break;
                case REMOVED:
                    result.append(String.format("Property '%s' was removed\n", key));
                    break;
                case CHANGED:
                    result.append(String.format(
                        "Property '%s' was updated. From %s to %s\n",
                        key, formatValue(entry.getOldValue()), formatValue(entry.getNewValue())
                    ));
                    break;
                case UNCHANGED:
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
            return "'" + value + "'"; // Добавлены кавычки для строк
        }
        if (value instanceof Map || value instanceof Iterable) {
            return "[complex value]";
        }
        return value.toString();
    }
}
