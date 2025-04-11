package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DifferTest {
    private String expectedDiff;

    @BeforeEach
    public void setUp() throws Exception {
        // Загружаем ожидаемый результат один раз для всех тестов
        Path expectedPath = Paths.get("src", "test", "resources", "result.diff");
        expectedDiff = Files.readString(expectedPath)
                            .replace("\r\n", "\n")
                            .trim();
    }

    // Тест для JSON-файлов (ваш отдельный тест)
    @Test
    public void testDifferJsonFiles() throws Exception {
        Path file1 = Paths.get("src", "test", "resources", "file1.json");
        Path file2 = Paths.get("src", "test", "resources", "file2.json");

        assertTrue(Files.exists(file1), "File1.json not found");
        assertTrue(Files.exists(file2), "File2.json not found");

        String diff = Differ.generate(file1.toString(), file2.toString())
                            .replace("\r\n", "\n")
                            .trim();

        assertEquals(expectedDiff, diff, "Diff не совпадает для JSON");
    }

    // Параметризованный тест для YAML/yml/txt комбинаций
    @ParameterizedTest
    @CsvSource({
        "file1.yml, file2.yml",
        "file1.yaml, file2.yaml",
        "file1.yml, file2.yaml",
    })
    public void testDifferYamlCombinations(String file1, String file2) throws Exception {
        Path path1 = Paths.get("src", "test", "resources", file1);
        Path path2 = Paths.get("src", "test", "resources", file2);

        assertTrue(Files.exists(path1), "File not found: " + path1);
        assertTrue(Files.exists(path2), "File not found: " + path2);

        String diff = Differ.generate(path1.toString(), path2.toString())
                            .replace("\r\n", "\n")
                            .trim();

        assertEquals(expectedDiff, diff);
    }
    @Test
    public void testDifferYmlAndTxtThrowsException() {
        Path file1 = Paths.get("src", "test", "resources", "file1.txt");
        Path file2 = Paths.get("src", "test", "resources", "file2.yml");

        // Ожидаем исключение
        assertThrows(IllegalArgumentException.class,
                     () -> Differ.generate(file1.toString(), file2.toString()));
        
    }
    @Test
    public void testNonExistentFile() {
        Path invalidFile = Paths.get("src", "test", "resources", "ghost.yml");
        assertThrows(Exception.class, () ->
            Differ.generate(invalidFile.toString(), "file2.yml"));
    }
}