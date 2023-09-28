package Controller;

import Utils.Command;

import java.util.*;
import java.util.Scanner;

/**
 * Game Entry point
 * ------Currently contains test code for testing input command----
 */
public class GameEngine {
    public static void main(String[] args) {
        System.out.println("Enter the Command");
        Scanner l_userInput = new Scanner(System.in);
        String l_commandString;
        l_commandString = l_userInput.nextLine();
        Command l_command = new Command(l_commandString);
        System.out.println("Base command : "+l_command.getMainCommand());
        Map<String,String> l_taskMap = l_command.getTaskandArguments();
        System.out.println("Operation >> "+l_taskMap.get("operation"));
        System.out.println("Arguments >> "+l_taskMap.get("arguments"));

    }
}
