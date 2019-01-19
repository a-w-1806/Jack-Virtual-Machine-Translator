import org.junit.Before;
import org.junit.Test;

import javax.xml.stream.events.Comment;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParserTest {
    private Parser simpleArith, commentArith, simpleMem, comment;

    @Before
    public void setUp() throws Exception {
        simpleArith = new Parser("src/test/resources/SimpleArith.vm");
        commentArith =  new Parser("src/test/resources/CommentArith.vm");
        simpleMem = new Parser("src/test/resources/SimpleMem.vm");
        comment = new Parser("src/test/resources/Comment.vm");

        simpleArith.advance();
        commentArith.advance();
        simpleMem.advance();
        comment.advance();
    }

    @Test
    public void getCommandType() {
        assertEquals(CommandType.C_ARITHMETIC, simpleArith.getCommandType());
        assertEquals(CommandType.C_ARITHMETIC, commentArith.getCommandType());

        assertEquals(CommandType.C_POP, simpleMem.getCommandType());
        simpleMem.advance();
        assertEquals(CommandType.C_PUSH, simpleMem.getCommandType());

        assertNull(comment.getCommandType());
    }

    @Test
    public void getCommand() {
        Map<Integer, String> map;

        map = new HashMap<>();
        map.put(0, "add");
        assertEquals(map, simpleArith.getCommand());

        map = new HashMap<>();
        map.put(0, "pop");
        map.put(1, "static");
        map.put(2, "2");
        assertEquals(map, simpleMem.getCommand());
    }
}