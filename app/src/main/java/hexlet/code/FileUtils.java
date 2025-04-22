package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static String readFile(String filepath) throws Exception {
        Path path = Paths.get(filepath).toAbsolutePath().normalize();
        return Files.readString(path);
    }
    public static String getFormat(String filepath) {
        if (filepath.endsWith(".json")) {
            return "json";
        }
        if (filepath.endsWith(".yaml") || filepath.endsWith(".yml")) {
            return "yaml";
        }
        throw new IllegalArgumentException("Unknown file format");
    }
}
