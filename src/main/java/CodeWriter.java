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

    public String generateArithmetic(Map<Integer, String> command) {
        String assembly = "";
        String arith = command.get(0);
        switch (arith) {
            case "add": break;
            case "sub": break;
            case "neg":
                assembly += "@SP" + System.lineSeparator() +
                        "A=M" + System.lineSeparator() +
                        "M=-M";
                break;
            case "eq": break;
            case "gt": break;
            case "lt": break;
            case "and": break;
            case "or": break;
            case "not":
                assembly += "@SP" + System.lineSeparator() +
                        "A=M" + System.lineSeparator() +
                        "M=!M";
                break;
        }
        return assembly;
    }

    public void writePushPop(Map<Integer, String> command) {
        String comment = generateComment(command);
        String assembly = generatePushPop(command);
        printStream.println(comment + assembly);
    }

    private String generateComment(Map<Integer, String> command) {
        StringBuilder s = new StringBuilder("// ");
        for (int i = 0; i < command.size(); i++) {
            s.append(command.get(i)).append(" ");
        }
        return s.toString() + System.lineSeparator();
    }

    public String generatePushPop(Map<Integer, String> command) {
        return "this is generatePushPop";
    }

    public void close() {
        printStream.close();
    }
}
