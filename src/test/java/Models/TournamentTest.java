package Models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;

import Controller.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TournamentTest {
    /**
     * First Player.
     */
    Player d_player1;

    /**
     * Second Player.
     */
    Player d_player2;

    /**
     * Game State.
     */
    GameState d_gameState;

    /**
     * Setup before each test case.
     *
     * @throws Exception Exception
     */
    @BeforeEach
    public void setup() throws Exception {
        d_gameState = new GameState();
        d_player1 = new Player("a");
        d_player1.setStrategy(new RandomPlayer());
        d_player2 = new Player("b");
        d_player2.setStrategy(new RandomPlayer());

        d_gameState.setD_players(Arrays.asList(d_player1, d_player2));
    }

    /**
     * Tests tournament command in case of invalid map arguments passed.
     *
     * @throws Exception Exception
     */
    @Test
    public void testInvalidMapArgs() throws Exception {
        Tournament l_tournament = new Tournament();
        assertFalse(l_tournament.parseTournamentCommand(d_gameState, "M",
                "test.map test123.map canada.map conquest.map swiss.map europe.map", new GameEngine()));
    }
    /**
     * Tests tournament command in case of invalid player arguments passed.
     *
     * @throws Exception Exception
     */
    @Test
    public void testInvalidPlayerStrategiesArgs() throws Exception {
        Tournament l_tournament = new Tournament();
        assertFalse(l_tournament.parseTournamentCommand(d_gameState, "P",
                "Random Human", new GameEngine()));
    }
    /**
     * Tests tournament command in case of invalid game arguments passed.
     *
     * @throws Exception Exception
     */
    @Test
    public void testInvalidNoOfGamesArgs() throws Exception {
        Tournament l_tournament = new Tournament();
        assertFalse(l_tournament.parseTournamentCommand(d_gameState, "G",
                "6", new GameEngine()));
    }
    /**
     * Tests tournament command in case of invalid turns arguments passed.
     *
     * @throws Exception Exception
     */
    @Test
    public void testInvalidNoOfTurnsArgs() throws Exception {
        Tournament l_tournament = new Tournament();
        assertFalse(l_tournament.parseTournamentCommand(d_gameState, "D",
                "60", new GameEngine()));
    }

    /**
     * Checks if valid tournament command is passed and plays the tournament.
     *
     * @throws Exception Exception
     */
    @Test
    public void testValidTournament() throws Exception {
        Tournament l_tournament = new Tournament();
        GameEngine l_gameEngine = new GameEngine();
        l_tournament.parseTournamentCommand(d_gameState, "M",
                "europe.map canada.map", l_gameEngine);
        l_tournament.parseTournamentCommand(d_gameState, "P",
                "Aggressive Random", l_gameEngine);
        l_tournament.parseTournamentCommand(d_gameState, "G",
                "3", l_gameEngine);
        l_tournament.parseTournamentCommand(d_gameState, "D",
                "11", l_gameEngine);

        assertEquals(l_tournament.getD_gameStateList().size(), 6);
        assertEquals(l_tournament.getD_gameStateList().get(1).getD_map().getD_mapFile(), "canada.map");

        assertEquals(l_tournament.getD_gameStateList().get(0).getD_players().size(), 2);
        assertEquals(l_tournament.getD_gameStateList().get(0).getD_maxNumberOfTurns(), 11);

    }
}
