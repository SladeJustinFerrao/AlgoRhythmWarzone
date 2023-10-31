package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import Services.PlayerServices;
import Utils.Command;

/**
 * This class contains data members and functions of player.
 */
public class Player {
    /**
     * color to show details with on map.
     */
    private String d_color;

    /**
     * Name of the player.
     */
    private String d_name;

    /**
     * List of countries owned by player.
     */
    List<Country> d_coutriesOwned;

    /**
     * List of Continents owned by player.
     */
    List<Continent> d_continentsOwned;

    /**
     * List of orders of player.
     */
    List<Order> d_ordersToExecute;

    /**
     * Number of armies allocated to player.
     */
    Integer d_noOfUnallocatedArmies;

    /**
     * More orders to be accepted for player.
     */
    boolean d_moreOrders;

    /**
     * If the per turn card is assigned already.
     */
    boolean d_oneCardPerTurn = false;

    /**
     * String holding Log for individual Player methods.
     */
    String d_playerLog;

    /**
     * Name of the card Player owns.
     */
    List<String> d_cardsOwnedByPlayer = new ArrayList<String>();

    /**
     * List of players to not attack if negotiated with.
     */
    List<Player> d_negotiatedWith = new ArrayList<Player>();

    /**
	 * constructor has param used to create player with name and be deafault
	 * armies.
	 * 
	 * @param p_playerName player name.
	 */
    public Player(String p_playerName) {
        this.d_name = p_playerName;
        this.d_noOfUnallocatedArmies = 0;
        this.d_ordersToExecute = new ArrayList<>();
    }


	/**
	 * This is no arg constructor.
	 */
    public Player() {

    }

    /**
	 * This getter is used to get player's name.
	 *
	 * @return return player name.
	 */
    public String getPlayerName() {
        return d_name;
    }

  /**
	 * This setter is used to set player's p_name.
	 *
	 * @param p_name set player name.
	 */
    public void setPlayerName(String p_name) {
        this.d_name = p_name;
    }


    /**
	 * This getter is used to get color code for player.
	 *
	 * @return Color
	 */
    public String getD_color() {
        return d_color;
    }

  /**
	 *
	 * @param p_color color string.
	 */
    public void setD_color(String p_color) {
        d_color = p_color;
    }


    /**
	 * This getter is used to get list of countries owned by player.
	 *
	 * @return return countries owned by player.
	 */
    public List<Country> getD_coutriesOwned() {
        return d_coutriesOwned;
    }


    /**
	 * This setter is used to set list of countries owned by player.
	 *
	 * @param p_coutriesOwned set countries owned by player.
	 */
    public void setD_coutriesOwned(List<Country> p_coutriesOwned) {
        this.d_coutriesOwned = p_coutriesOwned;
    }


    /**
	 * This getter is used to get list of continents owned by player.
	 *
	 * @return return list of continents owned by player.
	 */
    public List<Continent> getD_continentsOwned() {
        return d_continentsOwned;
    }


    /**
	 * This setter is used to set list of continents owned by player.
	 *
	 * @param p_continentsOwned set continents owned by player.
	 */
    public void setD_continentsOwned(List<Continent> p_continentsOwned) {
        this.d_continentsOwned = p_continentsOwned;
    }

  /**
	 * This getter is used to get execute orders of player.
	 * 
	 * @return return execute orders.
	 */
    public List<Order> getD_ordersToExecute() {
        return d_ordersToExecute;
    }


    /**
	 * This setter is used to set execute orders player.
	 * 
	 * @param p_ordersToExecute set execute orders.
	 */
    public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
        this.d_ordersToExecute = p_ordersToExecute;
    }


    /**
	 * This getter is used to get allocated armies of player.
	 * 
	 * @return return allocated armies of player.
	 */

    public Integer getD_noOfUnallocatedArmies() {
        return d_noOfUnallocatedArmies;
    }



    /**
	 * This setter is used to set number of allocated armies to player.
	 * 
	 * @param p_numberOfArmies set number of armies to player.
	 */
    public void setD_noOfUnallocatedArmies(Integer p_numberOfArmies) {
        this.d_noOfUnallocatedArmies = p_numberOfArmies;
    }



    /**
	 * Extracts the list of names of countries owned by the player.
	 *
	 * @return list of country names
	 */
    public List<String> getCountryNames(){
        List<String> l_countryNames=new ArrayList<String>();
        for(Country c: d_coutriesOwned){
            l_countryNames.add(c.d_countryName);
        }
        return l_countryNames;
    }



    /**
	 * Retrieves the list of continent names owned by the player.
	 *
	 * @return list of continent names
	 */
    public List<String> getContinentNames(){
        List<String> l_continentNames = new ArrayList<String>();
        if (d_continentsOwned != null) {
            for(Continent c: d_continentsOwned){
                l_continentNames.add(c.d_continentName);
            }
            return l_continentNames;
        }
        return null;
    }


    
	/**
	 * Issue order which takes order as an input and add it to players unassigned
	 * orders pool.
	 * 
	 * @throws IOException exception in reading inputs from user
	 */
    public void issue_order() throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        PlayerServices l_playerService = new PlayerServices();
        System.out.println("\nPlease enter command to deploy reinforcement armies on the map for player : "
                + this.getPlayerName());
        String l_commandEntered = l_reader.readLine();
        Command l_command = new Command(l_commandEntered);

        if (l_command.getMainCommand().equalsIgnoreCase("deploy") && l_commandEntered.split(" ").length == 3) {
            l_playerService.createDeployOrder(l_commandEntered, this);
        } else {
            System.out.println("Invalid command encountered");;
        }
    }


    /**
	 * Gives the first order in the players list of orders, then removes it from the
	 * list.
	 * 
	 * @return Order first order from the list of player's order
	 */
    public Order next_order() {
        if (this.d_ordersToExecute==null||this.d_ordersToExecute.isEmpty()) {
            return null;
        }
        Order l_order = this.d_ordersToExecute.get(0);
        this.d_ordersToExecute.remove(l_order);
        return l_order;
    }
}
