package Models;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CheaterPlayer extends PlayerBehavior {


    @Override
    public String createOrder(Player p_player, GameState p_gameState) throws IOException {

        if(p_player.getD_noOfUnallocatedArmies() != 0) {
            while(p_player.getD_noOfUnallocatedArmies() > 0) {
                Random l_random = new Random();
                Country l_randomCountry = getRandomCountry(p_player.getD_coutriesOwned());
                int l_armiesToDeploy = l_random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;

                l_randomCountry.setD_armies(l_armiesToDeploy);
                p_player.setD_noOfUnallocatedArmies(p_player.getD_noOfUnallocatedArmies() - l_armiesToDeploy);

                String l_logMessage = "Cheater Player: " + p_player.getPlayerName() +
                        " assigned " + l_armiesToDeploy +
                        " armies to  " + l_randomCountry.getD_countryName();

                p_gameState.updateLog(l_logMessage, "effect");
            }
        }

        conquerNeighboringEnemies(p_player, p_gameState);
        doubleArmyOnEnemyNeighboredCounties(p_player, p_gameState);

        p_player.checkForMoreOrders(true);
        return null;
    }
    private void doubleArmyOnEnemyNeighboredCounties(Player p_player, GameState p_gameState){
    }
    private Country getRandomCountry(List<Country> p_listOfCountries){
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }
    private void conquerNeighboringEnemies(Player p_player, GameState p_gameState){
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

    @Override
    public String getPlayerBehavior() {
        return null;
    }
}
