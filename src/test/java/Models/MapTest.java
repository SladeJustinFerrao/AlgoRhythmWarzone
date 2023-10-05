package Models;

import Controller.GameEngine;
import Services.MapService;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    /**
     * Invalid Test to see if all elements of the map are connected
     */
    @Test
    void invalidMapTest() {

        GameEngine l_gameEngine = new GameEngine();
        GameState d_gameState = new GameState();
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("loadmap TestInvalidMap.map");
            l_gameEngine.performLoadMap(l_command);
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertTrue(l_isExcep);
    }

    /**
     * Valid test to see if all elements of map are connectedS
     */
    @Test
    void validMapTest() {

        GameEngine l_gameEngine = new GameEngine();
        GameState d_gameState = new GameState();
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("loadmap canada.map");
            l_gameEngine.performLoadMap(l_command);
        } catch (Exception l_e){
            l_isExcep=true;
        }
        assertFalse(l_isExcep);
    }

    @Test
    void isCountriesConnectedTest() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("canada.map"));

        assertEquals(true,l_map.isCountriesConnected());
    }

    @Test
    void isContinentsConnectedTest() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("canada.map"));

        assertEquals(true,l_map.isContinentsConnected());
    }
}