package Constants;

import java.util.Arrays;
import java.util.List;

/**
 * This class contains all the constants that are going to be used in the application
 */
public final class GameConstants {
    public static final int CONSOLE_WIDTH = 100;
    public static final String CONTROL_VALUE = "Control Value";
    public static final String ARMIES = "Armies";
    public static final String CONNECTIVITY = "Connections";
    public static final String COMMAND = "command";
    public static final String ORDER = "order";
    public static final String PHASE = "phase";
    public static final String OUTCOME = "outcome";
    public static final String ERROR = "error";
    public static final String STARTLOG = "start";
    public static final String ENDLOG = "end";
    public static final String COMMANDLOG = " Command: ";
    public static final String ORDERLOG = " Order Issued: ";
    public static final String SEP = "============";
    public static final String LOG = "Log: ";
    public static final String ERRORLOG = "Error: ";
    public static final String GAMESTART = "---------- Starting the game the Game ----------";
    public static final String LOGFILENAME = "LogRecord.txt";
    public static final String INVALIDCOMMAND = "Invalid Command!!";

    public static final String INVALIDCOMMANDTOURNAMENTMODE = "Invalid Command Tournament Mode!!";
    public static final String ARGUMENTS = "arguments";
    public static final String OPERATIONS = "operation";

    public static final List<String> TOURNAMENT_BEHAVIORS = Arrays.asList("Aggressive", "Random", "Benevolent", "Cheater");

    public static final String AVAILABLECOMMANDS = "------------AVAILABLE COMMANDS------------\n"+
            "editmap filename\n" +
            "editcontinent -add continentName continentvalue -remove continentName\n" +
            "editcountry -add countryName continentName -remove countryName\n" +
            "editneighbor -add countryName neighborcountryName -remove countryName neighborcountryName showmap\n" +
            "savemap filename\n" +
            "loadmap filename\n" +
            "validatemap\n" +
            "gameplayer -add playername -remove playername\n" +
            "assigncountries\n" +
            "deploy countryName num \n" +
            "advance countrynamefrom countynameto numarmies\n" +
            "bomb countryName\n" +
            "bloackade countryName \n" +
            "------------------------------------";

}
