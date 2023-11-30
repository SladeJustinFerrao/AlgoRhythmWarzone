package Services;

import Controller.GameEngine;
import Models.GameState;
import Models.Map;
import Models.Phase;
import Models.StartUpPhase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapFileReaderTest {

    @Test
    void parseContinentsData() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();
        assertEquals("canada.map", l_phase.getD_gameState().getD_map().getD_mapFile());
    }
}