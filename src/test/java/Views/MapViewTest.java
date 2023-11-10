package Views;

import Controller.GameEngine;
import Models.GameState;
import Models.Map;
import Models.Phase;
import Models.StartUpPhase;
import Services.MapService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MapView.java
 */
class MapViewTest {

    /**
     * Test to check the method showMap
     */
    @Test
    void showMap() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();
        MapView l_mapView = new MapView(l_phase.getD_gameState());

        l_mapView.showMap();
        assertEquals(6, l_mapView.d_continents.size());
    }
}