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
        return Files.readString(Paths.get("src/test/resources/" + filename)).trim();
    }
    private String getFixturePath(String filename) {
        return "src/test/resources/" + filename;
    }

    @ParameterizedTest
    @CsvSource({
        "file1.json, file2.json, stylish",
        "file1.yaml, file2.yaml, stylish",
        "file1.yml, file2.yml, stylish",
        "file1.yaml, file2.yml, stylish"
    })
    void testGenerate(String file1, String file2, String format) throws Exception {
        String expected = getExpected("result.diff");
        String actual = Differ.generate(
            getFixturePath(file1),  // ← Добавляем путь
            getFixturePath(file2),  // ← Добавляем путь
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
