package Models;

import Constants.GameConstants;
import Controller.GameEngine;
import Services.MapService;
import Services.PlayerServices;
import Utils.Command;

import java.io.IOException;

public abstract class Phase {

    /**
     * Stores the information about the current GamePlay.
     */
    GameState d_gameState;

    /**
     * Stores the information about the current GamePlay.
     */
    GameEngine d_gameEngine;

    /**
     * Flag to determine if the map is loaded.
     */
    boolean l_isMapLoaded;

    /**
     * Manages the map file, handling operations such as loading, reading, parsing, editing, and saving the map.
     */
    MapService d_mapService = new MapService();

    /**
     * Manages player-related operations, including editing players and issuing orders.
     */
    PlayerServices d_playerService = new PlayerServices();

    /**
     * Manages the tournament mode.
     */
    Tournament d_tournament = new Tournament();

    /**
     * Constructor for initializing the current game engine and game state.
     *
     * @param p_gameEngine Instance of the game engine to update the state
     * @param p_gameState  Instance of the game state
     */
    public Phase(GameEngine p_gameEngine, GameState p_gameState) {
        d_gameEngine = p_gameEngine;
        d_gameState = p_gameState;
    }

    /**
     * Retrieves the current game state.
     *
     * @return The current game state
     */
    public GameState getD_gameState() {
        return d_gameState;
    }

    /**
     * Setter method for the current game state.
     *
     * @param p_gameState The game state instance to set for the phase
     */
    public void setD_gameState(GameState p_gameState) {
        d_gameState = p_gameState;
    }


    /**
     * Handles the commands specific to the state entered by the user.
     *
     * @param p_enteredCommand Command entered by the user in the Command Line Interface (CLI)
     * @throws Exception Indicates a failure
     */
    public void handleCommand(String p_enteredCommand) throws Exception {
        commandHandler(p_enteredCommand, null);
    }

    /**
     * Handles state-specific commands entered by the user.
     *
     * @param p_enteredCommand Command entered by the user in the Command Line Interface (CLI)
     * @param p_player         Player instance
     * @throws Exception Indicates a failure
     */
    public void handleCommand(String p_enteredCommand, Player p_player) throws Exception {
        commandHandler(p_enteredCommand, p_player);
    }

    /**
     * Redirect to specific phase implementations the command which were entered  by user.
     *
     * @param p_enteredCommand Command entered by the user in the Command Line Interface (CLI)
     * @param p_player         Player instance
     * @throws Exception Indicates a failure
     */
    private void commandHandler(String p_enteredCommand, Player p_player) throws Exception {
        Command l_command = new Command(p_enteredCommand);
        String l_rootCommand = l_command.getMainCommand();
        l_isMapLoaded = d_gameState.getD_map() != null;

        d_gameState.updateLog(l_command.getcommand(), GameConstants.COMMAND);
        switch (l_rootCommand) {
            case "editmap": {
                performMapEdit(l_command, p_player);
                break;
            }
            case "editcontinent": {
                performEditContinent(l_command, p_player);
                break;
            }
            case "deploy": {
                performCreateDeploy(p_enteredCommand, p_player);
                break;
            }
            case "gameplayer": {
                createPlayers(l_command, p_player);
                break;
            }
            case "savemap": {
                performSaveMap(l_command, p_player);
                break;
            }
            case "loadmap": {
                performLoadMap(l_command, p_player);
                break;
            }
            case "validatemap": {
                performValidateMap(l_command, p_player);
                break;
            }
            case "advance": {
                performAdvance(p_enteredCommand, p_player);
                break;
            }
            case "editcountry": {
                performEditCountry(l_command, p_player);
                break;
            }
            case "editneighbor": {
                performEditNeighbour(l_command, p_player);
                break;
            }
            case "airlift":
            case "blockade":
            case "negotiate":
            case "bomb": {
                performCardHandle(p_enteredCommand, p_player);
                break;
            }
            case "assigncountries": {
                performAssignCountries(l_command, p_player, false, d_gameState);
                break;
            }
            case "showmap": {
                performShowMap(l_command, p_player);
                break;
            }
            case "tournament": {
                tournamentGamePlay(l_command);
                break;
            }
            case "savegame": {
                performSaveGame(l_command, p_player);
                break;
            }
            case "loadgame": {
                performLoadGame(l_command, p_player);
                break;
            }
            case "exit": {
                d_gameEngine.setD_gameEngineLog("Exit Command Entered, Game Ends!", GameConstants.OUTCOME);
                System.exit(0);
                break;
            }
            default: {
                d_gameEngine.setD_gameEngineLog(GameConstants.INVALIDCOMMAND, GameConstants.OUTCOME);
                break;
            }
        }
    }

