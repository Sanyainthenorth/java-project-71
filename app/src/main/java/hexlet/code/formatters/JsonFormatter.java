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
                String status = entry.getStatus();
                
                if ("unchanged".equals(status)) {
                    entryMap.put("oldValue", entry.getOldValue());
                } else if ("removed".equals(status)) {
                    entryMap.put("oldValue", entry.getOldValue());
                } else if ("changed".equals(status)) {
                    entryMap.put("newValue", entry.getNewValue());
                    entryMap.put("oldValue", entry.getOldValue());
                } else if ("added".equals(status)) {
                    entryMap.put("newValue", entry.getNewValue());
                }

                entryMap.put("status", status);

                resultMap.put(entry.getKey(), entryMap);
            }
            return objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при форматировании JSON", e);
        }
    }
}
