package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.util.List;
import java.util.Map;

public class Differ {
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        String format1 = FileUtils.getFormat(filepath1);
        String format2 = FileUtils.getFormat(filepath2);

        String content1 = FileUtils.readFile(filepath1);
        String content2 = FileUtils.readFile(filepath2);

        Map<String, Object> data1 = Parser.parse(content1, format1);
        Map<String, Object> data2 = Parser.parse(content2, format2);
        List<DiffEntry> diffEntries = DiffBuilder.build(data1, data2);
        //Map<String, Map<String, Object>> diff = DiffBuilder.build(data1, data2);
        Formatter formatter = FormatterFactory.getFormatter(format);

        return formatter.format(diffEntries);
    }
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
}
