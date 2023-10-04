package Models;

import Controller.GameEngine;
import Services.MapService;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void isCountriesConnectedTest() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("canada.map"));

        assertEquals(true,l_map.isCountriesConnected());
    }
    @Test
    void isContinentsConnectedTest() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("canada.map"));

        assertEquals(true,l_map.isContinentsConnected());
    }

}