package Models;

import Controller.GameEngine;
import Utils.Command;

import java.io.IOException;

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
    protected void performSaveMap(Command p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performMapEdit(Command p_command, Player p_player) throws IOException {

    }

    @Override
    protected void performEditContinent(Command p_command, Player p_player) throws IOException {

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

}
