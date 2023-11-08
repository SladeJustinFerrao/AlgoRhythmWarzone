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
    List<Player> d_players;
    GameState d_gameState;
    Map d_map;
    List<Country> d_countries;
    List<Continent> d_continents;

    /**
     * Constructor to initialize Mapview
     * @param p_gameState current state of game
     */
    public MapView(GameState p_gameState) {
        d_gameState = p_gameState;
        d_map = p_gameState.getD_map();
        d_players = p_gameState.getD_players();
        d_countries = d_map.getD_countries();
        d_continents = d_map.getD_continents();
    }

    /**
     * Renders a centered string for heading
     * @param p_width  defined width in formatting
     * @param p_string string to show
     */
    private void renderCenteredString(int p_width, String p_string) {
        String l_centeredString = String.format("%-" + p_width  + "s",
                String.format("%" + (p_string.length() + (p_width - p_string.length()) / 2) + "s", p_string));
        System.out.format(l_centeredString + "\n");
    }

    /**
     * Separator to separate the heading
     */
    private void renderSeparator() {
        StringBuilder l_separator = new StringBuilder();
        for(int i=0; i<GameConstants.CONSOLE_WIDTH - 2; i++) {
            l_separator.append("-");
        }
        System.out.format("+%s+%n", l_separator.toString());
    }

    /**
     * Render continent name with centered string and separator
     * @param p_continentName continent name to show
     */
    private void renderContinentName(String p_continentName) {
        String l_continentName = p_continentName + " ( " + GameConstants.CONTROL_VALUE + " : " + d_gameState.getD_map().retrieveContinent(p_continentName).getD_continentValue() + " ) ";
        renderSeparator();
        renderCenteredString(GameConstants.CONSOLE_WIDTH, l_continentName);
        renderSeparator();
    }

    /**
     * Renders a country name in format
     * @param p_index index of countries
     * @param p_countryName country name to show
     * @return returns the string as formatted
     */
    private String getCountryNameFormatted(int p_index, String p_countryName) {
        String l_indexedString = String.format("%02d. %s", p_index, p_countryName);
        if (d_players != null) {
            String l_armies = "( " + GameConstants.ARMIES + " : " +
                    getArmiesOfCountry(p_countryName) + " ) " + "Player : " + getPlayerWhoOwnsCountry(p_countryName).getPlayerName();
            l_indexedString = String.format("%02d. %s %s", p_index, p_countryName, l_armies);
        }
        return String.format("%-30s", l_indexedString);
    }

    /**
     * Renders neighbour countries in format
     * @param p_countryName        country name to show
     * @param p_neighbourCountries list of neighbour countries to show
     */
    private void renderNeighbourCountryNameFormatted(String p_countryName, List<Country> p_neighbourCountries) {
        StringBuilder l_separatedCountries = new StringBuilder();
        for (int i = 0; i < p_neighbourCountries.size(); i++) {
            l_separatedCountries.append(p_neighbourCountries.get(i).getD_countryName());
            if (i < p_neighbourCountries.size() - 1) {
                l_separatedCountries.append(", ");
            }
        }
        String l_neighbourCountry = GameConstants.CONNECTIVITY + " : " + l_separatedCountries.toString();
        System.out.println(l_neighbourCountry);
        System.out.println();
    }

    /**
     * Method to show cards owned by the player
     * @param p_player player instance
     */
    private void renderCardsOwnedByPlayer(Player p_player) {
        StringBuilder l_cards = new StringBuilder();
        for(int i=0; i<p_player.getD_cardsOwnedByPlayer().size(); i++) {
            l_cards.append(p_player.getD_cardsOwnedByPlayer().get(i));
            if(i < p_player.getD_cardsOwnedByPlayer().size()-1) {
                l_cards.append(", ");
            }
        }
        String l_cardsOwnedByPlayer = "Cards Owned : "+ l_cards.toString();
        System.out.println(l_cardsOwnedByPlayer);
        System.out.println();
    }

    /**
     * Method to get the player who owns the country
     * @param p_countryName name of country
     * @return player object
     */
    private Player getPlayerWhoOwnsCountry(String p_countryName) {
        if (d_players != null) {
            for (Player p : d_players) {
                if (p.getCountryNames().contains(p_countryName)) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Renders the information of the player in format
     * @param p_index  index of the player
     * @param p_player player object
     */
    private void renderPlayerInformation(Integer p_index, Player p_player) {
        String l_playerInformation = String.format("%02d. %-8s", p_index, p_player.getPlayerName());
        System.out.print(l_playerInformation);
    }

    /**
     * Renders players in format
     */
    private void renderPlayers() {
        int l_count = 0;
        renderSeparator();
        renderCenteredString(GameConstants.CONSOLE_WIDTH, "GAME PLAYERS");
        renderSeparator();
        for (Player p : d_players) {
            l_count++;
            renderPlayerInformation(l_count, p);
            renderCardsOwnedByPlayer(p);
        }
        System.out.println();
    }

    /**
     * Method to get the player who owns the continent
     * @param p_continentName name of continent
     * @return player object
     */
    private Player getPlayerWhoOwnsContinent(String p_continentName) {
        if (d_players != null) {
            for (Player p : d_players) {
                if (p.getContinentNames() != null && p.getContinentNames().contains(p_continentName)) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Get the number of armies for the country
     * @param p_countryName name of country
     * @return number of armies
     */
    private Integer getArmiesOfCountry(String p_countryName) {
        Integer l_armies = d_gameState.getD_map().getCountryByName(p_countryName).getD_armies();
        if (l_armies == null) {
            return 0;
        }
        return l_armies;
    }

    /**
     * Method to get unallocated player armies
     * @param p_player player object
     * @return string to fit with player
     */
    private String getPlayerArmies(Player p_player) {
        return "( Unallocated Armies : " + p_player.getD_noOfUnallocatedArmies() + ")";
    }

    /**
     * Method to display the list of countries and continents along with the current game state
     */
    public void showMap() {
        if (d_players != null) {
            renderPlayers();
        }
        if (d_continents != null) {
            d_continents.forEach(l_continent -> {
                renderContinentName(l_continent.getD_continentName());
                List<Country> l_continentCountries = l_continent.getD_countries();
                final int[] l_countryIndex = { 1 };
                if (l_continentCountries.size() != 0) {
                    l_continentCountries.forEach(l_country -> {
                        String l_countryNameFormatted = getCountryNameFormatted(l_countryIndex[0]++, l_country.getD_countryName());
                        System.out.println(l_countryNameFormatted);
                        try {
                            List<Country> l_neighbourCountries = d_map.getNeighbourCountry(l_country);
                            renderNeighbourCountryNameFormatted(l_country.getD_countryName(), l_neighbourCountries);
                        } catch (Exception l_invalidMap) {
                            System.out.println(l_invalidMap.getMessage());
                        }
                    });
                } else {
                    System.out.println("There is no countries in the continent !!!");
                }
            });
        } else {
            System.out.println("There is no continents to display !!!");
        }
    }
}