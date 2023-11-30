package Models;

import java.io.IOException;
import java.util.*;
import java.util.Map;

/**
 * This is the class of an Aggressive Player.
 * An Aggressive player, gathers all his armies, attacks from his strongest
 * territory and deploys armies to maximize his forces on one country.
 */
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

    /**
     * Get the strongest country the player owns
     *
     * @param p_player    Player
     * @param p_gameState Current game state
     * @return Strongest country
     */
    public Country getStrongestCountry(Player p_player, GameState p_gameState) {
        List<Country> l_countriesOwnedByPlayer = p_player.getD_coutriesOwned();
        LinkedHashMap<Country, Integer> l_countryWithArmies = new LinkedHashMap<>();

        int l_largestNoOfArmies;
        Country l_country = null;
        for (Country country : l_countriesOwnedByPlayer) {
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
        Country l_randomSourceCountry = getRandomCountry(p_player.getD_coutriesOwned());
        //moveArmiesFromItsNeighbors(p_player, l_randomSourceCountry, p_gameState);

        Random l_random = new Random();
        Country l_randomTargetCountry = p_gameState.getD_map()
                .retrieveCountry(l_randomSourceCountry.getD_neighbourCountryId()
                        .get(l_random.nextInt(l_randomSourceCountry.getD_neighbourCountryId().size())));

        int l_armiesToSend = l_randomSourceCountry.getD_currentArmies() >= 1 ? l_randomSourceCountry.getD_currentArmies() : 0;
        if(l_armiesToSend==0){
            return "nocommand";
        }
        l_randomSourceCountry.setD_currentArmies(l_randomSourceCountry.getD_currentArmies()-l_armiesToSend);
        return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_randomTargetCountry.getD_countryName()
                + " " + l_armiesToSend;
    }

    /**
     * Move armies from neighbor to maximize number of armies attacking.
     *
     * @param p_player              Player
     * @param p_randomSourceCountry Source country
     * @param p_gameState           Game state
     */
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

    /**
     * This method returns random country.
     *
     * @param p_listOfCountries List of countries
     * @return Random country
     */
    private Country getRandomCountry(List<Country> p_listOfCountries) {
        Random l_random = new Random();
        Country l_country;
        int count = 0;
        do{
            l_country = p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
            if(l_country.getD_currentArmies()>0){
                break;
            }
            count++;
        }while(count < p_listOfCountries.size());
        return l_country;
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
        Random l_random = new Random();
        Country l_StrongestSourceCountry = getStrongestCountry(p_player, d_gameState);
        int count = 0;
        Country l_randomTargetCountry;
        do {
            l_randomTargetCountry = p_gameState.getD_map()
                    .retrieveCountry(l_StrongestSourceCountry.getD_neighbourCountryId()
                            .get(l_random.nextInt(l_StrongestSourceCountry.getD_neighbourCountryId().size())));
            if(!p_player.getD_coutriesOwned().contains(l_randomTargetCountry)){
                break;
            }
            count++;
        }while(count<l_StrongestSourceCountry.getD_neighbourCountryId().size());

        Player l_randomPlayer = getRandomEnemyPlayer(p_player, p_gameState);

        int l_armiesToSend = l_StrongestSourceCountry.getD_armies() > 1 ? l_StrongestSourceCountry.getD_armies() : 1;

        switch (p_cardName) {
            case "bomb":
                return "bomb " + l_randomTargetCountry.getD_countryName();
            case "blockade":
                return "blockade " + l_StrongestSourceCountry.getD_countryName();
            case "airlift":
                return "airlift " + l_StrongestSourceCountry.getD_countryName() + " "
                        + getRandomCountry(p_player.getD_coutriesOwned()).getD_countryName() + " " + l_armiesToSend;
            case "negotiate":
                return "negotiate" + " " + l_randomPlayer.getPlayerName();
        }
        return null;
    }

    /**
     * Get random enemy player.
     *
     * @param p_player    Player
     * @param p_gameState Game state
     * @return Random enemy player
     */
    private Player getRandomEnemyPlayer(Player p_player, GameState p_gameState) {
        ArrayList<Player> l_playerList = new ArrayList<>();
        Random l_random = new Random();

        for (Player l_player : p_gameState.getD_players()) {
            if (!l_player.equals(p_player))
                l_playerList.add(p_player);
        }
        return l_playerList.get(l_random.nextInt(l_playerList.size()));
    }

    /**
     * This method returns player's behavior
     *
     * @return String of Player's behavior
     */
    @Override
    public String getPlayerBehavior() {
        return "Aggressive";
    }
}
