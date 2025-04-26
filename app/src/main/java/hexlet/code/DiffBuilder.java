package hexlet.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DiffBuilder {
    public static List<DiffEntry> build(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());
        List<DiffEntry> result = new ArrayList<>();

        for (String key : allKeys) {
            DiffStatus status;
            Object oldValue = data1.get(key);
            Object newValue = data2.get(key);

            if (!data1.containsKey(key)) {
                status = DiffStatus.ADDED;
                newValue = data2.get(key);
            } else if (!data2.containsKey(key)) {
                status = DiffStatus.REMOVED;
                oldValue = data1.get(key);
            } else if (!Objects.equals(oldValue, newValue)) {
                status = DiffStatus.CHANGED;
            } else {
                status = DiffStatus.UNCHANGED;
            }

            result.add(new DiffEntry(status, oldValue, newValue, key));
        }

        Collections.sort(result, DiffEntry.getKeyComparator());
        return result;
    }
}

