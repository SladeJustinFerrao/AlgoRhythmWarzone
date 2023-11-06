package Models;

import Controller.GameEngine;
import Utils.Command;
import Utils.UncaughtExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StartUpPhase extends Phase {

    /**
     * Constructor for initializing the current game engine and game state.
     *
     * @param p_gameEngine Instance of the game engine to update the state
     * @param p_gameState  Instance of the game state
     */
    public StartUpPhase(GameEngine p_gameEngine, GameState p_gameState) {
        super(p_gameEngine, p_gameState);
    }

    @Override
    protected void performValidateMap(Command p_command, Player p_player) throws IOException {
    }

    @Override
    protected void performEditNeighbour(Command p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performEditCountry(Command p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performLoadMap(Command p_command, Player p_player) throws IOException {

    }
    @Override
    protected void createPlayers(Command p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performCreateDeploy(String p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performAdvance(String p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performCardHandle(String p_enteredCommand, Player p_player) throws IOException {
        printInvalidCommandInState();
    }

    @Override
    protected void performAssignCountries(Command p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performShowMap(Command p_command, Player p_player) throws IOException {

    }

    @Override
    public void initPhase() {

    }
    /**
     * {@inheritDoc}
     */

    public void performMapEdit(Command p_command, Player p_player) throws IOException {
        List<java.util.Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            System.out.println("Invalid Command");
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent("arguments", l_map)) {
                    d_mapService.editMap(d_gameState, l_map.get("arguments"));
                } else {
                    System.out.println("Invalid Command");
                }
            }
        }
    }

    @Override
    protected void performEditContinent(Command p_command, Player p_player) throws IOException {

    }

    /**
     * {@inheritDoc}
     */
    public void performSaveMap(Command p_command, Player p_player) {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("No map found to save, Please `editmap` first", "effect");
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            System.out.println("Invalid Command");
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent("arguments", l_map)) {
                    boolean l_fileUpdateStatus = d_mapService.saveMap(d_gameState,
                            l_map.get("arguments"));
                    if (l_fileUpdateStatus) {
                        d_gameEngine.setD_gameEngineLog("Required changes have been made in map file", "effect");
                    } else
                        System.out.println(d_gameState.getError());
                } else {
                    System.out.println("Invalid Command");
                }
            }
        }
    }


}
