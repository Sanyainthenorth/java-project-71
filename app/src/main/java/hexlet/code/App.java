package hexlet.code;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0", description = "Compares two configuration files and shows a difference.")
public class App {
    @Parameters(index = "0", description = "path to the first file", paramLabel = "filepath1")
    private String filepath1;

    @Parameters(index = "1", description = "path to the second file", paramLabel = "filepath2")
    private String filepath2;

    // Опция для выбора формата
    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]", defaultValue = "stylish", paramLabel = "format")
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
