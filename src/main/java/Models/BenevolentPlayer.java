package Models;

import java.util.*;
import java.util.Map;

/**
 * Class for benevolent player who will only defend, never attack
 */
public class BenevolentPlayer extends PlayerBehavior {
    /**
     * List containing deploy order countries
     */
    ArrayList<Country> d_deployCountries = new ArrayList<>();

    /**
     * Method to create a new order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String
     */
    @Override
    public String createOrder(Player p_Player, GameState p_gameState) {
        String l_command;
        if(!checkIfArmiesDeployed(p_Player)) {
            if(p_Player.getD_noOfUnallocatedArmies() > 0) {
                l_command = createDeployOrder(p_Player, p_gameState);
            } else {
                l_command = createAdvanceOrder(p_Player, p_gameState);
            }
        } else {
            if(!p_Player.getD_cardsOwnedByPlayer().isEmpty()) {
                System.out.println("Enter Card Logic : ");
                int i_index = (int) (Math.random() * 3) + 1;
                switch(i_index) {
                    case 1:
                        System.out.println("Deploy !");
                        l_command = createDeployOrder(p_Player, p_gameState);
                        break;
                    case 2:
                        System.out.println("Advance !");
                        l_command = createAdvanceOrder(p_Player, p_gameState);
                        break;
                    case 3:
                        if(p_Player.getD_cardsOwnedByPlayer().size() == 1) {
                            System.out.println("Cards !");
                            l_command = createCardOrder(p_Player, p_gameState, p_Player.getD_cardsOwnedByPlayer().get(0));
                            break;
                        } else {
                            Random l_random = new Random();
                            int l_randomIndex = l_random.nextInt(p_Player.getD_cardsOwnedByPlayer().size());
                            l_command = createCardOrder(p_Player, p_gameState, p_Player.getD_cardsOwnedByPlayer().get(l_randomIndex));
                            break;
                        }
                    default:
                        l_command = createAdvanceOrder(p_Player, p_gameState);
                        break;
                }
            } else {
                Random l_random = new Random();
                Boolean l_randomBoolean = l_random.nextBoolean();
                if(l_randomBoolean) {
                    System.out.println("Without Card Deploy Logic");
                    l_command = createDeployOrder(p_Player, p_gameState);
                } else {
                    System.out.println("Without Card Advance Logic");
                    l_command = createAdvanceOrder(p_Player, p_gameState);
                }
            }
        }
        return l_command;
    }

    /**
     * Method to create deploy order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String
     */
    @Override
    public String createDeployOrder(Player p_Player, GameState p_gameState) {
        if(p_Player.getD_noOfUnallocatedArmies() > 0) {
            Country l_weakestCountry = getWeakestCountry(p_Player);
            d_deployCountries.add(l_weakestCountry);

            Random l_random = new Random();
            int l_armiesToDeploy = l_random.nextInt(p_Player.getD_noOfUnallocatedArmies()) + 1;

            System.out.println("deploy " + l_weakestCountry.getD_countryName() + " " + l_armiesToDeploy);
            return String.format("deploy %s %d", l_weakestCountry.getD_countryName(), l_armiesToDeploy);
        } else {
            return createAdvanceOrder(p_Player, p_gameState);
        }
    }

    /**
     * Method to create advance order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String
     */
    @Override
    public String createAdvanceOrder(Player p_Player, GameState p_gameState) {
        //Advance on the weakest country
        int l_armiesToSend;
        Random l_random = new Random();

        Country l_randomSourceCountry = getRandomCountry(p_Player.getD_coutriesOwned());
        System.out.println("Source Country " + l_randomSourceCountry.getD_countryName());
        Country l_weakestTargetCountry = getWeakestNeighbor(l_randomSourceCountry, p_gameState);
        System.out.println("Target Country " + l_weakestTargetCountry.getD_countryName());
        if(l_randomSourceCountry.getD_currentArmies() > 0) {
            l_armiesToSend = l_random.nextInt(l_randomSourceCountry.getD_currentArmies() - 1);
        } else {
            l_armiesToSend = 0;
        }
        if(l_armiesToSend==0){
            return "nocommand";
        }
        l_randomSourceCountry.setD_currentArmies(l_randomSourceCountry.getD_currentArmies()-l_armiesToSend);

        System.out.println("advance " + l_randomSourceCountry.getD_countryName() + " " + l_weakestTargetCountry.getD_countryName() + " " + l_armiesToSend);
        return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_weakestTargetCountry.getD_countryName() + " " + l_armiesToSend;
    }

