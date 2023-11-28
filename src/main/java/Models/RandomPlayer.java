package Models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class RandomPlayer extends PlayerBehavior{

    ArrayList<Country> d_deployCountries = new ArrayList<Country>();

    @Override
    public String getPlayerBehavior() {
        return "Random";
    }

    private Player getRandomPlayer(Player p_player, GameState p_gameState){
        ArrayList<Player> l_playerList = new ArrayList<Player>();
        Random l_random = new Random();

        for(Player l_player : p_gameState.getD_players()){
            if(!l_player.equals(p_player))
                l_playerList.add(p_player);
        }
        return l_playerList.get(l_random.nextInt(l_playerList.size()));
    }

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

    private Country getRandomCountry(List<Country> p_listOfCountries){
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }

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

    private Boolean checkIfArmiesDeployed(Player p_player){
        if(p_player.getD_coutriesOwned().stream().anyMatch(l_country -> l_country.getD_armies()>0)){
            return true;
        }
        return false;
    }
}
