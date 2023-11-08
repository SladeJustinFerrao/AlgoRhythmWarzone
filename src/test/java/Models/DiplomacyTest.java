package Models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test functionality of diplomacy class
 */
class DiplomacyTest {
    /**
     * First Player
     */
    Player d_player1;

    /**
     * Second Player
     */
    Player d_player2;

    /**
     * Bomb order
     */
    Bomb d_bombOrder;

    /**
     * Diplomacy order
     */
    Diplomacy d_diplomacyOrder;

    /**
     * Game state
     */
    GameState d_gameState;

    /**
     * Method to test if diplomacy works or not
     */
    @Test
    public void testDiplomacyExecution() {
        d_gameState = new GameState();
        d_player1 = new Player();
        d_player1.setPlayerName("A");
        d_player2 = new Player("B");

        List<Country> l_countryList = new ArrayList<>();
        Country l_country = new Country(0, "France", 1);
        l_country.setD_armies(9);
        l_countryList.add(l_country);

        Country l_countryNeighbour = new Country(1, "Belgium", 1);
        l_countryNeighbour.addNeighbourToCountry(0);
        l_country.addNeighbourToCountry(1);
        l_countryNeighbour.setD_armies(10);
        l_countryList.add(l_countryNeighbour);

        List<Country> l_countryList2 = new ArrayList<>();
        Country l_countryNotNeighbour = new Country(2, "Spain", 1);
        l_countryNotNeighbour.setD_armies(15);
        l_countryList2.add(l_countryNotNeighbour);

        Map l_map = new Map();
        l_map.setD_countries(new ArrayList<>(){{ addAll(l_countryList); addAll(l_countryList2); }});

        d_gameState.setD_map(l_map);
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList2);
        List<Player> l_playerList = new ArrayList<>();
        l_playerList.add(d_player1);
        l_playerList.add(d_player2);
        d_gameState.setD_players(l_playerList);
        d_diplomacyOrder = new Diplomacy(d_player2.getPlayerName(), d_player1);
        d_bombOrder = new Bomb(d_player2, "France"){};

        //Testing the methods
        d_diplomacyOrder.execute(d_gameState);
        assertEquals(d_player1.d_negotiatedWith.get(0), d_player2);
    }

    /**
     * Method to test if the orders after negotiation works or not
     */
    @Test
    public void NegotiationWorking() {
        d_gameState = new GameState();
        d_player1 = new Player();
        d_player1.setPlayerName("A");
        d_player2 = new Player("B");

        List<Country> l_countryList = new ArrayList<>();
        Country l_country = new Country(0, "France", 1);
        l_country.setD_armies(9);
        l_countryList.add(l_country);

        Country l_countryNeighbour = new Country(1, "Belgium", 1);
        l_countryNeighbour.addNeighbourToCountry(0);
        l_country.addNeighbourToCountry(1);
        l_countryNeighbour.setD_armies(10);
        l_countryList.add(l_countryNeighbour);

        List<Country> l_countryList2 = new ArrayList<>();
        Country l_countryNotNeighbour = new Country(2, "Spain", 1);
        l_countryNotNeighbour.setD_armies(15);
        l_countryList2.add(l_countryNotNeighbour);

        Map l_map = new Map();
        l_map.setD_countries(new ArrayList<>(){{ addAll(l_countryList); addAll(l_countryList2); }});

        d_gameState.setD_map(l_map);
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList2);
        List<Player> l_playerList = new ArrayList<>();
        l_playerList.add(d_player1);
        l_playerList.add(d_player2);
        d_gameState.setD_players(l_playerList);
        d_diplomacyOrder = new Diplomacy(d_player2.getPlayerName(), d_player1);
        d_bombOrder = new Bomb(d_player2, "France"){};

        //Testing the methods
        d_diplomacyOrder.execute(d_gameState);
        d_bombOrder.execute(d_gameState);
        assertEquals("Log: Bomb card order : bomb France is not executed as B has negotiation pact with the target country's player!",d_gameState.getRecentLog().trim());
    }
}