    /**
     * Method to create card order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @param p_cardName Card name for created Order
     * @return String
     */
    @Override
    public String createCardOrder(Player p_Player, GameState p_gameState, String p_cardName) {
        int l_armiesToSend;
        Random l_random = new Random();
        Country l_randomOwnCountry = getRandomCountry(p_Player.getD_coutriesOwned());
        Country l_randomEnemyNeighbor = p_gameState.getD_map()
                .retrieveCountry(randomEnemyNeighbor(p_Player, l_randomOwnCountry)
                        .get(l_random.nextInt(randomEnemyNeighbor(p_Player, l_randomOwnCountry).size())));

        if(l_randomOwnCountry.getD_armies() > 0) {
            l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_currentArmies() - 1);
        } else {
            l_armiesToSend = 0;
        }

        switch(p_cardName) {
            case "bomb":
                System.err.println("I am benevolent player, I don't hurt anyone.");
                return "nocommand";
            case "blockade":
                return "blockade " + l_randomOwnCountry.getD_countryName();
            case "airlift":
                return "airlift " + l_randomOwnCountry.getD_countryName() + " "
                        + getRandomCountry(p_Player.getD_coutriesOwned()).getD_countryName() + " " + l_armiesToSend;
            case "negotiate":
                return "negotiate " + p_Player.getPlayerName();
        }

        return null;
    }

    /**
     * Method to get player behaviour
     * @return String
     */
    @Override
    public String getPlayerBehavior() {
        return "Benevolent";
    }

    /**
     * Method to get random country
     * @param p_listOfCountries list of countries
     * @return random country from the list given
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
     * Method to get the weakest country
     * @param p_Player player object
     * @return weakest country given player has
     */
    public Country getWeakestCountry(Player p_Player) {
        List<Country> l_countriesOwnedByPlayer = p_Player.getD_coutriesOwned();
        Country l_Country = calculateWeakestCountry(l_countriesOwnedByPlayer);
        return l_Country;
    }

    /**
     * Method to get the weakest neighbour country
     * @param l_randomSourceCountry random source country
     * @param p_gameState current game state
     * @return weakest neighbour country
     */
    public Country getWeakestNeighbor(Country l_randomSourceCountry, GameState p_gameState) {
        List<Integer> l_neighbourCountryIds = l_randomSourceCountry.getD_neighbourCountryId();
        List<Country> l_listOfNeighbors = new ArrayList<Country>();
        for(int l_index = 0; l_index < l_neighbourCountryIds.size(); l_index++) {
            Country l_Country = p_gameState.getD_map()
                    .retrieveCountry(l_randomSourceCountry.getD_neighbourCountryId().get(l_index));
            l_listOfNeighbors.add(l_Country);
        }
        Country l_Country = calculateWeakestCountry(l_listOfNeighbors);

        return l_Country;
    }

    /**
     * Method to calculate the weakest country
     * @param l_listOfCountries list of countries
     * @return weakest country
     */
    public Country calculateWeakestCountry(List<Country> l_listOfCountries) {
        LinkedHashMap<Country, Integer> l_CountryWithArmies = new LinkedHashMap<Country, Integer>();
        int l_smallestNoOfArmies;
        Country l_Country = null;

        //return weakest country from owned countries of player
        for(Country l_c : l_listOfCountries) {
            l_CountryWithArmies.put(l_c, l_c.getD_armies());
        }
        l_smallestNoOfArmies = Collections.min(l_CountryWithArmies.values());
        for(Map.Entry<Country, Integer> entry : l_CountryWithArmies.entrySet()) {
            if(entry.getValue().equals(l_smallestNoOfArmies)) {
                return entry.getKey();
            }
        }
        return l_Country;
    }

    /**
     * Method to get random enemy neighbour country
     * @param p_Player player object
     * @param p_Country country object
     * @return random country
     */
    private ArrayList<Integer> randomEnemyNeighbor(Player p_Player, Country p_Country) {
        ArrayList<Integer> l_enemyNeighbors = new ArrayList<>();
        for(Integer l_countryID : p_Country.getD_neighbourCountryId()) {
            if(!p_Player.getCountryIDs().contains(l_countryID)) {
                l_enemyNeighbors.add(l_countryID);
            }
        }
        return l_enemyNeighbors;
    }

    /**
     * Method to check if armies are deployed by the player or not
     * @param p_Player player object
     * @return true is armies are deployed or false
     */
    private Boolean checkIfArmiesDeployed(Player p_Player) {
        if(p_Player.getD_coutriesOwned().stream().anyMatch(l_country -> l_country.getD_armies() > 0)) {
            return true;
        }
        return false;
    }
}
