package hexlet.code;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        StringBuilder diff = new StringBuilder();

        // Собираем все ключи из обоих файлов
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        diff.append("{\n");

        // Проходим по всем ключам и строим различия
        for (String key : allKeys) {
            if (!data1.containsKey(key)) {
                // Ключ добавлен во втором файле
                diff.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
            } else if (!data2.containsKey(key)) {
                // Ключ удален во втором файле
                diff.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
            } else if (!data1.get(key).equals(data2.get(key))) {
                // Ключ изменен в значении
                diff.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
                diff.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
            } else {
                // Ключ одинаковый в обоих файлах
                diff.append("    ").append(key).append(": ").append(data1.get(key)).append("\n");
            }
        }
        diff.append("}");

        return diff.toString();
    }
}
