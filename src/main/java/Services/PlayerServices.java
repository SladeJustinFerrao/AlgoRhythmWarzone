package Services;

import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Services class for Player to fetch the sepcific methods to be used for players in the game
 * @author Darshan Kansara
 */
public class PlayerServices {

    /**
     *
     * @param p_existingPlayerList check if it is existing or not
     * @param p_playerName         input the player name who is playing
     * @return if the player is unique or not
     */
    public boolean isPlayerNameUnique(List<Player> p_existingPlayerList, String p_playerName) {
        boolean l_isUnique = true;
        if (!(p_existingPlayerList == null)) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_playerName)) {
                    l_isUnique = false;
                    break;
                }
            }
        }
        return l_isUnique;
    }

    /**
     * The method can add and remove players at the same time
     * @param p_existingPlayerList list of existing players
     * @param p_operation          type of operation to be performed
     * @param p_argument           arguments which gives list of players
     * @return the updated list of players after performing add or delete operation
     */
    public List<Player> addRemovePlayers(List<Player> p_existingPlayerList, String p_operation, String p_argument) {
        List<Player> l_updatedPlayers = new ArrayList<>();
        if (!(p_existingPlayerList == null))
            l_updatedPlayers.addAll(p_existingPlayerList);
        String l_enteredPlayerName = p_argument.split(" ")[0];
        boolean l_playerNameAlreadyExist = !isPlayerNameUnique(p_existingPlayerList, l_enteredPlayerName);

        switch (p_operation.toLowerCase()) {
            case "add":
                addGamePlayer(l_updatedPlayers, l_enteredPlayerName, l_playerNameAlreadyExist);
                break;
            case "remove":
                removeGamePlayer(p_existingPlayerList, l_updatedPlayers, l_enteredPlayerName, l_playerNameAlreadyExist);
                break;
            default:
                System.out.println("Invalid Operation on Players list");
        }
        return l_updatedPlayers;
    }

    /**
     * the method removes the existing game player or shows the Player does not exist in the console
     * @param p_existingPlayerList list of existing players
     * @param p_updatedPlayers updated player list with newly added player
     * @param p_enteredPlayerName new player name to be removed
     * @param p_playerNameAlreadyExist false if player to be removed does not exist
     */
    private void removeGamePlayer(List<Player> p_existingPlayerList, List<Player> p_updatedPlayers,
            String p_enteredPlayerName, boolean p_playerNameAlreadyExist) {
        if (p_playerNameAlreadyExist) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_enteredPlayerName)) {
                    p_updatedPlayers.remove(l_player);
                    System.out.println("Player with name : " + p_enteredPlayerName + " has been removed successfully.");
                }
            }
        } else {
            System.out.print("Player with name : " + p_enteredPlayerName + " does not Exist. Changes are not made.");
        }
    }

    /**
     * Adds player to Game if it's not there already.
     *
     * @param p_updatedPlayers         updated player list with newly added player
     * @param p_enteredPlayerName      new player name to be added
     * @param p_playerNameAlreadyExist true if player to be added already exists
     */
    private void addGamePlayer(List<Player> p_updatedPlayers, String p_enteredPlayerName,
            boolean p_playerNameAlreadyExist) {
        if (p_playerNameAlreadyExist) {
            System.out.print("Player with name : " + p_enteredPlayerName + "  Exists already. Changes not made.");
        } else {
            Player l_addNewPlayer = new Player(p_enteredPlayerName);
            p_updatedPlayers.add(l_addNewPlayer);
            System.out.println("Player name : " + p_enteredPlayerName + "  added successfully.");
        }
    }

    /**
     * The method checks the player availability
     * @param p_gameState game state or phase of the current game
     * @return boolean if the player is available or not
     */
    public boolean checkPlayersAvailability(GameState p_gameState) {
        if (p_gameState.getD_players() == null || p_gameState.getD_players().isEmpty()) {
            System.out.println("Please add players before assigning countries");
            return false;
        }
        return true;
    }

    /**
     * This method assign countries to the player
     * 
     * @param p_gameState game state or phase of the current game
     */
    public void assignCountries(GameState p_gameState) {
        if (!checkPlayersAvailability(p_gameState))
            return;

        List<Country> l_countries = p_gameState.getD_map().getD_countries();
        int l_countriesPerPlayer = Math.floorDiv(l_countries.size(), p_gameState.getD_players().size());

        this.performRandomCountryAssignment(l_countriesPerPlayer, l_countries, p_gameState.getD_players());
        this.performContinentAssignment(p_gameState.getD_players(), p_gameState.getD_map().getD_continents());
        System.out.println("Countries have been assigned to Players.");

    }

    /**
     * This method randomly assig countries to the list of players
     * 
     * @param p_countriesPerPlayer number of countries per player to be assigned
     * @param p_countries          list of countries to be assigned
     * @param p_players            list of players
     */
    private void performRandomCountryAssignment(int p_countriesPerPlayer, List<Country> p_countries,
            List<Player> p_players) {
        List<Country> l_unassignedCountries = new ArrayList<>(p_countries);
        for (Player l_pl : p_players) {
            if (l_unassignedCountries.isEmpty())
                break;
            // Based on number of countries to be assigned to player, it generates random
            // country and assigns to player
            for (int i = 0; i < p_countriesPerPlayer; i++) {
                Random l_random = new Random();
                int l_randomIndex = l_random.nextInt(l_unassignedCountries.size());
                Country l_randomCountry = l_unassignedCountries.get(l_randomIndex);

                if (l_pl.getD_coutriesOwned() == null)
                    l_pl.setD_coutriesOwned(new ArrayList<>());
                l_pl.getD_coutriesOwned().add(l_randomCountry);
                System.out.println("Player : " + l_pl.getPlayerName() + " is assigned with country : "
                        + l_randomCountry.getD_countryName());
                l_unassignedCountries.remove(l_randomCountry);
            }
        }
        // If any countries are still left for assignment, it will redistribute those
        // among players
        if (!l_unassignedCountries.isEmpty()) {
            performRandomCountryAssignment(1, l_unassignedCountries, p_players);
        }
    }

    /**
     * This method assigns the continent
     * 
     * @param p_players    list of players
     * @param p_continents list of continents
     */
    public void performContinentAssignment(List<Player> p_players, List<Continent> p_continents) {
        for (Player l_pl : p_players) {
            List<String> l_countriesOwned = new ArrayList<>();
            if (l_pl.getD_coutriesOwned().size() != 0) {
                l_pl.getD_coutriesOwned().forEach(l_country -> l_countriesOwned.add(l_country.getD_countryName()));

                for (Continent l_cont : p_continents) {
                    List<String> l_countriesOfContinent = new ArrayList<>();
                    l_cont.getD_countries().forEach(l_count -> l_countriesOfContinent.add(l_count.getD_countryName()));
                    if (l_countriesOwned.containsAll(l_countriesOfContinent)) {
                        if (l_pl.getD_continentsOwned() == null)
                            l_pl.setD_continentsOwned(new ArrayList<>());

                        l_pl.getD_continentsOwned().add(l_cont);
                        System.out.println("Player : " + l_pl.getPlayerName() + " is assigned with continent : "
                                + l_cont.getD_continentName());
                    }
                }
            }
        }
    }
    

    /**
     * this method validates the armies that are deployed properly or not
     * 
     * @param p_player     player object
     * @param p_noOfArmies number of armies
     * @return boolean if the deployed armies are valid or invalid
     */
    public boolean validateDeployOrderArmies(Player p_player, String p_noOfArmies) {
        return p_player.getD_noOfUnallocatedArmies() < Integer.parseInt(p_noOfArmies);
    }

    /**
     * This method calculates the army for a player who is currently available
     * @param p_player player object
     * @return the calculated number of armies to the player
     */
    public int calculateArmiesForPlayer(Player p_player) {
        int l_armies = 0;
        if (p_player.getD_coutriesOwned().size() != 0) {
            l_armies = Math.max(3, Math.round((p_player.getD_coutriesOwned().size()) / 3));
        }
        if (p_player.getD_continentsOwned()!=null && p_player.getD_continentsOwned().size() != 0) {
            int l_continentCtrlValue = 0;
            for (Continent l_continent : p_player.getD_continentsOwned()) {
                l_continentCtrlValue = l_continentCtrlValue + l_continent.getD_continentValue();
            }
            l_armies = l_armies + l_continentCtrlValue;
        }
        return l_armies;
    }

    /**
     * This function assign the armies in the game state and to the players
     * @param p_gameState game state or phase of the current game
     */
    public void assignArmies(GameState p_gameState) {
        for (Player l_pl : p_gameState.getD_players()) {
            Integer l_armies = this.calculateArmiesForPlayer(l_pl);
            System.out.println("Player : " + l_pl.getPlayerName() + " has been assigned with " + l_armies + " armies");

            l_pl.setD_noOfUnallocatedArmies(l_armies);
        }
    }

    /**
     * Check if unexecuted orders exists in the game.
     *
     * @param p_playersList players involved in game
     * @return boolean true if unexecuted orders exists with any of the players or
     *         else false
     */
    public boolean unexecutedOrdersExists(List<Player> p_playersList) {
        int l_totalUnexecutedOrders = 0;
        for (Player l_player : p_playersList) {
            l_totalUnexecutedOrders = l_totalUnexecutedOrders + l_player.getD_ordersToExecute().size();
        }
        return l_totalUnexecutedOrders != 0;
    }

    /**
     * The method checks if there are any unassigned armies left or not
     * @param p_playersList list of players available
     * @return true or false f there are any unassigned armies left or not
     */
    public boolean unassignedArmiesExists(List<Player> p_playersList) {
        int l_unassignedArmies = 0;
        for (Player l_player : p_playersList) {
            l_unassignedArmies = l_unassignedArmies + l_player.getD_noOfUnallocatedArmies();
        }
        return l_unassignedArmies != 0;
    }

    /**
     * The method updates the list of players
     * @param p_gameState  game state or phase of the current game
     * @param p_operation operation to update the list
     * @param p_argument arguments which gives list of players
     */
    public void updatePlayers(GameState p_gameState, String p_operation, String p_argument) {
        if (!isMapLoaded(p_gameState)) {
            System.out.println("Kindly load the map first to add player: " + p_argument);
            return;
        }
        List<Player> l_updatedPlayers = this.addRemovePlayers(p_gameState.getD_players(), p_operation, p_argument);

        if (l_updatedPlayers != null) {
            p_gameState.setD_players(l_updatedPlayers);
        }
    }

    /**
     * This method is to check if the map is loaded properly
     * 
     * @param p_gameState game state or phase of the current game
     * @return boolean if map is loaded or not
     */
    public boolean isMapLoaded(GameState p_gameState) {
        return !(p_gameState.getD_map() == null);
    }


    /**
     * Find Player By Name.
     *
     * @param p_playerName player name to be found
     * @param p_gameState GameState Instance.
     * @return p_player object
     */
    public Player findPlayerByName(String p_playerName, GameState p_gameState) {
        return p_gameState.getD_players().stream().filter(l_player -> l_player.getPlayerName().equals(p_playerName)).findFirst().orElse(null);
    }
}
