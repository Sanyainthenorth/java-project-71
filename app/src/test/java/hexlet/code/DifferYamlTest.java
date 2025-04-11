package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferYamlTest {
    @Test
    public void testDifferYaml() throws Exception {
        String filepath1 = Paths.get("src/test/resources/file1.yml").toAbsolutePath().toString();
        String filepath2 = Paths.get("src/test/resources/file2.yml").toAbsolutePath().toString();

        //String content1 = ReadParse.readFile(filePath1);
        //String content2 = ReadParse.readFile(filePath2);

        //Map<String, Object> data1 = ReadParse.parseYaml(content1);
        //Map<String, Object> data2 = ReadParse.parseYaml(content2);

        String diff = Differ.generate(filepath1, filepath2);
        String expectedDiff = "{\n"
            + "  - follow: false\n"
            + "    host: hexlet.io\n"
            + "  - proxy: 123.234.53.22\n"
            + "  - timeout: 50\n"
            + "  + timeout: 20\n"
            + "  + verbose: true\n"
            + "}";

        assertEquals(expectedDiff, diff);
    }
}
