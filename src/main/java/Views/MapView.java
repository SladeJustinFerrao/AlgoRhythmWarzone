package Views;

import Models.Player;
import Models.GameState;
import Models.Map;
import Models.Country;
import Models.Continent;
import Constants.GameConstants;

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

    /**
     * Renders a centered string for heading
     * @param p_width defined width in formatting
     * @param p_string string to show
     */
    private void renderCenteredString(int p_width, String p_string) {
        String l_centeredString = String.format("%-" + p_width + "s", String.format("%" + (p_string.length() + (p_width - p_string.length()) / 2) + "s", p_string));
        System.out.format(l_centeredString + "\n");
    }

    /**
     * Separator to separate the heading
     */
    private void renderHeadingSeparator() {
        System.out.format("+%s+%n", "-".repeat(GameConstants.CONSOLE_WIDTH - 2));
    }
}
