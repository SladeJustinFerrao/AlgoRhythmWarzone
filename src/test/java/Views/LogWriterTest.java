package Views;

import Controller.GameEngine;
import Models.GameState;
import Models.Phase;
import Models.StartUpPhase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogWriterTest {

    @Test
    void updateTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        assert(l_phase.getD_gameState().getRecentLog().contains("already exists and is loaded for editing"));
    }
}