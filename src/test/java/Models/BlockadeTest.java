package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class is used to test functionality of Blockade class functions.
 */
public class BlockadeTest {

    /**
     * First Player.
     */
    Player d_player1;

    /**
     * Second Player.
     */
    Player d_player2;

    /**
     * Neutral Player.
     */
    Player d_neutralPlayer;

    /**
     * Blockade Order One.
     */
    Blockade d_blockadeOrder1;

    /**
     * Blockade Order Two.
     */
    Blockade d_blockadeOrder2;

    /**
     * Blockade Order Three.
     */
    Blockade d_blockadeOrder3;

    /**
     * Target Country.
     */
    String d_targetCountry;

    /**
     * Order List.
     */
    List<Order> d_order_list;

    /**
     * Game State.
     */
    GameState d_gameState;

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setup() {
        d_gameState = new GameState();
        d_order_list = new ArrayList<Order>();
        d_player1 = new Player();
        d_player1.setPlayerName("yug");
        d_player2 = new Player();
        d_player2.setPlayerName("darshan");
        d_neutralPlayer = new Player();
        d_neutralPlayer.setPlayerName("Neutral");

        List<Country> l_countryList = new ArrayList<Country>();
        l_countryList.add(new Country("India"));
        l_countryList.add(new Country("Canada"));
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList);
        d_neutralPlayer.setD_coutriesOwned(l_countryList);

        List<Country> l_mapCountries = new ArrayList<Country>();
        Country l_country1 = new Country(1, "Canada", 1);
        Country l_country2 = new Country(2, "India", 2);
        l_country1.setD_armies(10);
        l_country2.setD_armies(5);

        l_mapCountries.add(l_country1);
        l_mapCountries.add(l_country2);

        Map l_map = new Map();
        l_map.setD_countries(l_mapCountries);
        d_gameState.setD_map(l_map);

        List<Player> l_playerList = new ArrayList<Player>();
        l_playerList.add(d_neutralPlayer);
        d_gameState.setD_players(l_playerList);

        d_blockadeOrder1 = new Blockade(d_player1, "India");
        d_blockadeOrder2 = new Blockade(d_player1, "USA");

        d_order_list.add(d_blockadeOrder1);
        d_order_list.add(d_blockadeOrder2);

        d_player2.setD_ordersToExecute(d_order_list);
        d_blockadeOrder3 = new Blockade(d_player2, "India");

    }

    /**
     * Test Validation of Blockade Order.
     */
    @Test
    public void testValidBlockadeOrder() {

        boolean l_actualBoolean = d_blockadeOrder1.valid(d_gameState);
        assertFalse(l_actualBoolean);

        boolean l_actualBoolean2 = d_blockadeOrder2.valid(d_gameState);
        assertTrue(l_actualBoolean2);
    }
}