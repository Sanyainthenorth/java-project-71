package hexlet.code;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Map;
import java.nio.file.*;
import java.io.*;
import java.util.concurrent.Callable;


@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0", description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Parameters(index = "0", description = "path to the first file", paramLabel = "filepath1")
    private String filepath1;

    @Parameters(index = "1", description = "path to the second file", paramLabel = "filepath2")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]", defaultValue = "stylish", paramLabel = "format")
    private String format;


    @Override
    public Integer call() {
        try {
            String fileContent1 = ReadParse.readFile(filepath1);
            Map<String, Object> data1 = ReadParse.parseJson(fileContent1);

            String fileContent2 = ReadParse.readFile(filepath2);
            Map<String, Object> data2 = ReadParse.parseJson(fileContent2);

            String diff = Differ.generate(data1, data2);
            System.out.println(diff);

            return 0;
        } catch (Exception e) {
            // В случае ошибки выводим её и возвращаем код ошибки (не 0)
            e.printStackTrace();
            return 1;
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

}
