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
            case "add": case "sub": case "and": case "or":
                assembly += "@2" + System.lineSeparator() +
                            "D=A" + System.lineSeparator() +
                            "@SP" + System.lineSeparator() +
                            "A=M-D" + System.lineSeparator() +
                            "D=M" + System.lineSeparator() +
                            "@SP" + System.lineSeparator() +
                            "M=M-1" + System.lineSeparator() +
                            "A=M" + System.lineSeparator();
                if (arith.equals("add")) {
                    assembly += "D=D+M";
                } else if (arith.equals("sub")) {
                    assembly += "D=D-M";
                } else if (arith.equals("and")) {
                    assembly += "D=D&M";
                } else if (arith.equals("or")) {
                    assembly += "D=D|M";
                }
                assembly += System.lineSeparator();
                assembly += "@SP" + System.lineSeparator() +
                            "A=M-1" + System.lineSeparator() +
                            "M=D";
                break;
            case "neg":
                assembly += "@SP" + System.lineSeparator() +
                        "A=M" + System.lineSeparator() +
                        "M=-M";
                break;
            case "eq": break;
            case "gt": break;
            case "lt": break;
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
