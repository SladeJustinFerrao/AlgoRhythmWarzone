package Models;

import Controller.GameEngine;
import Services.MapService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Continents.java
 */
class ContinentTest {

    /**
     * Test to see if the country is removed for all neighbour countries
     */
    @Test
    void removeCountryForAllNeighboursTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("loadmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();

        List<Continent> l_continentList = l_map.getD_continents();
        List<Country> l_countriesList = l_continentList.get(0).getD_countries();
        int l_initCount = l_countriesList.get(0).getD_neighbourCountryId().size();
        l_continentList.get(0).removeCountryForAllNeighbours(8);
        int l_finalCount = l_countriesList.get(0).getD_neighbourCountryId().size();
        assertEquals(l_initCount-1,l_finalCount);
    }
}