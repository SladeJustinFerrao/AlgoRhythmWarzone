package Services;

import Controller.GameEngine;
import Models.*;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for MapService Class
 */
class MapServiceTest {

    /**
     * Test Method to test the editmap method
     */
    @Test
    void editMapTest() {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);

        int l_initCount=0,l_finalCount=0;
        boolean l_isExcep = false;
        try {
            l_phase.handleCommand("editmap canada.map");
            Map l_map = l_phase.getD_gameState().getD_map();
            l_initCount = l_map.getD_continents().size();
            l_phase.handleCommand("editcontinent -add Asia 5");
            l_finalCount = l_map.getD_continents().size();
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertEquals(l_initCount+1,l_finalCount);
    }

    /**
     * Test Method to test the editcountry method
     */
    @Test
    void removeCountryTest() {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);

        int l_initCount=0,l_finalCount=0;
        boolean l_isExcep = false;
        try {
            l_phase.handleCommand("editmap canada.map");
            Map l_map = l_phase.getD_gameState().getD_map();
            l_initCount = l_map.getD_countries().size();
            l_phase.handleCommand("editcountry -remove New_Brunswick");
            l_finalCount = l_map.getD_countries().size();
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertEquals(l_initCount-1,l_finalCount);
    }
}