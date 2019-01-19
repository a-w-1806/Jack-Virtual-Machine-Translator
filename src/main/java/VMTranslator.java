public class VMTranslator {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("You should only provide a path.");
        }
        String path = args[0];
        String extension;
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i+1);
        } else {
            throw new IllegalArgumentException("Does this file have extension?");
        }
        if (!extension.equals("vm")) {
            throw new IllegalArgumentException("Must specify a valid .vm file!");
        }
        Parser parser = new Parser(path);
        String outFilePath = path.split("\\.")[0] + ".asm";
        CodeWriter codeWriter = new CodeWriter(outFilePath);
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.getCommandType() == CommandType.C_ARITHMETIC) {
                codeWriter.writeArithmetic(parser.getCommand());
            } else if (parser.getCommandType() == CommandType.C_PUSH ||
                    parser.getCommandType() == CommandType.C_POP) {
                codeWriter.writePushPop(parser.getCommand());
            }
        }
        codeWriter.close();
    }
}
