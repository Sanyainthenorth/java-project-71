package hexlet.code;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;


class DifferTest {

    private String getExpected(String filename) throws Exception {
        return Files.readString(Paths.get("src/test/resources/" + filename)).trim();
    }

    @Test
    void testJsonToJson() throws Exception {
        String expected = getExpected("result.diff");
        String actual = Differ.generate("file1.json", "file2.json", "stylish");
        assertEquals(expected, actual.trim());
    }

    @Test
    void testYamlToYaml() throws Exception {
        String expected = getExpected("result.diff");
        String actual = Differ.generate("file1.yaml", "file2.yaml", "stylish");
        assertEquals(expected, actual.trim());
    }

    @Test
    void testYmlToYml() throws Exception {
        String expected = getExpected("result.diff");
        String actual = Differ.generate("file1.yml", "file2.yml", "stylish");
        assertEquals(expected, actual.trim());
    }

    @Test
    void testYamlToYml() throws Exception {
        String expected = getExpected("result.diff");
        String actual = Differ.generate("file1.yaml", "file2.yml", "stylish");
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
