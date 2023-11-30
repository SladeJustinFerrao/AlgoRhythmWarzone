package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the working of Aggressive Player Behavior.
 */
public class AggressivePlayerTest {

    /**
     * aggressive Player to test.
     */
    Player d_player;

    /**
     * Strategy of Player.
     */
    PlayerBehavior d_playerBehaviorStrategy;

    /**
     * Aggressive player strategy.
     */
    AggressivePlayer d_aggressivePlayer = new AggressivePlayer();

    /**
     * Game State.
     */
    GameState d_gameState = new GameState();

    /**
     * Country.
     */
    Country d_country1;

    /**
     * Setup For testing Aggressive Behavior Strategy.
     */
    @BeforeEach
    public void setup() {
        this.d_country1 = new Country(1, "Spain", 1);
        Country l_country2 = new Country(1, "France", 1);
        Country l_country3 = new Country(1, "Portugal", 1);

        l_country2.setD_countryId(3);
        d_country1.addNeighbourToCountry(3);

        l_country3.setD_countryId(2);
        d_country1.addNeighbourToCountry(2);

        this.d_country1.setD_armies(10);
        l_country2.setD_armies(3);
        l_country3.setD_armies(2);

        ArrayList<Country> l_list = new ArrayList<Country>();
        l_list.add(d_country1);
        l_list.add(l_country2);
        l_list.add(l_country3);

        d_playerBehaviorStrategy = new AggressivePlayer();
        d_player = new Player("Bhoomi");
        d_player.setD_coutriesOwned(l_list);
        d_player.setStrategy(d_playerBehaviorStrategy);
        d_player.setD_noOfUnallocatedArmies(8);

        List<Player> l_listOfPlayer = new ArrayList<Player>();
        l_listOfPlayer.add(d_player);

        Map l_map = new Map();
        l_map.setD_countries(l_list);
        l_map.setD_countries(l_list);
        d_gameState.setD_map(l_map);
        d_gameState.setD_players(l_listOfPlayer);
    }

    /**
     * Checks if it creates an Order String and first order is deploy.
     *
     * @throws IOException Exception
     */
    @Test
    public void testOrderCreation() throws IOException {
        assertEquals("deploy", d_player.getPlayerOrder(d_gameState).split(" ")[0]);
    }

    /**
     * Check if aggressive player deploy armies on weakest country or not.
     */
    @Test
    public void testStrongestCountry() {
        assertEquals("Spain", d_aggressivePlayer.getStrongestCountry(d_player, d_gameState).getD_countryName());
    }
}
