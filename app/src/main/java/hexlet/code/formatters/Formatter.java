package hexlet.code.formatters;

import java.util.Map;

public interface Formatter {
    String format(Map<String, Map<String, Object>> diff);
}
