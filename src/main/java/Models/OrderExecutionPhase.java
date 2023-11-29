package Models;

import Constants.GameConstants;
import Controller.GameEngine;
import Utils.Command;
import Utils.UncaughtExceptionHandler;
import Views.MapView;
import Services.GamePlayService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

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
    protected void performLoadGame(Command p_command, Player p_player) throws Exception {
        printInvalidCommandInState();
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
     * Handels the tournament gameplay.
     *
     * @param p_command Command entered by the user
     * @throws Exception
     */
    @Override
    protected void tournamentGamePlay(Command p_command) throws Exception {


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
    protected void performAssignCountries(Command p_command, Player p_player, boolean isTournamentMode, GameState p_gameState) throws Exception {
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
     * {@inheritDoc}
     */
    @Override
    protected void performSaveGame(Command p_command, Player p_player) throws Exception {
        List<java.util.Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new Exception(GameConstants.INVALIDCOMMANDERRORSAVEGAME);
        }

        for (Map<String, String> l_map : l_operations_list) {
            if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                String l_filename = l_map.get(GameConstants.ARGUMENTS);
                GamePlayService.saveGame(this, l_filename);
                d_gameEngine.setD_gameEngineLog("Game Saved Successfully to "+l_filename, "effect");
            } else {
                throw new Exception(GameConstants.INVALIDCOMMANDERRORSAVEGAME);
            }
        }
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
        d_playerService.updatePlayersInGame(p_gameState);
        for (Player l_player : p_gameState.getD_players()) {
            if (l_player.getD_coutriesOwned().size() == l_totalCountries) {
                d_gameState.setD_winner(l_player);
                d_gameEngine.setD_gameEngineLog("Player : " + l_player.getPlayerName()
                        + " has won the Game by conquering all countries. Exiting the Game .....", "end");
                return true;
            }
        }
        return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initPhase(boolean isTournamentMode) {
        executeOrders();

        MapView l_map_view = new MapView(d_gameState);
        l_map_view.showMap();

        if (this.checkEndOftheGame(d_gameState))
            return;


        try {
            String l_continue = this.continueForNextTurn(isTournamentMode);
            if (l_continue.equalsIgnoreCase("N") && isTournamentMode) {
                d_gameEngine.setD_gameEngineLog("Start Up Phase", "phase");
                d_gameEngine.setD_CurrentPhase(new StartUpPhase(d_gameEngine, d_gameState));
            } else if (l_continue.equalsIgnoreCase("N") && !isTournamentMode) {
                d_gameEngine.setStartUpPhase();

            } else if (l_continue.equalsIgnoreCase("Y")) {
                System.out.println("\n" + d_gameState.getD_numberOfTurnsLeft() + " Turns are left for this game. Continuing for next Turn.\n");
                d_playerService.assignArmies(d_gameState);
                d_gameEngine.setIssueOrderPhase(isTournamentMode);
            } else {
                System.out.println("Invalid Input");
            }
        }  catch (IOException l_e) {
            System.out.println("Invalid Input");
        }

    }
    /**
     * Checks if next turn has to be played or not.
     *
     * @param isTournamentMode if tournament is being played
     * @return Yes or no based on user input or tournament turns left
     * @throws IOException indicates failure in I/O operation
     */
    private String continueForNextTurn(boolean isTournamentMode) throws IOException {
        String l_continue = new String();
        if (isTournamentMode) {
            d_gameState.setD_numberOfTurnsLeft(d_gameState.getD_numberOfTurnsLeft() - 1);
            l_continue = d_gameState.getD_numberOfTurnsLeft() == 0 ? "N" : "Y";
        } else {
            System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
            l_continue = l_reader.readLine();
        }
        return l_continue;
    }
}
