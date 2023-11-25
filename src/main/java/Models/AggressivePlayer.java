package Models;

import java.io.IOException;
import java.util.*;
import java.util.Map;

public class AggressivePlayer extends PlayerBehavior {

    /**
     * List containing deploy order countries.
     */
    ArrayList<Country> d_deployCountries = new ArrayList<Country>();

    /**
     * This method creates new order for different players with different strategies
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @return Order object of Order class
     * @throws IOException Exception
     */
    @Override
    public String createOrder(Player p_player, GameState p_gameState) throws IOException {
        String l_command;

        if (p_player.getD_noOfUnallocatedArmies() > 0) {
            l_command = createDeployOrder(p_player, p_gameState);
        } else {
            if (p_player.getD_cardsOwnedByPlayer().size() > 0) {
                Random l_random = new Random();
                int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size() + 1);
                if (l_randomIndex == p_player.getD_cardsOwnedByPlayer().size()) {
                    l_command = createAdvanceOrder(p_player, p_gameState);
                } else {
                    l_command = createCardOrder(p_player, p_gameState,
                            p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
                }
            } else {
                l_command = createAdvanceOrder(p_player, p_gameState);
            }
        }
        return l_command;
    }

    /**
     * Deploy orders as per the player's strategy
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @return String of order
     */
    @Override
    public String createDeployOrder(Player p_player, GameState p_gameState) {
        Random l_Random = new Random();
        Country l_strongestCountry = getStrongestCountry(p_player, d_gameState);
        d_deployCountries.add(l_strongestCountry);
        int l_armiesToDeploy = l_Random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;
        return String.format("deploy %s %d", l_strongestCountry.getD_countryName(), l_armiesToDeploy);
    }

    private Country getStrongestCountry(Player p_player, GameState p_gameState) {
        List<Country> l_countriesOwnedByPlayer = p_player.getD_coutriesOwned();
        return calculateStrongestCountry(l_countriesOwnedByPlayer);
    }

    private Country calculateStrongestCountry(List<Country> p_listOfCountries) {
        LinkedHashMap<Country, Integer> l_countryWithArmies = new LinkedHashMap<Country, Integer>();

        int l_largestNoOfArmies;
        Country l_country = null;
        for (Country country : p_listOfCountries) {
            l_countryWithArmies.put(country, country.getD_armies());
        }
        l_largestNoOfArmies = Collections.max(l_countryWithArmies.values());
        for (Map.Entry<Country, Integer> entry : l_countryWithArmies.entrySet()) {
            if (entry.getValue().equals(l_largestNoOfArmies)) {
                return entry.getKey();
            }
        }
        return l_country;
    }

    /**
     * Advance orders as per the player's strategy
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @return String of order
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameState p_gameState) {
        Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
        moveArmiesFromItsNeighbors(p_player, l_randomSourceCountry, p_gameState);

        Random l_random = new Random();
        Country l_randomTargetCountry = p_gameState.getD_map()
                .retrieveCountry(l_randomSourceCountry.getD_neighbourCountryId()
                        .get(l_random.nextInt(l_randomSourceCountry.getD_neighbourCountryId().size())));

        int l_armiesToSend = l_randomSourceCountry.getD_armies() > 1 ? l_randomSourceCountry.getD_armies() : 1;

        return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_randomTargetCountry.getD_countryName()
                + " " + l_armiesToSend;
    }

    private void moveArmiesFromItsNeighbors(Player p_player, Country p_randomSourceCountry, GameState p_gameState) {
        List<Integer> l_adjacentCountryIds = p_randomSourceCountry.getD_neighbourCountryId();
        List<Country> l_listOfNeighbors = new ArrayList<>();
        for (int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++) {
            Country l_country = p_gameState.getD_map().retrieveCountry(p_randomSourceCountry.getD_neighbourCountryId().get(l_index));
            if (p_player.getD_coutriesOwned().contains(l_country)) {
                l_listOfNeighbors.add(l_country);
            }
        }

        int l_ArmiesToMove = 0;
        for (Country l_con : l_listOfNeighbors) {
            l_ArmiesToMove += p_randomSourceCountry.getD_armies() > 0
                    ? p_randomSourceCountry.getD_armies() + (l_con.getD_armies())
                    : (l_con.getD_armies());

        }
        p_randomSourceCountry.setD_armies(l_ArmiesToMove);
    }

    private Country getRandomCountry(ArrayList<Country> p_listOfCountries) {
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }

    /**
     * Card orders as per the player's strategy
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @param p_cardName  Card name for created Order
     * @return String of order
     */
    @Override
    public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
        return null;
    }

    /**
     * This method returns player's behavior
     *
     * @return String of Player's behavior
     */
    @Override
    public String getPlayerBehavior() {
        return null;
    }
}
