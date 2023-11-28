package Models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * This is the class of Random Player, that deploys armies randomly,
 * attacks random neighboring countries and moves armies on their own territories randomly.
 */
public class RandomPlayer extends PlayerBehavior{

    /**
     * List containing deploy order countries.
     */
    ArrayList<Country> d_deployCountries = new ArrayList<Country>();

    /**
     * This method returns the player behavior
     * @return String Player Behavior
     */
    @Override
    public String getPlayerBehavior() {
        return "Random";
    }

    /**
     * Choose a random player to negotiate
     *
     * @param p_player Player object
     * @param p_gameState current Gamestate
     * @return Player object
     */
    private Player getRandomPlayer(Player p_player, GameState p_gameState){
        ArrayList<Player> l_playerList = new ArrayList<Player>();
        Random l_random = new Random();

        for(Player l_player : p_gameState.getD_players()){
            if(!l_player.equals(p_player))
                l_playerList.add(p_player);
        }
        return l_playerList.get(l_random.nextInt(l_playerList.size()));
    }

    /**
     * This method creates a new order.
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     *
     * @return Order object of Order class
     * @throws IOException
     */
    @Override
    public String createOrder(Player p_player, GameState p_gameState) throws IOException {
        String l_command;
        if (!checkIfArmiesDeployed(p_player)) {
            if(p_player.getD_noOfUnallocatedArmies()>0) {
                l_command = createDeployOrder(p_player, p_gameState);
            }else{
                l_command = createAdvanceOrder(p_player, p_gameState);
            }
        } else {
            if(p_player.getD_cardsOwnedByPlayer().size()>0){
                int l_index = (int) (Math.random() * 3) +1;
                switch (l_index) {
                    case 1:
                        l_command = createDeployOrder(p_player, p_gameState);
                        break;
                    case 2:
                        l_command = createAdvanceOrder(p_player, p_gameState);
                        break;
                    case 3:
                        if (p_player.getD_cardsOwnedByPlayer().size() == 1) {
                            l_command = createCardOrder(p_player, p_gameState, p_player.getD_cardsOwnedByPlayer().get(0));
                            break;
                        } else {
                            Random l_random = new Random();
                            int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size());
                            l_command = createCardOrder(p_player, p_gameState, p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
                            break;
                        }
                    default:
                        l_command = createAdvanceOrder(p_player, p_gameState);
                        break;
                }
            } else{
                Random l_random = new Random();
                Boolean l_randomBoolean = l_random.nextBoolean();
                if(l_randomBoolean){
                    l_command = createDeployOrder(p_player, p_gameState);
                }else{
                    l_command = createAdvanceOrder(p_player, p_gameState);
                }
            }
        }
        return l_command;
    }

    /**
     * returns a Random country owned by player.
     *
     * @param p_listOfCountries list of Countries owned by player
     * @return Random country from list
     */
    private Country getRandomCountry(List<Country> p_listOfCountries){
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }

    /**
     * Thismethod creates new Advance order.
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     * @return Order object to Advance
     */
    @Override
    public String createDeployOrder(Player p_player, GameState p_gameState) {
        if (p_player.getD_noOfUnallocatedArmies()>0) {
            Random l_random = new Random();
            System.out.println(p_player.getD_coutriesOwned().size());
            Country l_randomCountry = getRandomCountry(p_player.getD_coutriesOwned());
            d_deployCountries.add(l_randomCountry);
            int l_armiesToDeploy = l_random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;

            return String.format("deploy %s %d", l_randomCountry.getD_countryName(), l_armiesToDeploy);
        } else {
            return createAdvanceOrder(p_player,p_gameState);
        }
    }

    @Override
    public String createAdvanceOrder(Player p_player, GameState p_gameState) {
        return null;
    }

    @Override
    public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
        return null;
    }

    /**
     * Check if it is first turn.
     *
     * @param p_player Object of Player class
     * @return boolean
     */
    private Boolean checkIfArmiesDeployed(Player p_player){
        if(p_player.getD_coutriesOwned().stream().anyMatch(l_country -> l_country.getD_armies()>0)){
            return true;
        }
        return false;
    }
}
