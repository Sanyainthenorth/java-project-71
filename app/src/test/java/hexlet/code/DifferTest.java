package hexlet.code;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    @Test
    public void testDifferFiles() throws Exception {

        String filePath1 = Paths.get("file1.json").toAbsolutePath().toString();
        String filePath2 = Paths.get("file2.json").toAbsolutePath().toString();

        String fileContent1 = ReadParse.readFile(filePath1);
        String fileContent2 = ReadParse.readFile(filePath2);

        Map<String, Object> data1 = ReadParse.parseJson(fileContent1);
        Map<String, Object> data2 = ReadParse.parseJson(fileContent2);

        String diff = Differ.generate(data1, data2);

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
