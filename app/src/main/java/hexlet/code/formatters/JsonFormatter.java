package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DiffEntry;

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

                switch (entry.getStatus()) {
                    case "unchanged":
                    case "removed":
                        entryMap.put("oldValue", entry.getOldValue());
                        entryMap.put("status", entry.getStatus());
                        break;
                    case "changed":
                        entryMap.put("newValue", entry.getNewValue());
                        entryMap.put("oldValue", entry.getOldValue());
                        entryMap.put("status", entry.getStatus());
                        break;
                    case "added":
                        entryMap.put("newValue", entry.getNewValue());
                        entryMap.put("status", entry.getStatus());
                        break;
                }

                resultMap.put(entry.getKey(), entryMap);
            }
            return objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при форматировании JSON", e);
        }
    }
}
