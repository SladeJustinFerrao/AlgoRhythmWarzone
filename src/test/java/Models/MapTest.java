package Models;

import Controller.GameEngine;
import Services.MapService;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Map.java
 */
class   MapTest {

    /**
     * Invalid Test to see if all elements of the map are connected
     */
    @Test
    void invalidMapTest() {

        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Player l_player = new Player();
        StartUpPhase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("loadmap TestInvalidMap.map");
            l_phase.performLoadMap(l_command,l_player);
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertTrue(l_isExcep);
    }

    /**
     * Valid test to see if all elements of map are connected
     */
    @Test
    void validMapTest() {

        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Player l_player = new Player();
        StartUpPhase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("loadmap canada.map");
            l_phase.performLoadMap(l_command,l_player);
        } catch (Exception l_e){
            l_isExcep=true;
        }
        assertFalse(l_isExcep);
    }

    /**
     * Test to see if the countries are connected or not
     */
    @Test
    void isCountriesConnectedTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("loadmap canada.map");
        Map l_map = l_phase.d_gameState.d_map;

        assertEquals(true,l_map.isCountriesConnected());
    }

    /**
     * Test to see if the continents are connected or not
     */
    @Test
    void isContinentsConnectedTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("loadmap canada.map");
        Map l_map = l_phase.d_gameState.d_map;

        assertEquals(true,l_map.isContinentsConnected());
    }

}