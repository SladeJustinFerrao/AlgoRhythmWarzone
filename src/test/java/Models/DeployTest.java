package Models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class to test functionality of deploy class
 */
class DeployTest {

    /**
     * First player
     */
    Player d_player1;

    /**
     * Second player
     */
    Player d_player2;

    /**
     * First deploy order
     */
    Deploy d_deployOrder1;

    /**
     * Second deploy order
     */
    Deploy d_deployOrder2;

    /**
     * Game State
     */
    GameState d_gameState = new GameState();

    /**
     * Method to test deploy order and check if required armies are deployed or not
     */
    @Test
    public void testDeployOrderExecution() {
        d_player1 = new Player();
        d_player1.setPlayerName("A");
        d_player2 = new Player();
        d_player2.setPlayerName("B");

        List<Country> l_countryList = new ArrayList<>();
        l_countryList.add(new Country("India"));
        l_countryList.add(new Country("Canada"));
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList);

        List<Country> l_mapCountries = new ArrayList<>();
        Country l_country1 = new Country(1, "India", 1);
        Country l_country2 = new Country(2, "Canada", 2);
        l_country1.setD_armies(10);

        l_mapCountries.add(l_country1);
        l_mapCountries.add(l_country2);

        Map l_map = new Map();
        l_map.setD_countries(l_mapCountries);
        d_gameState.setD_map(l_map);

        d_deployOrder1 = new Deploy(d_player1, "India", 10);
        d_deployOrder2 = new Deploy(d_player2, "Canada", 20);

        //Testing the methods
        d_deployOrder1.execute(d_gameState);
        Country l_countryIndia = d_gameState.getD_map().getCountryByName("India");
        assertEquals(20, l_countryIndia.getD_armies());

        d_deployOrder2.execute(d_gameState);
        Country l_countryCanada = d_gameState.getD_map().getCountryByName("Canada");
        assertEquals(20, l_countryCanada.getD_armies());
    }

    /**
     * Method to test country name entered by player in deploy command to check if the entered country name belongs to player or not
     * If it does not belong to player then order will not be executed
     */
    @Test
    public void testValidateDeployOrderCountry() {
        d_player1 = new Player();
        d_player1.setPlayerName("A");
        d_player2 = new Player();
        d_player2.setPlayerName("B");

        List<Country> l_countryList = new ArrayList<>();
        l_countryList.add(new Country("India"));
        l_countryList.add(new Country("Canada"));
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList);

        List<Country> l_mapCountries = new ArrayList<>();
        Country l_country1 = new Country(1, "India", 1);
        Country l_country2 = new Country(2, "Canada", 2);
        l_country1.setD_armies(10);

        l_mapCountries.add(l_country1);
        l_mapCountries.add(l_country2);

        Map l_map = new Map();
        l_map.setD_countries(l_mapCountries);
        d_gameState.setD_map(l_map);

        d_deployOrder1 = new Deploy(d_player1, "India", 10);
        d_deployOrder2 = new Deploy(d_player2, "Canada", 20);

        //Testing the methods
        boolean l_actualBoolean = d_deployOrder1.valid(d_gameState);
        assertTrue(l_actualBoolean);
        boolean l_actualBoolean2 = d_deployOrder2.valid(d_gameState);
        assertTrue(l_actualBoolean2);
    }

    /**
     * Method to test deploy order logic to see if required order is created and armies are re-calculated
     */
    @Test
    public void testDeployOrder() {
        d_player1 = new Player();
        d_player1.setPlayerName("A");
        d_player2 = new Player();
        d_player2.setPlayerName("B");

        List<Country> l_countryList = new ArrayList<>();
        l_countryList.add(new Country("India"));
        l_countryList.add(new Country("Canada"));
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList);

        List<Country> l_mapCountries = new ArrayList<>();
        Country l_country1 = new Country(1, "India", 1);
        Country l_country2 = new Country(2, "Canada", 2);
        l_country1.setD_armies(10);

        l_mapCountries.add(l_country1);
        l_mapCountries.add(l_country2);

        Map l_map = new Map();
        l_map.setD_countries(l_mapCountries);
        d_gameState.setD_map(l_map);

        d_deployOrder1 = new Deploy(d_player1, "India", 10);
        d_deployOrder2 = new Deploy(d_player2, "Canada", 20);

        //Testing the methods
        Player l_player = new Player("A");
        l_player.setD_noOfUnallocatedArmies(10);
        Country l_country = new Country(1, "Japan", 1);
        l_player.setD_coutriesOwned(List.of(l_country));

        l_player.createDeployOrder("deploy Japan 4", l_player);

        assertEquals(6, l_player.getD_noOfUnallocatedArmies());
        assertEquals(1, l_player.getD_ordersToExecute().size());
        Deploy l_order = (Deploy) l_player.getD_ordersToExecute().get(0);
        assertEquals("Japan", l_order.d_targetCountryName);
        assertEquals(4, l_order.d_numberOfArmiesToPlace);
    }
}