package Controller;

import Models.*;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameEngine.java
 */
class GameEngineTest {

    /**
     * Method to Test Invalid Command input
     * @throws Exception indicates Exception
     */
    @Test
    void performEditContinentInvalidTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        boolean l_isExcep = false;
        try {
            l_phase.handleCommand("editcontinent - add Asia 5");
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertTrue(l_isExcep);
    }

    /**
     * Method to test Valid command input
     * @throws Exception indicates Exception
     */
    @Test
    void performEditContinentValidTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();
        int l_initCount = l_map.getD_continents().size();
        l_phase.handleCommand("editcontinent -add Asia 5");
        int l_finalCount = l_map.getD_continents().size();

        assertEquals(l_initCount+1,l_finalCount);
    }

    /**
     * Method to try loading a file that is not present
     */
    @Test
    void invalidFileLoadTest(){
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Player l_player = new Player();
        StartUpPhase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("loadmap nonexistant.map");
            l_phase.performLoadMap(l_command,l_player);
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertTrue(l_isExcep);
    }
}