package Models;

import Services.MapService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContinentTest {

    /**
     * Test to see if the country is removed for all neighbour countries
     */
    @Test
    void removeCountryForAllNeighboursTest() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("canada.map"));

        List<Continent> l_continentList = l_map.getD_continents();
        List<Country> l_countriesList = l_continentList.get(0).getD_countries();
        int l_initCount = l_countriesList.get(0).getD_neighbourCountryId().size();
        l_continentList.get(0).removeCountryForAllNeighbours(8);
        int l_finalCount = l_countriesList.get(0).getD_neighbourCountryId().size();
        assertEquals(l_initCount-1,l_finalCount);
    }
}