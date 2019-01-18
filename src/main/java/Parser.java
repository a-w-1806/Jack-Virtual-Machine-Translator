import java.util.Map;

public class Parser {
    public Parser(String path) {

    }

    public boolean hasMoreCommands() {
        return false;
    }

    public void advance() {

    }

    public CommandType getCommandType() {
        return null;
    }

    /** Each command is represented by a map, where key is the index, value is the string
     * For example, push static 2 => {0: "push", 1: "static", 2: "2"}
     * @return the map
     */
    public Map<Integer, String> getCommand() {
        return null;
    }
}
