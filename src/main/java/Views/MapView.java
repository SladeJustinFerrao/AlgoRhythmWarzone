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

    /**
     * Parameterized constructor to initialize Mapview without players
     * @param p_gameState current state of game
     */
    public MapView(GameState p_gameState) {
        d_gameState = p_gameState;
        d_map = p_gameState.getD_map();
        d_countries = p_gameState.getD_map().getD_countries();
        d_continents = p_gameState.getD_map().getD_continents();
    }

    /**
     * Parameterized constructor to initialize Mapview with players
     * @param p_gameState current state of game
     * @param p_players list of players
     */
    public MapView(GameState p_gameState, List<Player> p_players) {
        d_gameState = p_gameState;
        d_players = p_players;
        d_map = p_gameState.getD_map();
        d_countries = d_map.getD_countries();
        d_continents = d_map.getD_continents();
    }
}
