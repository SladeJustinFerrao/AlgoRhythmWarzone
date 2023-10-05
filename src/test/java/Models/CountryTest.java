package Models;

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
    void removeNeighbourFromCountry() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("canada.map"));
        List<Country> l_countries = l_map.getD_countries();
        int l_initCount = l_countries.get(0).getD_neighbourCountryId().size();
        l_countries.get(0).removeNeighbourFromCountry(8);
        int l_finalCount = l_countries.get(0).getD_neighbourCountryId().size();
        assertEquals(l_initCount-1,l_finalCount);
    }

}