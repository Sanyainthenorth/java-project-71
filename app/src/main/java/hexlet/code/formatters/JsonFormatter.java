package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DiffEntry;

import java.util.List;

public final class JsonFormatter implements Formatter {
    @Override
    public String format(List<DiffEntry> diffEntries) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return objectMapper.writeValueAsString(diffEntries);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сериализации JSON", e);
        }
    }
}
