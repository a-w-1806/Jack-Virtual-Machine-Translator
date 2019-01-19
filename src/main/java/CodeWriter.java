import java.io.*;
import java.util.Map;

public class CodeWriter {
    private PrintStream printStream;

    public CodeWriter(String filePath) {
        try {
            printStream = new PrintStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeArithmetic(Map<Integer, String> command) {
        String comment = generateComment(command);
        String assembly = generateArithmetic(command);
        printStream.println(comment + assembly);
    }

    private String generateArithmetic(Map<Integer, String> command) {
        return "this is generateArithmetic";
    }

    public void writePushPop(Map<Integer, String> command) {
        String comment = generateComment(command);
        String assembly = generatePushPop(command);
        printStream.println(comment + assembly);
    }

    private String generateComment(Map<Integer, String> command) {
        return "// this is generateComment" + System.lineSeparator();
    }

    private String generatePushPop(Map<Integer, String> command) {
        return "this is generatePushPop";
    }

    public void close() {
        printStream.close();
    }
}
