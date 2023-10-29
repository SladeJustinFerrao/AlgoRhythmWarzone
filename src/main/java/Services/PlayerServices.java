package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Player;

/**
 * This service class handles the players.
 */
public class PlayerService {

    String d_playerLog;

    String d_assignmentLog = "Country/Continent Assignment:";

    public boolean isPlayerNameUnique(List<Player> p_existingPlayerList, String p_playerName) {
        boolean l_isUnique = true;
        if (!p_existingPlayerList.isEmpty()) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_playerName)) {
                    l_isUnique = false;
                    break;
                }
            }
        }
        return l_isUnique;
    }

    public List<Player> addRemovePlayers(List<Player> p_existingPlayerList, String p_operation, String p_argument) {
        List<Player> l_updatedPlayers = new ArrayList<>();
        if (!p_existingPlayerList.isEmpty())
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
                setD_playerLog("Invalid Operation on Players list");
        }
        return l_updatedPlayers;
    }

    private void removeGamePlayer(List<Player> p_existingPlayerList, List<Player> p_updatedPlayers, String p_enteredPlayerName, boolean p_playerNameAlreadyExist) {
        if (p_playerNameAlreadyExist) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_enteredPlayerName)) {
                    p_updatedPlayers.remove(l_player);
                    setD_playerLog("Player with name : " + p_enteredPlayerName + " has been removed successfully.");
                }
            }
        } else {
            setD_playerLog("Player with name : " + p_enteredPlayerName + " does not Exist. Changes are not made.");
        }
    }

    private void addGamePlayer(List<Player> p_updatedPlayers, String p_enteredPlayerName, boolean p_playerNameAlreadyExist) {

        if (p_playerNameAlreadyExist) {
            setD_playerLog("Player with name : " + p_enteredPlayerName + " already Exists. Changes are not made.");
        } else {
            Player l_addNewPlayer = new Player(p_enteredPlayerName);
            p_updatedPlayers.add(l_addNewPlayer);
            setD_playerLog("Player with name : " + p_enteredPlayerName + " has been added successfully.");
        }
    }

    public boolean checkPlayersAvailability(GameState p_gameState) {
        if (p_gameState.getD_players() == null || p_gameState.getD_players().isEmpty()) {
            return false;
        }
        return true;
    }

    public void assignColors(GameState p_gameState) {
        if (!checkPlayersAvailability(p_gameState))
            return;

        List<Player> l_players = p_gameState.getD_players();

        
    }

    public void assignCountries(GameState p_gameState) {
        if (!checkPlayersAvailability(p_gameState)){
            p_gameState.updateLog("Kindly add players before assigning countries",  "effect");
            return;
        }

        List<Country> l_countries = p_gameState.getD_map().getD_countries();
        int l_playerSize = p_gameState.getD_players().size();
        Player l_neutralPlayer = p_gameState.getD_players().stream()
                .filter(l_player -> l_player.getPlayerName().equalsIgnoreCase("Neutral")).findFirst().orElse(null);
        if (l_neutralPlayer != null)
            l_playerSize = l_playerSize - 1;
        int l_countriesPerPlayer = Math.floorDiv(l_countries.size(), l_playerSize);

        this.performRandomCountryAssignment(l_countriesPerPlayer, l_countries, p_gameState.getD_players(), p_gameState);
        this.performContinentAssignment(p_gameState.getD_players(), p_gameState.getD_map().getD_continents());
        p_gameState.updateLog(d_assignmentLog, "effect");
        System.out.println("Countries have been assigned to Players.");

    }

    private void performRandomCountryAssignment(int p_countriesPerPlayer, List<Country> p_countries, List<Player> p_players, GameState p_gameState) {
        List<Country> l_unassignedCountries = new ArrayList<>(p_countries);
        for (Player l_pl : p_players) {
            if(!l_pl.getPlayerName().equalsIgnoreCase("Neutral")) {
                if (l_unassignedCountries.isEmpty())
                    break;
                for (int i = 0; i < p_countriesPerPlayer; i++) {
                    Random l_random = new Random();
                    int l_randomIndex = l_random.nextInt(l_unassignedCountries.size());
                    Country l_randomCountry = l_unassignedCountries.get(l_randomIndex);

                    if (l_pl.getD_coutriesOwned() == null)
                        l_pl.setD_coutriesOwned(new ArrayList<>());
                    l_pl.getD_coutriesOwned().add(p_gameState.getD_map().getCountryByName(l_randomCountry.getD_countryName()));
                    System.out.println("Player : " + l_pl.getPlayerName() + " is assigned with country : "
                            + l_randomCountry.getD_countryName());
                    d_assignmentLog += "\n Player : " + l_pl.getPlayerName() + " is assigned with country : "
                            + l_randomCountry.getD_countryName();
                    l_unassignedCountries.remove(l_randomCountry);
                }
            }
        }
        if (!l_unassignedCountries.isEmpty()) {
            performRandomCountryAssignment(1, l_unassignedCountries, p_players, p_gameState);
        }
    }

    public void performContinentAssignment(List<Player> p_players, List<Continent> p_continents) {
        for (Player l_pl : p_players) {
            List<String> l_countriesOwned = new ArrayList<>();
            if (!l_pl.getD_coutriesOwned().isEmpty()) {
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
                        d_assignmentLog += "\n Player : " + l_pl.getPlayerName() + " is assigned with continent : "
                                + l_cont.getD_continentName();
                    }
                }
            }
        }
    }

    public Integer calculateArmiesForPlayer(Player p_player) {
        Integer l_armies = null != p_player.getD_noOfUnallocatedArmies() ? p_player.getD_noOfUnallocatedArmies() : 0;
        if (!p_player.getD_coutriesOwned().isEmpty()) {
            l_armies = l_armies + Math.max(3, Math.round((p_player.getD_coutriesOwned().size()) / 3));
        }
        if (!p_player.getD_coutriesOwned().isEmpty()) {
            int l_continentCtrlValue = 0;
            for (Continent l_continent : p_player.getD_continentsOwned()) {
                l_continentCtrlValue = l_continentCtrlValue + l_continent.getD_continentValue();
            }
            l_armies = l_armies + l_continentCtrlValue;
        }
        return l_armies;
    }

    public void assignArmies(GameState p_gameState) {
        for (Player l_pl : p_gameState.getD_players()) {
            Integer l_armies = this.calculateArmiesForPlayer(l_pl);
            this.setD_playerLog("Player : " + l_pl.getPlayerName() + " has been assigned with " + l_armies + " armies");
            p_gameState.updateLog(this.d_playerLog, "effect");

            l_pl.setD_noOfUnallocatedArmies(l_armies);
        }
    }

    public boolean unexecutedOrdersExists(List<Player> p_playersList) {
        int l_totalUnexecutedOrders = 0;
        for (Player l_player : p_playersList) {
            l_totalUnexecutedOrders = l_totalUnexecutedOrders + l_player.getD_ordersToExecute().size();
        }
        return l_totalUnexecutedOrders != 0;
    }

    public boolean unassignedArmiesExists(List<Player> p_playersList) {
        int l_unassignedArmies = 0;
        for (Player l_player : p_playersList) {
            l_unassignedArmies = l_unassignedArmies + l_player.getD_noOfUnallocatedArmies();
        }
        return l_unassignedArmies != 0;
    }

    public void updatePlayers(GameState p_gameState, String p_operation, String p_argument) {
        if (!isMapLoaded(p_gameState)) {
            this.setD_playerLog("Kindly load the map first to add player: " + p_argument);
            p_gameState.updateLog(this.d_playerLog, "effect");
            return;
        }
        List<Player> l_updatedPlayers = this.addRemovePlayers(p_gameState.getD_players(), p_operation, p_argument);

        if (l_updatedPlayers!=null) {
            p_gameState.setD_players(l_updatedPlayers);
            p_gameState.updateLog(d_playerLog, "effect");
        }
    }

    public boolean isMapLoaded(GameState p_gameState) {
        return p_gameState.getD_map()!=null ? true : false;
    }

    public boolean checkForMoreOrders(List<Player> p_playersList) {
        for (Player l_player : p_playersList) {
            if(l_player.getD_moreOrders())
                return true;
        }
        return false;
    }

    public void resetPlayersFlag(List<Player> p_playersList) {
        for (Player l_player : p_playersList) {
            if (!l_player.getPlayerName().equalsIgnoreCase("Neutral"))
                l_player.setD_moreOrders(true);
            l_player.setD_oneCardPerTurn(false);
            l_player.resetNegotiation();
        }
    }

    public void setD_playerLog(String p_playerLog) {
        this.d_playerLog = p_playerLog;
        System.out.println(p_playerLog);
    }

    public Player findPlayerByName(String p_playerName, GameState p_gameState) {
        return p_gameState.getD_players().stream().filter(l_player -> l_player.getPlayerName().equals(p_playerName)).findFirst().orElse(null);
    }
}
