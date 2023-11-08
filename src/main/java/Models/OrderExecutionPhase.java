package Models;

import Constants.GameConstants;
import Controller.GameEngine;
import Utils.Command;
import Views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    protected void performAdvance(String p_command, Player p_player) throws Exception {
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
     * Invokes the execution logic for all unexecuted orders of players in the game.
     */
    protected void executeOrders() {
        addNeutralPlayer(d_gameState);
        // Executing orders
        d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Orders.....", "start");
        while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
            for (Player l_player : d_gameState.getD_players()) {
                Order l_order = l_player.next_order();
                if (l_order != null) {
                    l_order.printOrder();
                    d_gameState.updateLog(l_order.orderExecutionLog(), "effect");
                    l_order.execute(d_gameState);
                }
            }
        }
        d_playerService.resetPlayersFlag(d_gameState.getD_players());
    }

    /**
     * Adds a neutral player to the game state.
     *
     * @param p_gameState The GameState to which the neutral player is added
     */
    public void addNeutralPlayer(GameState p_gameState) {
    }

    /**
     * Checks if a single player has conquered all countries of the map, indicating the end of the game.
     *
     * @param p_gameState Current state of the game
     * @return true if a player has conquered all countries and the game should end; otherwise, returns false
     */
    protected Boolean checkEndOftheGame(GameState p_gameState) {
        Integer l_totalCountries = p_gameState.getD_map().getD_countries().size();
        for (Player l_player : p_gameState.getD_players()) {
            if (l_player.getD_coutriesOwned().size() == l_totalCountries) {
                d_gameEngine.setD_gameEngineLog("Player : " + l_player.getPlayerName()
                        + " has won the Game by conquering all countries. Exiting the Game .....", GameConstants.ENDLOG);
                return true;
            }
        }
        return false;
    }


    /**
     * This method signifies the main functionality executed on phase change.
     */
    @Override
    public void initPhase() {
        while (d_gameEngine.getD_CurrentPhase() instanceof OrderExecutionPhase) {
            executeOrders();

            MapView l_map_view = new MapView(d_gameState);
            l_map_view.showMap();

            if (this.checkEndOftheGame(d_gameState))
                break;

            while (d_gameState.getD_players() != null) {
                System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

                try {
                    String l_continue = l_reader.readLine();

                    if (l_continue.equalsIgnoreCase("N")) {
                        break;
                    } else if(l_continue.equalsIgnoreCase("Y")){
                        d_playerService.assignArmies(d_gameState);
                        d_gameEngine.setIssueOrderPhase();
                    } else {
                        System.out.println("Invalid Input");
                    }
                } catch (IOException l_e) {
                    System.out.println("Invalid Input");
                }
            }
        }
    }
}
