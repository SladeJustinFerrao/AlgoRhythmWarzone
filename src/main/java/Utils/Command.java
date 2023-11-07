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
     * @return List of Map of Operations and Arguments for the command
     */
    public List<Map<String , String>>  getTaskandArguments(){
        String l_baseCommand = getMainCommand();
        String l_tasks = d_command.replace(l_baseCommand,"").trim();
        List<Map<String , String>> l_taskList  = new ArrayList<Map<String,String>>();

        if(l_tasks!=null && !l_tasks.equals("")) {
            if (l_tasks.charAt(0) != '-') {
                l_tasks = "-filename " + l_tasks;
            }

            Map<String, String> l_tasksMap = new HashMap<String, String>();
            String[] l_taskString = l_tasks.split("-");
            for (int i = 1; i < l_taskString.length; i++) {
                l_taskList.add(getIndividualTasksandArguments(l_taskString[i]));
            }
        }
        return l_taskList;
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

        l_taskMap.put("operation", l_taskList[0]);

        if(l_taskList.length > 1){
            l_arguments = p_tasks.replace(l_taskList[0],"").trim();
        }
        l_taskMap.put("arguments", l_arguments);

        return l_taskMap;
    }

    /**
     * This method checks if the map chosen by the user consists of the specified keys or not.
     * @param p_key keys given by the player
     * @param p_inputMap the input map selected by the player
     * @return boolean true if the required keys are present and false if not
     */
    public boolean checkRequiredKeysPresent(String p_key, Map<String, String> p_inputMap) {
        if(p_inputMap.containsKey(p_key) && null != p_inputMap.get(p_key)
                && !p_inputMap.get(p_key).isEmpty())
            return true;
        return false;
    }

    /**
     * Getter for the command.
     *
     * @return command in string
     */
    public String getD_command() {
        return d_command;
    }
}
