package Models;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Services.PlayerServices;

/**
 * The CheaterPlayer class represents a player in a game who employs a cheating strategy during the issue order phase.
 * This strategy involves directly attacking neighboring enemy countries and doubling the number of armies on the player's
 * own countries that have enemy neighbors.
 */
public class CheaterPlayer extends PlayerBehavior {

    /**
     * Creates a new order for the specified player based on the given player and game state.
     *
     * @param p_player The Player object for which the order is being generated.
     * @param p_gameState The GameState object representing the current state of the game.
     *
     * @return An Order object representing the newly generated order for the specified player.
     */
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

    /**
     * Doubles the number of armies on countries that are neighbors to enemy countries for the specified player,
     * based on the given player and game state.
     *
     * @param p_player The Player object for whom the armies on neighboring countries are being doubled.
     * @param p_gameState The GameState object representing the current state of the game.
     */
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

    /**
     * Returns a random country owned by the player from the provided list of countries.
     *
     * @param p_listOfCountries List of countries owned by the player.
     * @return A random country from the list of owned countries.
     */
    private Country getRandomCountry(List<Country> p_listOfCountries){
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }

    /**
     * Conquers all enemy countries that are neighbors to the countries owned by the player,
     * based on the given player and game state.
     *
     * @param p_player The Player object initiating the conquest.
     * @param p_gameState The GameState object representing the current state of the game.
     */
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

    /**
     * Initiates the conquest of the target country when it doesn't have any armies, transferring ownership
     * from the target country's player to the cheater player.
     *
     * @param p_gameState       The current state of the game.
     * @param p_cheaterPlayer   The player owning the source country.
     * @param p_targetCPlayer   The player owning the target country.
     * @param p_targetCountry   The target country of the battle.
     */
    private void conquerTargetCountry(GameState p_gameState, Player p_targetCPlayer, Player p_cheaterPlayer, Country p_targetCountry) {
        p_targetCPlayer.getD_coutriesOwned().remove(p_targetCountry);
        p_targetCPlayer.getD_coutriesOwned().add(p_targetCountry);
        // Add Log Here
        this.updateContinents(p_cheaterPlayer, p_targetCPlayer, p_gameState);
    }

    /**
     * Updates the continents of players based on the results of a battle, specifically involving the cheater player
     * (owner of the source country), the target country's player, and the current state of the game.
     *
     * @param p_cheaterPlayer   The player owning the source country.
     * @param p_targetCPlayer   The player owning the target country.
     * @param p_gameState       The current state of the game.
     */
    private void updateContinents(Player p_cheaterPlayer, Player p_targetCPlayer,
                                  GameState p_gameState) {
        List<Player> l_playesList = new ArrayList<>();
        p_cheaterPlayer.setD_continentsOwned(new ArrayList<>());
        p_targetCPlayer.setD_continentsOwned(new ArrayList<>());
        l_playesList.add(p_cheaterPlayer);
        l_playesList.add(p_targetCPlayer);

        PlayerServices l_playerService = new PlayerServices();
        l_playerService.performContinentAssignment(l_playesList, p_gameState.getD_map().getD_continents());
    }

    /**
     * Retrieves the owner of the country with the specified ID and searches for its neighbors in the current game state.
     *
     * @param p_gameState The current state of the game.
     * @param p_countryId The ID of the country for which neighbors are to be searched.
     * @return The owner of the specified country.
     */
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

    /**
     * give Enemies
     * @param p_player
     * @param p_country
     * @return l_enemyNeighbors
     */
    private ArrayList<Integer> getEnemies(Player p_player, Country p_country){
        ArrayList<Integer> l_enemyNeighbors = new ArrayList<Integer>();

        for(Integer l_countryID : p_country.getD_neighbourCountryId()){
            if(!p_player.getCountryIDs().contains(l_countryID))
                l_enemyNeighbors.add(l_countryID);
        }
        return l_enemyNeighbors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createDeployOrder(Player p_player, GameState p_gameState) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameState p_gameState) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
        return null;
    }

    /**
     * This method returns the player behavior.
     * @return player behavior String
     */
    @Override
    public String getPlayerBehavior() {
        return "Cheater";
    }
}
