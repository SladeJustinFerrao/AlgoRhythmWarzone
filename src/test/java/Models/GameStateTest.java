package Models;

import Controller.GameEngine;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    /**
     * Method to test get map file
     * @throws Exception indicates Exception
     */
    @Test
    void getD_mapTest() throws Exception {
        GameEngine l_gameEngine = new GameEngine();
        Command l_command = new Command("editmap canada.map");
        l_gameEngine.performEditMap(l_command);
        assertEquals("canada.map", l_gameEngine.d_gameState.getD_map().getD_mapFile());
    }
}