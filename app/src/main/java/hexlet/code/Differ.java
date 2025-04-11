package hexlet.code;

import java.util.Map;
import java.util.TreeSet;

public class Differ {
    public static String getFormat(String filepath) {
        if (filepath.endsWith(".json")) {
            return "json";
        }
        if (filepath.endsWith(".yaml") || filepath.endsWith(".yml")) {
            return "yaml";
        }
        throw new IllegalArgumentException("Unknown file format");
    }
    public static String generate(String filepath1, String filepath2) throws Exception {
        String content1 = ReadParse.readFile(filepath1);
        String content2 = ReadParse.readFile(filepath2);

        String format1 = getFormat(filepath1);
        String format2 = getFormat(filepath2);

        Map<String, Object> data1 = ReadParse.parse(content1, format1);
        Map<String, Object> data2 = ReadParse.parse(content2, format2);

        var diff = new StringBuilder();
        var allKeys = new TreeSet<String>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        diff.append("{\n");

        for (String key : allKeys) {
            if (!data1.containsKey(key)) {
                diff.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
            } else if (!data2.containsKey(key)) {
                diff.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
            } else if (!data1.get(key).equals(data2.get(key))) {
                diff.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
                diff.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
            } else {
                diff.append("    ").append(key).append(": ").append(data1.get(key)).append("\n");
            }
        }
        diff.append("}");

        return diff.toString();
    }
}

