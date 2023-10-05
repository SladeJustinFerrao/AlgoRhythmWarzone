package Utils;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Command class
 */
public class CommandTest {
    /**
     * Test for base command retrieval
     */
    @Test
    void testBaseCommand() {
        Command l_command = new Command("editmap -add 12 23");
        String l_basecommand = l_command.getMainCommand();

        assertEquals("editmap",l_basecommand);
    }

    /**
     * Test for Single task in a command line for a base command
     */
    @Test
    void testSingleTask() {
        Command l_command = new Command("editmap -add 12 23");
        List<Map<String,String>> l_taskList = l_command.getTaskandArguments();

        assertEquals(1,l_taskList.size());
        assertEquals("add",l_taskList.get(0).get("operation"));
        assertEquals("12 23",l_taskList.get(0).get("arguments"));
    }

    /**
     * Test for Multiple task in a command line for a base command
     */
    @Test
    void testMultipleTask() {
        Command l_command = new Command("editmap -add 12 23 -remove 45 43");
        List<Map<String,String>> l_taskList = l_command.getTaskandArguments();

        assertEquals(2,l_taskList.size());
        assertEquals("add",l_taskList.get(0).get("operation"));
        assertEquals("12 23",l_taskList.get(0).get("arguments"));
        assertEquals("remove",l_taskList.get(1).get("operation"));
        assertEquals("45 43",l_taskList.get(1).get("arguments"));
    }

    /**
     * Test for filepath in command
     */
    @Test
    void testfilepath() {
        Command l_command = new Command("loadmap myMap.map");
        List<Map<String,String>> l_taskList = l_command.getTaskandArguments();

        assertEquals(1,l_taskList.size());
        assertEquals("filename",l_taskList.get(0).get("operation"));
        assertEquals("myMap.map",l_taskList.get(0).get("arguments"));
    }
}