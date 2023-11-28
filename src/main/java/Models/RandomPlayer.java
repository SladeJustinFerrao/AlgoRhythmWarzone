package Models;

import java.io.IOException;
import java.util.ArrayList;


public class RandomPlayer extends PlayerBehavior{

    ArrayList<Country> d_deployCountries = new ArrayList<Country>();

    @Override
    public String getPlayerBehavior() {
        return null;
    }

    @Override
    public String createOrder(Player p_player, GameState p_gameState) throws IOException {
        return null;
    }

    @Override
    public String createDeployOrder(Player p_player, GameState p_gameState) {
        return null;
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
