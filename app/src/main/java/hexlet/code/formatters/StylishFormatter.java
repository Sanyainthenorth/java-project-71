package hexlet.code.formatters;

import hexlet.code.DiffEntry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class StylishFormatter implements Formatter {
    @Override
    public String format(List<DiffEntry> diffEntries) {
        StringBuilder result = new StringBuilder("{\n");

        for (DiffEntry entry : diffEntries) {
            String key = entry.getKey();
            String status = entry.getStatus();

            switch (status) {
                case "added" -> result.append("  + ").append(key).append(": ")
                                      .append(formatValue(entry.getNewValue())).append("\n");
                case "removed" -> result.append("  - ").append(key).append(": ")
                                        .append(formatValue(entry.getOldValue())).append("\n");
                case "changed" -> {
                    result.append("  - ").append(key).append(": ")
                          .append(formatValue(entry.getOldValue())).append("\n");
                    result.append("  + ").append(key).append(": ")
                          .append(formatValue(entry.getNewValue())).append("\n");
                }
                case "unchanged" -> result.append("    ").append(key).append(": ")
                                          .append(formatValue(entry.getOldValue())).append("\n");
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
