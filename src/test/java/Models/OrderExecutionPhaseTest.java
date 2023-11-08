package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Controller.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit test class for the Order Execution Phase functionality.
 */
public class OrderExecutionPhaseTest {
    private Player d_player1;
    private Player d_player2;
    private GameState d_gameState;

    /**
     * Sets up the initial state before each test case execution.
     *
     * @throws Exception If an error occurs during setup.
     */
    @BeforeEach
    public void setup() throws Exception {
        d_gameState = new GameState();
        d_player1 = new Player();
        d_player1.setPlayerName("a");
        d_player2 = new Player();
        d_player2.setPlayerName("b");

        List<Country> l_countryList = new ArrayList<>();
        Country l_country = new Country(0, "France", 1);
        l_country.setD_armies(9);
        l_countryList.add(l_country);

        Country l_countryNeighbour = new Country(1, "Belgium", 1);
        l_countryNeighbour.addNeighbourToCountry(0);
        l_country.addNeighbourToCountry(1);
        l_countryNeighbour.setD_armies(10);
        l_countryList.add(l_countryNeighbour);

        d_player1.setD_coutriesOwned(l_countryList);

        Map l_map = new Map();
        l_map.setD_countries(l_countryList);
        d_gameState.setD_map(l_map);
        d_gameState.setD_players(Arrays.asList(d_player1, d_player2));
    }

    /**
     * Tests whether the game ends when a player conquers all countries.
     */
    @Test
    public void testEndOfTheGame() {
        OrderExecutionPhase l_orderExec = new OrderExecutionPhase(new GameEngine(), d_gameState);
        assertTrue(l_orderExec.checkEndOftheGame(d_gameState));
    }
}
