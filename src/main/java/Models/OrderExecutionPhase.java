package Models;

import Controller.GameEngine;
import Utils.Command;

public class OrderExecutionPhase extends Phase {

    /**
     * Constructor for initializing the current game engine and game state.
     *
     * @param p_gameEngine Instance of the game engine to update the state
     * @param p_gameState  Instance of the game state
     */
    public OrderExecutionPhase(GameEngine p_gameEngine, GameState p_gameState) {
        super(p_gameEngine, p_gameState);
    }

    /**
     * Basic validation of <strong>validatemap</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure in I/O operation
     */
    @Override
    protected void performValidateMap(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Basic validation of <strong>editneighbor</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user in the CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Handles File I/O Exception
     */
    @Override
    protected void performEditNeighbour(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Basic validation of <strong>editcountry</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command Command entered by the user on CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Handles File I/O Exception
     */
    @Override
    protected void performEditCountry(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Basic validation of <strong>loadmap</strong> command for checking required
     * arguments and redirecting control to the model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws Exception indicates failure in I/O operation
     */
    @Override
    protected void performLoadMap(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Basic validation of <strong>savemap</strong> command for checking required
     * arguments and redirecting control to the model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws Exception indicates failure in I/O operation
     */
    @Override
    protected void performSaveMap(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Basic validation of <strong>editmap</strong> command for checking required
     * arguments and redirecting control to the model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws Exception indicates when failure in I/O operation
     */
    @Override
    protected void performMapEdit(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Basic validation of <strong>editcontinent</strong> command for checking
     * required arguments and redirecting control to the model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws Exception indicates failure in I/O operation
     */
    @Override
    protected void performEditContinent(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Basic validation of gameplayer command for checking required arguments and redirecting control to the model for adding or removing players.
     *
     * @param p_command Command entered by the user in the CLI
     * @param p_player  Instance of Player Object
     * @throws Exception Indicates a failure in I/O operation
     */
    @Override
    protected void createPlayers(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Handles the deployment order in the gameplay.
     *
     * @param p_command Command entered by the user
     * @param p_player  Instance of the player object
     * @throws Exception Indicates failure in I/O operation
     */
    @Override
    protected void performCreateDeploy(String p_command, Player p_player) throws Exception {

    }

    /**
     * Handles the advance order in the gameplay.
     *
     * @param p_command Command entered by the user
     * @param p_player  Instance of the player object
     * @throws Exception Indicates failure in I/O operation
     */
    @Override
    protected void performAdvance(String p_command, Player p_player) {
        printInvalidCommandInState();
    }


    /**
     * Handles the card commands.
     *
     * @param p_enteredCommand String representing the entered command
     * @param p_player         Player instance
     * @throws Exception Signals an I/O exception
     */
    @Override
    protected void performCardHandle(String p_enteredCommand, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * Basic validation of <strong>assigncountries</strong> to check for required
     * arguments and redirect control to the model for assigning countries to players.
     *
     * @param p_command Command entered by the user in the CLI
     * @param p_player  Instance of the Player Object
     * @throws Exception Indicates failure in I/O operation
     */
    @Override
    protected void performAssignCountries(Command p_command, Player p_player) throws Exception {

    }

    /**
     * Handles the 'show map' command.
     *
     * @param p_command Command entered by the user
     * @param p_player  Player object instance
     * @throws Exception Indicates a failure in I/O operation
     */
    @Override
    protected void performShowMap(Command p_command, Player p_player) throws Exception {

    }

    /**
     * This method signifies the main functionality executed on phase change.
     */
    @Override
    public void initPhase() {

    }
}
