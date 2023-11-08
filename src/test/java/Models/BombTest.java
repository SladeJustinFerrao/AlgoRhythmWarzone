package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is used to test functionality of Bomb class functions.
 */
public class BombTest {

    /**
     * Player 1 object.
     */
    Player d_player1;

    /**
     * Player 2 object.
     */
    Player d_player2;

    /**
     * Bomb Order1.
     */
    Bomb d_bombOrder1;

    /**
     * Bomb Order2.
     */
    Bomb d_bombOrder2;

    /**
     * Bomb Order3.
     */
    Bomb d_bombOrder3;

    /**
     * Bomb Order4.
     */
    Bomb d_bombOrder4;

    Order deployOrder;

    /**
     * name of the target country.
     */
    String d_targetCountry;

    /**
     * list of orders.
     */
    List<Order> d_order_list;

    /**
     * Game State object.
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
        d_player1.setPlayerName("Darshan");
        d_player2 = new Player();
        d_player2.setPlayerName("Harshil");

        List<Country> l_countryList = new ArrayList<Country>();
        l_countryList.add(new Country("Finland"));
        l_countryList.add(new Country("Norway"));
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList);

        List<Country> l_mapCountries = new ArrayList<Country>();
        Country l_country1 = new Country(1, "Finland", 1);
        Country l_country2 = new Country(2, "Norway", 2);
        Country l_country3 = new Country(2, "Japan", 2);
        Country l_country4 = new Country(2, "India", 2);
        Country l_country5 = new Country(2, "Canada", 2);

        l_country3.setD_armies(4);
        l_country4.setD_armies(15);
        l_country5.setD_armies(1);

        l_mapCountries.add(l_country1);
        l_mapCountries.add(l_country2);
        l_mapCountries.add(l_country3);
        l_mapCountries.add(l_country4);
        l_mapCountries.add(l_country5);

        Map l_map = new Map();
        l_map.setD_countries(l_mapCountries);
        d_gameState.setD_map(l_map);
        d_bombOrder1 = new Bomb(d_player1, "Japan");
        d_bombOrder2 = new Bomb(d_player1, "Norway");
        d_bombOrder3 = new Bomb(d_player1, "India");
        d_bombOrder4 = new Bomb(d_player1, "Canada");

        d_order_list.add(d_bombOrder1);
        d_order_list.add(d_bombOrder2);

        d_player2.setD_ordersToExecute(d_order_list);

    }

    /**
     * Test validation of bomb card.
     */
    @Test
    public void testValidBombOrder() {

        // Player cannot bomb own territory
        boolean l_actualBoolean = d_bombOrder1.valid(d_gameState);
        assertTrue(l_actualBoolean);

        // fail if target country is owned by player
        boolean l_actualBoolean1 = d_bombOrder2.valid(d_gameState);
        assertFalse(l_actualBoolean1);
    }

}