package Models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is used to test functionality of Advance order.
 */
public class AdvanceTest {
    /**
     * Recent GameState
     */
    GameState d_gameState = new GameState();

    /**
     * Checks whether advance order given is invalid.
     */
    @Test
    public void testInvalidAdvanceOrder() {
        Player l_player = new Player("Darshan");
        Country l_country1 = new Country("India");
        l_country1.setD_armies(12);
        Country l_country2 = new Country("Canada");
        l_country2.setD_armies(10);
        List<Country> l_countries = Arrays.asList(l_country1, l_country2);
        l_player.setD_coutriesOwned(l_countries);

        assertFalse(new Advance(l_player, "India", "France", 15).valid(d_gameState));
        assertFalse(new Advance(l_player, "Italy", "France", 10).valid(d_gameState));
        assertTrue(new Advance(l_player, "India", "France", 10).valid(d_gameState));
    }

    /**
     * Checks if Attacker has won battle, countries and armies are updated correctly
     * or not.
     */
    @Test
    public void testAttackersWin() {
        Player l_sourcePlayer = new Player("Darshan");
        Country l_country1 = new Country("India");
        l_country1.setD_armies(7);
        List<Country> l_s1 = new ArrayList<>();
        l_s1.add(l_country1);
        l_sourcePlayer.setD_coutriesOwned(l_s1);

        Player l_targetPlayer = new Player("Slade");
        Country l_country2 = new Country("Canada");
        l_country2.setD_armies(4);
        List<Country> l_s2 = new ArrayList<>();
        l_s2.add(l_country2);
        l_targetPlayer.setD_coutriesOwned(l_s2);

        Advance l_advance = new Advance(l_sourcePlayer, "India", "Canada", 5);
        l_advance.handleSurvivingArmies(5, 0, l_country1, l_country2, l_targetPlayer);

        assertEquals(l_targetPlayer.getD_coutriesOwned().size(), 0);
        assertEquals(l_sourcePlayer.getD_coutriesOwned().size(), 2);
        String attackers = String.valueOf(l_sourcePlayer.getD_coutriesOwned().get(1).getD_armies());
        assertEquals(attackers, "5");
    }

}
