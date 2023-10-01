package Services;
import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PlayerServices {

    /**
     *
     * @param p_existingPlayerList  check if it is existing or not
     * @param p_playerName    input the player name who is playing
     * @return  if the player is unique or not
     */
    public boolean isPlayerNameUnique(List<Player> p_existingPlayerList, String p_playerName) {
        boolean l_isUnique = true;
        if (!(p_existingPlayerList==null) || p_existingPlayerList.isEmpty()) {
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
     *
     * @param p_existingPlayerList  list of existing players
     * @param p_operation  type of operation to be performed
     * @param p_argument  arguments which gives list of studenst
     * @return
     */
    public List<Player> addRemovePlayers(List<Player> p_existingPlayerList, String p_operation, String p_argument) {
        List<Player> l_updatedPlayers = new ArrayList<>();
        if (!(p_existingPlayerList==null) || p_existingPlayerList.isEmpty())
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
     * Adds player to Game if its not there already.
     *
     * @param p_updatedPlayers updated player list with newly added player
     * @param p_enteredPlayerName new player name to be added
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
     *
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
     * this method assigns colors to the players and the parameter is gameState
     * @param p_gameState
     */
    public void assignColors(GameState p_gameState){
        if (!checkPlayersAvailability(p_gameState)) return;

        List<Player> l_players = p_gameState.getD_players();
      String[] colorArray={"RED","GREEN","YELLOW","VIOLET","CYAN"};
        for(int i = 0; i< l_players.size(); i++){
            l_players.get(i).setD_color(colorArray[i]);
        }
    }

    public void assignCountries(GameState p_gameState) {
        //WIP... dependend on map class


    }


    private void performRandomCountryAssignment(int p_countriesPerPlayer, List<Country> p_countries, List<Player> p_players) {
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
     * @param p_players  list of players
     * @param p_continents list of continents
     */
    private void performContinentAssignment(List<Player> p_players, List<Continent> p_continents) {
        for (Player l_pl : p_players) {
			List<String> l_countriesOwned = new ArrayList<>();
			if (l_pl.getD_coutriesOwned().size()!=0) {
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
     * it creates and deploy order of the game
     * @param p_commandEntered get the parameter of the command given by the user
     * @param p_player object of the player
     */
    public void createDeployOrder(String p_commandEntered, Player p_player) {
        List<Order> l_orders = p_player.getD_ordersToExecute().size()==0 ? new ArrayList<>()
				: p_player.getD_ordersToExecute();
		String l_countryName = p_commandEntered.split(" ")[1];
		String l_noOfArmies = p_commandEntered.split(" ")[2];
		if (validateDeployOrderArmies(p_player, l_noOfArmies)) {
			System.out.println(
					"Given deploy order cant be executed as armies in deploy order exceeds player's unallocated armies");
		} else {
			Order l_orderObject = new Order(p_commandEntered.split(" ")[0], l_countryName,
					Integer.parseInt(l_noOfArmies));
			l_orders.add(l_orderObject);
			p_player.setD_ordersToExecute(l_orders);
			Integer l_unallocatedarmies = p_player.getD_noOfUnallocatedArmies() - Integer.parseInt(l_noOfArmies);
			p_player.setD_noOfUnallocatedArmies(l_unallocatedarmies);
			System.out.println("Order has been added to queue for execution.");
		}
    }


    public boolean validateDeployOrderArmies(Player p_player, String p_noOfArmies) {
        return p_player.getD_noOfUnallocatedArmies() < Integer.parseInt(p_noOfArmies);
    }


    public int calculateArmiesForPlayer(Player p_player) {
        int l_armies = 0;
		if (p_player.getD_coutriesOwned().size()!=0) {
			l_armies = Math.max(3, Math.round((p_player.getD_coutriesOwned().size()) / 3));
		}
		if (p_player.getD_continentsOwned().size()!=0) {
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


    public boolean unassignedArmiesExists(List<Player> p_playersList) {
        int l_unassignedArmies = 0;
        for (Player l_player : p_playersList) {
            l_unassignedArmies = l_unassignedArmies + l_player.getD_noOfUnallocatedArmies();
        }
        return l_unassignedArmies != 0;
    }


    public void updatePlayers(GameState p_gameState, String p_operation, String p_argument) {
        if (!isMapLoaded(p_gameState)) {
            System.out.println("Kindly load the map first to add player: " + p_argument);
            return;
        }
        List<Player> l_updatedPlayers = this.addRemovePlayers(p_gameState.getD_players(), p_operation, p_argument);

        if (l_updatedPlayers!=null) {
            p_gameState.setD_players(l_updatedPlayers);
        }
    }

    /**
     * This method is to check if the map is loaded properly
     * @param p_gameState
     * @return boolean if map is loaded or not
     */
    public boolean isMapLoaded(GameState p_gameState) {
        return !(p_gameState.getD_map()==null);
    }
}
