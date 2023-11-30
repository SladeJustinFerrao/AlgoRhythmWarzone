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
     * This method creates new Deploy order.
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     * @return Order object to Deploy
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

    /**
     * This method creates new Advance order.
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     * @return Order object to Deploy
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameState p_gameState) {
        int l_armiesToSend;
        Random l_random = new Random();
        Country l_randomOwnCountry = getRandomCountry(p_player.getD_coutriesOwned());
        int l_randomIndex = l_random.nextInt(l_randomOwnCountry.getD_neighbourCountryId().size());
        Country l_randomNeighbor;
        if (l_randomOwnCountry.getD_neighbourCountryId().size()>1) {
            l_randomNeighbor = p_gameState.getD_map().retrieveCountry(l_randomOwnCountry.getD_neighbourCountryId().get(l_randomIndex));
        } else {
            l_randomNeighbor = p_gameState.getD_map().retrieveCountry(l_randomOwnCountry.getD_neighbourCountryId().get(0));
        }

        if (l_randomOwnCountry.getD_currentArmies()>0) {
            l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_currentArmies() - 1);
        } else {
            l_armiesToSend = 0;
        }
        if(l_armiesToSend==0){
            return "nocommand";
        }
        l_randomOwnCountry.setD_currentArmies(l_randomOwnCountry.getD_currentArmies()-l_armiesToSend);
        return "advance "+l_randomOwnCountry.getD_countryName()+" "+l_randomNeighbor.getD_countryName()+" "+ l_armiesToSend;
    }

    /**
     * This method creates new Card order.
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     * @param p_cardName Card name for created Order
     * @return Card Object
     */
    @Override
    public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
        int l_armiesToSend;
        Random l_random = new Random();
        Country l_randomOwnCountry = getRandomCountry(p_player.getD_coutriesOwned());

        int count = 0;
        Country l_randomNeighbour;
        do {
            l_randomNeighbour = p_gameState.getD_map().retrieveCountry(l_randomOwnCountry.getD_neighbourCountryId().get(l_random.nextInt(l_randomOwnCountry.getD_neighbourCountryId().size())));
            if(!p_player.getD_coutriesOwned().contains(l_randomNeighbour)){
                break;
            }
            count++;
        }while(count<l_randomOwnCountry.getD_neighbourCountryId().size());


        Player l_randomPlayer = getRandomPlayer(p_player, p_gameState);

        if (l_randomOwnCountry.getD_currentArmies()>0) {
            l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_currentArmies() - 1);
        } else {
            l_armiesToSend = 0;
        }
        switch(p_cardName){
            case "bomb":
                return "bomb "+ l_randomNeighbour.getD_countryName();
            case "blockade":
                return "blockade "+ l_randomOwnCountry.getD_countryName();
            case "airlift":
                return "airlift "+ l_randomOwnCountry.getD_countryName()+" "+getRandomCountry(p_player.getD_coutriesOwned()).getD_countryName()+" "+l_armiesToSend;
            case "negotiate":
                System.out.println();
                return "negotiate"+" "+l_randomPlayer.getPlayerName();
        }
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
