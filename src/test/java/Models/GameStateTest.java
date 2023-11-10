package Models;

import Controller.GameEngine;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameState.java
 */
class GameStateTest {

    /**
     * Method to test get map file
     * @throws Exception indicates Exception
     */
    @Test
    void getD_mapTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();
        assertEquals("canada.map", l_phase.getD_gameState().getD_map().getD_mapFile());
    }
}