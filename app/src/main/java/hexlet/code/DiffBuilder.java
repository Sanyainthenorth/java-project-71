package hexlet.code;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class DiffBuilder {
    public static Map<String, Map<String, Object>> build(Map<String, Object> data1, Map<String, Object> data2) {
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