    /**
     * Basic validation of <strong>validatemap</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performValidateMap(Command p_command, Player p_player) throws Exception;

    /**
     * Basic validation of <strong>editneighbor</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user in the CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performEditNeighbour(Command p_command, Player p_player) throws Exception;

    /**
     * Basic validation of <strong>editcountry</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performEditCountry(Command p_command, Player p_player) throws Exception;

    /**
     * Basic validation of <strong>loadmap</strong> command for checking required
     * arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performLoadMap(Command p_command, Player p_player) throws Exception;

    /**
     * Basic validation of <strong>savemap</strong> command for checking required
     * arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performSaveMap(Command p_command, Player p_player) throws Exception;


    /**
     * Basic validation of <strong>editmap</strong> command for checking required
     * arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performMapEdit(Command p_command, Player p_player) throws Exception;

    /**
     * Basic validation of <strong>editcontinent</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performEditContinent(Command p_command, Player p_player) throws Exception;

    /**
     * Basic validation of gameplayer command for checking required arguments and redirecting control to the model for adding or removing players.
     *
     * @param p_command Command entered by the user in the CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void createPlayers(Command p_command, Player p_player) throws Exception;

    /**
     * Handles the deployment order in the gameplay.
     *
     * @param p_command Command entered by the user
     * @param p_player  Instance of the player object
     * @throws Exception Indicates a failure
     */
    protected abstract void performCreateDeploy(String p_command, Player p_player) throws Exception;


    /**
     * Handles the advance order in the gameplay.
     *
     * @param p_command Command entered by the user
     * @param p_player  Instance of the player object
     * @throws Exception Indicates a failure
     */
    protected abstract void performAdvance(String p_command, Player p_player) throws Exception;

    /**
     * Handels the tournament gameplay.
     *
     * @param p_command Command entered by the user
     * @throws Exception
     */
    protected abstract void tournamentGamePlay(Command p_command) throws Exception;

    /**
     * Handles Game Load Feature.
     *
     * @param p_command command entered by user
     * @param p_player  player instance
     * @throws IOException indicates failure in I/O operation
     */
    protected abstract void performLoadGame(Command p_command, Player p_player) throws Exception;

    /**
     * Handles Game Save Feature.
     *
     * @param p_command command entered by user
     * @param p_player  player instance
     * @throws IOException indicates failure in I/O operation
     */
    protected abstract void performSaveGame(Command p_command, Player p_player) throws IOException;


    /**
     * Handles the card commands.
     *
     * @param p_enteredCommand String representing the entered command
     * @param p_player         Player instance
     * @throws Exception Indicates a failure
     */
    protected abstract void performCardHandle(String p_enteredCommand, Player p_player) throws Exception;

    /**
     * Basic validation of <strong>assigncountries</strong> to check for required
     * arguments and redirect control to the model for assigning countries to players.
     *
     * @param p_command Command entered by the user in the CLI
     * @param p_player  Instance of the Player Object
     * @throws Exception Indicates a failure
     */
    protected abstract void performAssignCountries(Command p_command, Player p_player, boolean p_isTournamentMode, GameState p_gameState) throws Exception;

    /**
     * Handles the 'show map' command.
     *
     * @param p_command Command entered by the user
     * @param p_player  Player object instance
     * @throws Exception Indicates a failure
     */
    protected abstract void performShowMap(Command p_command, Player p_player) throws Exception;

    /**
     * Method to log and print if the command can't be executed in the current phase.
     */
    public void printInvalidCommandInState() {
        d_gameEngine.setD_gameEngineLog("Invalid Command in Current State", GameConstants.OUTCOME);
    }

    /**
     * This method signifies the main functionality executed on phase change.
     */
    public abstract void initPhase(boolean p_isTournamentMode);

}
