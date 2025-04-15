package hexlet.code;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    public static String getFormat(String filepath) {
        if (filepath.endsWith(".json")) {
            return "json";
        }
        if (filepath.endsWith(".yaml") || filepath.endsWith(".yml")) {
            return "yaml";
        }
        if (filepath.endsWith(".txt")) {
            return "txt";
        }
        throw new IllegalArgumentException("Unknown file format");
    }
    public static String generate(String filepath1, String filepath2, String format) throws Exception {

        String content1 = ReadParse.readFile(filepath1);
        String content2 = ReadParse.readFile(filepath2);

        String format1 = getFormat(filepath1);
        String format2 = getFormat(filepath2);

        if (format1.equals("txt") || format2.equals("txt")) {
            throw new IllegalArgumentException("Cannot compare .txt file with YAML or JSON");
        }

        Map<String, Object> data1 = ReadParse.parse(content1, format1);
        Map<String, Object> data2 = ReadParse.parse(content2, format2);

        Map<String, Map<String, Object>> diff = buildDiff(data1, data2);
        return Formatter.getFormatter(format, diff);
    }

    private static Map<String, Map<String, Object>> buildDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        Map<String, Map<String, Object>> result = new LinkedHashMap<>();

        for (String key : allKeys) {
            Map<String, Object> node = new HashMap<>();

            if (!data1.containsKey(key)) {
                node.put("status", "added");
                node.put("newValue", data2.get(key));
            } else if (!data2.containsKey(key)) {
                node.put("status", "removed");
                node.put("oldValue", data1.get(key));
            } else if (!Objects.equals(data1.get(key), data2.get(key))) {
                node.put("status", "changed");
                node.put("oldValue", data1.get(key));
                node.put("newValue", data2.get(key));
            } else {
                node.put("status", "unchanged");
                node.put("oldValue", data1.get(key));
            }

            result.put(key, node);
        }

        return result;
    }
}

