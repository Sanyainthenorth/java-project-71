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

    @ParameterizedTest
    @CsvSource({
        "file1.json, file2.json, stylish",
        "file1.yaml, file2.yaml, stylish",
        "file1.yml, file2.yml, stylish",
        "file1.yaml, file2.yml, stylish"
    })
    void testGenerate(String file1, String file2, String format) throws Exception {
        String expected = getExpected("result.diff");
        String actual = Differ.generate(file1, file2, format);
        assertEquals(expected, actual.trim());
    }

    @Test
    void testTxtToYamlShouldFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate("file1.txt", "file2.yaml", "stylish");
        });

        String expectedMessage = "Unknown file format";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
    @Test
    void testDefaultFormat() throws Exception {
        String result = Differ.generate("file1.json", "file2.json");
        assertFalse(result.isEmpty());
    }
}
