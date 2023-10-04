package Controller;

import Models.GameState;
import Models.Map;
import Services.MapService;
import Utils.Command;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    /**
     * Method to Test Invalid Command input
     * @throws Exception
     */
    @Test
    void performEditContinentInvalidTest() throws Exception {
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.d_mapService = new MapService();
        l_gameEngine.d_gameState = new GameState();
        Map l_map = l_gameEngine.d_mapService.loadMap(l_gameEngine.d_gameState, l_gameEngine.d_mapService.getFilePath("canada.map"));
        int l_initCount = l_map.getD_continents().size();
        Command l_command = new Command("editcontinent - add Asia");
        l_gameEngine.performEditContinent(l_command);
        int l_finalCount = l_map.getD_continents().size();

        assertEquals(l_initCount,l_finalCount);
    }

    /**
     * Method to test Valid command input
     * @throws Exception
     */
    @Test
    void performEditContinentValidTest() throws Exception {
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.d_mapService = new MapService();
        l_gameEngine.d_gameState = new GameState();
        Map l_map = l_gameEngine.d_mapService.loadMap(l_gameEngine.d_gameState, l_gameEngine.d_mapService.getFilePath("canada.map"));
        int l_initCount = l_map.getD_continents().size();
        Command l_command = new Command("editcontinent -add Asia 5");
        l_gameEngine.performEditContinent(l_command);
        int l_finalCount = l_map.getD_continents().size();

        assertEquals(l_initCount+1,l_finalCount);
    }

    /**
     * Method to try loading a file that is not present
     */
    @Test
    void invalidFileLoadTest(){
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.d_mapService = new MapService();
        l_gameEngine.d_gameState = new GameState();
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("loadmap nonexistant.map");
            l_gameEngine.performLoadMap(l_command);
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertTrue(l_isExcep);
    }
}