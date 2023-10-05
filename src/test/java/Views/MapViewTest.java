package Views;

import Models.GameState;
import Models.Map;
import Services.MapService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MapView.java
 * @author Harshil Prajapati
 */
class MapViewTest {

    /**
     * Test to check the method showMap
     */
    @Test
    void showMap() {
        MapService l_service = new MapService();
        GameState l_gameState = new GameState();
        Map l_map = l_service.loadMap(l_gameState, l_service.getFilePath("canada.map"));
        MapView l_mapView = new MapView(l_gameState);

        l_mapView.showMap();
        assertEquals(6, l_mapView.d_continents.size());
    }
}