package Utils;

import java.util.*;

/**
 * Class to handle input command from End User
 */
public class Command {

    public String d_command;

    public Command(String p_input){
        this.d_command = p_input;
    }

    public String getMainCommand() {
        String[] l_baseCommand = this.d_command.split(" ");
        return l_baseCommand[0];
    }
}
