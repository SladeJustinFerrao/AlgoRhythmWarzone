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
     * {@inheritDoc}
     */
    @Override
    protected void performValidateMap(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performEditNeighbour(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performEditCountry(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performLoadMap(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performSaveMap(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performMapEdit(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performEditContinent(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createPlayers(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performCreateDeploy(String p_command, Player p_player) throws Exception {
        printInvalidCommandInState();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performAdvance(String p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void performCardHandle(String p_enteredCommand, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performAssignCountries(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performShowMap(Command p_command, Player p_player) throws Exception {
        MapView l_mapView = new MapView(d_gameState);
        l_mapView.showMap();

    }

    /**
     * Invokes the execution logic for all unexecuted orders of players in the game.
     */
    protected void executeOrders() {
        addNeutralPlayer(d_gameState);
        // Executing orders
        d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Orders.....", GameConstants.STARTLOG);
        while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
            for (Player l_player : d_gameState.getD_players()) {
                Order l_order = l_player.next_order();
                if (l_order != null) {
                    l_order.printOrder();
                    d_gameState.updateLog(l_order.orderExecutionLog(), GameConstants.OUTCOME);
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

        Player l_player = p_gameState.getD_players().stream()
                .filter(l_pl -> l_pl.getPlayerName().equalsIgnoreCase("Neutral")).findFirst().orElse(null);
        if (l_player == null) {
            Player l_neutralPlayer = new Player("Neutral");
            l_neutralPlayer.setD_moreOrders(false);
            p_gameState.getD_players().add(l_neutralPlayer);
        } else {
            return;
        }
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
     * {@inheritDoc}
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
