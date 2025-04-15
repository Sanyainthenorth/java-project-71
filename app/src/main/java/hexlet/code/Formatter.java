package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.Map;

public class Formatter {
    public static String getFormatter(String format, Map<String, Map<String, Object>> diff) {
        switch (format) {
            case "plain":
                return Plain.format(diff);
            case "stylish":
                return Stylish.format(diff);
            default:
                throw new IllegalArgumentException("Unknown format: " + format);
        }
    }
}
