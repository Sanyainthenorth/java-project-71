package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DiffEntry;
import hexlet.code.DiffStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JsonFormatter implements Formatter {
    @Override
    public String format(List<DiffEntry> diffEntries) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            Map<String, Map<String, Object>> resultMap = new LinkedHashMap<>();
            for (DiffEntry entry : diffEntries) {
                Map<String, Object> entryMap = new LinkedHashMap<>();
                DiffStatus status = entry.getStatus();

                switch (status) {
                    case ADDED -> entryMap.put("newValue", entry.getNewValue());
                    case REMOVED -> entryMap.put("oldValue", entry.getOldValue());
                    case CHANGED -> {
                        // Явно задаём порядок: newValue -> oldValue
                        entryMap.put("newValue", entry.getNewValue());
                        entryMap.put("oldValue", entry.getOldValue());
                    }
                    case UNCHANGED -> entryMap.put("oldValue", entry.getOldValue());
                    default -> throw new IllegalArgumentException("Unknown status: " + status);
                }

                entryMap.put("status", status.getValue().toLowerCase());
                resultMap.put(entry.getKey(), entryMap);
            }
            return objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при форматировании JSON", e);
        }
    }
}
