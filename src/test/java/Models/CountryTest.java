package Models;

import Controller.GameEngine;
import Services.MapService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Country.java
 */
class CountryTest {

    /**
     * Test to check whether neighbour is removed or not
     */
    @Test
    void removeNeighbourFromCountry() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();
        List<Country> l_countries = l_map.getD_countries();
        int l_initCount = l_countries.get(0).getD_neighbourCountryId().size();
        l_countries.get(0).removeNeighbourFromCountry(8);
        int l_finalCount = l_countries.get(0).getD_neighbourCountryId().size();
        assertEquals(l_initCount-1,l_finalCount);
    }

}