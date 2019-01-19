import java.io.*;
import java.util.Map;

public class CodeWriter {
    private PrintStream printStream;
    // How many times do eq, lt, gt appears. Need to be used as part of the labels.
    private int timeComparisons = 0;

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
                            "A=M-1" + System.lineSeparator() +
                            "M=-M";
                break;
            case "eq": case "gt": case "lt":
                String type = "";
                switch (arith) {
                    case "eq": type = "JEQ"; break;
                    case "gt": type = "JGT"; break;
                    case "lt": type = "JLT"; break;
                }
                assembly += "@SP" + System.lineSeparator() +
                            "AM=M-1" + System.lineSeparator() +
                            "D=M" + System.lineSeparator() +
                            "A=A-1" + System.lineSeparator() +
                            "D=M-D" + System.lineSeparator() +
                            "@TRUE" + timeComparisons + System.lineSeparator() +
                            "D;" + type + System.lineSeparator() +
                            "@SP" + System.lineSeparator() +
                            "A=M-1" + System.lineSeparator() +
                            "M=0" + System.lineSeparator() +
                            "@CONTINUE" + timeComparisons + System.lineSeparator() +
                            "0;JMP" + System.lineSeparator() +
                            "(TRUE" + timeComparisons + ")" + System.lineSeparator() +
                            "@SP" + System.lineSeparator() +
                            "A=M-1" + System.lineSeparator() +
                            "M=-1" + System.lineSeparator() +
                            "(CONTINUE" + timeComparisons + ")";
                timeComparisons++;
                break;
            case "not":
                assembly += "@SP" + System.lineSeparator() +
                            "A=M-1" + System.lineSeparator() +
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

    private String generatePushPop(Map<Integer, String> command) {
        String pushPop = command.get(0);
        String memSeg = command.get(1);
        String num = command.get(2);

        String assembly = "";

        switch (memSeg) {
            case "local": case "argument": case "this": case "that":
                String base = "";
                switch (memSeg) {
                    case "local": base = "LCL"; break;
                    case "argument": base = "ARG"; break;
                    case "this": base = "THIS"; break;
                    case "that": base = "THAT"; break;
                }
                if (pushPop.equals("push")) {
                    assembly += "@" + num + System.lineSeparator() +
                                "D=A" + System.lineSeparator() +
                                "@" + base + System.lineSeparator() +
                                "A=D+M" + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "M=D" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "M=M+1";
                } else {
                    // Pop.
                    assembly += "@" + num + System.lineSeparator() +
                                "D=A" + System.lineSeparator() +
                                "@" + base + System.lineSeparator() +
                                "D=D+M" + System.lineSeparator() +
                                "@R13" + System.lineSeparator() +
                                "M=D" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "M=M-1" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@R13" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "M=D";
                }
                break;
            case "constant":
                // Only can push to constant.
                assembly += "@" + num + System.lineSeparator() +
                            "D=A" + System.lineSeparator() +
                            "@SP" + System.lineSeparator() +
                            "A=M" + System.lineSeparator() +
                            "M=D" + System.lineSeparator() +
                            "@SP" + System.lineSeparator() +
                            "M=M+1";
                break;
            case "static":
                if (pushPop.equals("push")) {
                    assembly += "@Static." + num + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "M=D" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "M=M+1";
                } else {
                    // Pop
                    assembly += "@SP" + System.lineSeparator() +
                                "M=M-1" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@Static." + num + System.lineSeparator() +
                                "M=D";
                }
                break;
            case "temp":
                if (pushPop.equals("push")) {
                    assembly += "@5" + System.lineSeparator() +
                                "D=A" + System.lineSeparator() +
                                "@" + num + System.lineSeparator() +
                                "A=D+A" + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "M=D" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "M=M+1";
                } else {
                    // Pop.
                    assembly += "@5" + System.lineSeparator() +
                                "D=A" + System.lineSeparator() +
                                "@" + num + System.lineSeparator() +
                                "D=D+A" + System.lineSeparator() +
                                "@R14" + System.lineSeparator() +
                                "M=D" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "M=M-1" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@R14" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "M=D";
                }
                break;
            case "pointer":
                String token;
                if (num.equals("0")) {
                    token = "THIS";
                } else {
                    token = "THAT";
                }
                if (pushPop.equals("push")) {
                    assembly += "@" + token + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "M=D" + System.lineSeparator() +
                                "@SP" + System.lineSeparator() +
                                "M=M+1";
                } else {
                    // Pop.
                    assembly += "@SP" + System.lineSeparator() +
                                "M=M-1" + System.lineSeparator() +
                                "A=M" + System.lineSeparator() +
                                "D=M" + System.lineSeparator() +
                                "@" + token + System.lineSeparator() +
                                "M=D";
                }
                break;
        }
        return assembly;
    }

    public void close() {
        String endLoop = "(END)" + System.lineSeparator() +
                        "@END" + System.lineSeparator() +
                        "0; JMP";
        printStream.println(endLoop);
        printStream.close();
    }
}
