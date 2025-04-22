package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DifferTest {

    private String getExpected(String filename) throws Exception {
        return Files.readString(Paths.get(getFixturePath(filename))).trim();
    }

    private String getFixturePath(String filename) {
        return Paths.get("src", "test", "resources", filename).toString();
    }

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
    void testGenerate(String file1, String file2, String format, String expectedResultFile) throws Exception {
        String expected = getExpected(expectedResultFile);
        String actual = Differ.generate(
            getFixturePath(file1),
            getFixturePath(file2),
            format
        );
        assertEquals(expected, actual.trim());
    }

    @Test
    void testDefaultFormat() throws Exception {
        String result = Differ.generate(
            getFixturePath("file1.json"),  // ← Добавляем путь
            getFixturePath("file2.json")   // ← Добавляем путь
        );
        assertFalse(result.isEmpty());
    }

    @Test
    void testTxtToYamlShouldFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(
                getFixturePath("file1.txt"),  // ← Добавляем путь
                getFixturePath("file2.yaml"),
                "stylish"
            );
        });
        assertTrue(exception.getMessage().contains("Unknown file format"));
    }
}
