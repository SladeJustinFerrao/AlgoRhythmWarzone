package Utils;

import java.util.*;

/**
 * Class to handle input command from End User
 */
public class Command {

    /**
     * Stores the Player input command.
     */
    public String d_command;

    /**
     * Constructor to set d_command with the input.
     * @param p_input User input.
     */
    public Command(String p_input){
        this.d_command = p_input;
    }

    /**
     * Retrieves the Main Activity in the command.
     * @return String of Main command
     */
    public String getMainCommand() {
        String[] l_baseCommand = this.d_command.split(" ");
        return l_baseCommand[0];
    }

    /**
     * Retrieves the Tasks to be performed under the Main Activity.
     * @return Map of Operations and Arguments for the command
     */
    public Map<String,String> getTaskandArguments(){
        String l_baseCommand = getMainCommand();
        String l_tasks = d_command.replace(l_baseCommand,"").trim();

        Map<String,String> l_tasksMap = new HashMap<String,String>();

        l_tasksMap = getIndividualTasksandArguments(l_tasks);
        return l_tasksMap;
    }

    /**
     * Generate a map of Operations and Arguments
     * @param p_tasks base string of input command excluding the Main Activity keyword
     * @return Map of Operations and Arguments
     */
    public Map<String, String> getIndividualTasksandArguments(String p_tasks){
        String[] l_taskList = p_tasks.split(" ");
        Map<String, String> l_taskMap = new HashMap<String, String>();
        String l_arguments = "";

        l_taskMap.put("operation", l_taskList[0].replace("-",""));

        if(l_taskList.length > 1){
            l_arguments = p_tasks.replace(l_taskList[0],"").trim();
        }
        l_taskMap.put("arguments", l_arguments);

        return l_taskMap;
    }
}
