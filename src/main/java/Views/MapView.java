package Views;

import Models.Player;
import Models.GameState;
import Models.Map;
import Models.Country;
import Models.Continent;

import java.util.List;

/**
 * MapView class for Views
 */
public class MapView {
    /**
     * List of players
     */
    List<Player> d_players;
    /**
     * Current state of game
     */
    GameState d_gameState;
    /**
     * Current map
     */
    Map d_map;
    /**
     * List of countries
     */
    List<Country> d_countries;
    /**
     * List of continents
     */
    List<Continent> d_continents;
}
