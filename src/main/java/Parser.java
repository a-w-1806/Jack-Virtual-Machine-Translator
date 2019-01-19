import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    private Scanner scanner;
    private CommandType commandType;
    private Map<Integer, String> command;

    public Parser(String path) {
        scanner = new Scanner(path);
    }

    public boolean hasMoreCommands() {
        if (!scanner.hasNextLine()) {
            scanner.close();
        }
        return scanner.hasNextLine();
    }

    public void advance() {
        /*  Read one line.
            Remove the comments and blanks.
                if "", set command type and command to null.
            set the command type
            set the command
         */
        String s = scanner.nextLine();
        s = removeComment(s);
        commandType = setCommandType(s);
        command = setCommand(s);
    }

    private Map<Integer, String> setCommand(String s) {
        Map<Integer, String> map = new HashMap<>();
        String[] splited = s.split("\\s+");
        for (int i = 0; i < splited.length; i++) {
            map.put(i, splited[i]);
        }
        return map;
    }

    private CommandType setCommandType(String s) {
        if (s.equals("")) {
            return null;
        }
        String[] splited = s.split("\\s+");
        if (splited.length == 1) {
            return CommandType.C_ARITHMETIC;
        }
        if (splited.length == 3) {
            if (splited[0].equals("pop")) {
                return CommandType.C_POP;
            }
            if (splited[0].equals("push")) {
                return CommandType.C_PUSH;
            }
        }
        // More coming.
        return null;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    /** Each command is represented by a map, where key is the index, value is the string
     * For example, push static 2 => {0: "push", 1: "static", 2: "2"}
     * @return the map
     */
    public Map<Integer, String> getCommand() {
        return command;
    }

    private static String removeComment(String s) {
        // Remove the comments.
        int offSet = s.indexOf("//");
        if (offSet != -1) {
            s = s.substring(0, offSet);
        }
        return s;
    }
}
