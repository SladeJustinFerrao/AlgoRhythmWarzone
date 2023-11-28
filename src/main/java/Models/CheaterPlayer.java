package Models;
import java.io.IOException;
import java.util.ArrayList;
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
        List<Country> l_countriesOwned = p_player.getD_coutriesOwned();

        for(Country l_ownedCountry : l_countriesOwned) {
            ArrayList<Integer> l_countryEnemies = getEnemies(p_player, l_ownedCountry);

            if(l_countryEnemies.size() == 0) continue;

            Integer l_arimiesInTerritory = l_ownedCountry.getD_armies();

            if(l_arimiesInTerritory == 0) continue;

            l_ownedCountry.setD_armies(l_arimiesInTerritory*2);

            String l_logMessage = "Cheater Player: " + p_player.getPlayerName() +
                    " doubled the armies ( Now: " + l_arimiesInTerritory*2 +
                    ") in " + l_ownedCountry.getD_countryName();

            p_gameState.updateLog(l_logMessage, "effect");

        }
    }
    private Country getRandomCountry(List<Country> p_listOfCountries){
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }
    private void conquerNeighboringEnemies(Player p_player, GameState p_gameState){
        List<Country> l_countriesOwned = p_player.getD_coutriesOwned();

        for(Country l_ownedCountry : l_countriesOwned) {
            ArrayList<Integer> l_countryEnemies = getEnemies(p_player, l_ownedCountry);

            for(Integer l_enemyId: l_countryEnemies) {
                Map l_loadedMap =  p_gameState.getD_map();
                Player l_enemyCountryOwner = this.getCountryOwner(p_gameState, l_enemyId);
                Country l_enemyCountry = l_loadedMap.retrieveCountry(l_enemyId);
                this.conquerTargetCountry(p_gameState, l_enemyCountryOwner ,p_player, l_enemyCountry);

                String l_logMessage = "Cheater Player: " + p_player.getPlayerName() +
                        " Now owns " + l_enemyCountry.getD_countryName();

                p_gameState.updateLog(l_logMessage, "effect");
            }

        }

    }
    private void conquerTargetCountry(GameState p_gameState, Player p_targetCPlayer, Player p_cheaterPlayer, Country p_targetCountry) {

    }
    private Player getCountryOwner(GameState p_gameState, Integer p_countryId){
        List<Player> l_players = p_gameState.getD_players();
        Player l_owner = null;

        for(Player l_player: l_players){
            List<Integer> l_countriesOwned = l_player.getCountryIDs();
            if(l_countriesOwned.contains(p_countryId)){
                l_owner = l_player;
                break;
            }
        }

        return l_owner;
    }

    private ArrayList<Integer> getEnemies(Player p_player, Country p_country){
        ArrayList<Integer> l_enemyNeighbors = new ArrayList<Integer>();

        for(Integer l_countryID : p_country.getD_neighbourCountryId()){
            if(!p_player.getCountryIDs().contains(l_countryID))
                l_enemyNeighbors.add(l_countryID);
        }
        return l_enemyNeighbors;
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
