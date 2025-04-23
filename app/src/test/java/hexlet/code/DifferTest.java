package hexlet.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DifferTest {
    @ParameterizedTest
    @CsvSource({
        // Stylish format
        "file1.json, file2.json, stylish, result_stylish.diff",
        "file1.yaml, file2.yaml, stylish, result_stylish.diff",
        "file1.yml, file2.yml, stylish, result_stylish.diff",
        "file1.yaml, file2.yml, stylish, result_stylish.diff",

        // Plain format
        "file1.json, file2.json, plain, result_plain.diff",
        "file1.yaml, file2.yaml, plain, result_plain.diff",
        "file1.yml, file2.yml, plain, result_plain.diff",
        "file1.yaml, file2.yml, plain, result_plain.diff",

        // JSON format
        "file1.json, file2.json, json, result_json.diff",
        "file1.yaml, file2.yaml, json, result_json.diff",
        "file1.yml, file2.yml, json, result_json.diff",
        "file1.yaml, file2.yml, json, result_json.diff"
    })
    void testGenerateDiff(String file1, String file2, String format, String expectedResultFile) throws Exception {
        String filepath1 = getFixturePath(file1);
        String filepath2 = getFixturePath(file2);
        String expectedResult = Files.readString(Paths.get(getFixturePath(expectedResultFile)));

        String result = Differ.generate(filepath1, filepath2, format);
        System.out.println("Result:\n" + result);
        System.out.println("Expected:\n" + expectedResult);

        assertEquals(expectedResult, result);
    }
    private String getFixturePath(String filename) {
        // Получаем путь к ресурсам для тестов
        Path path = Paths.get("src", "test", "resources", filename).toAbsolutePath().normalize();
        return path.toString();
    }
}
