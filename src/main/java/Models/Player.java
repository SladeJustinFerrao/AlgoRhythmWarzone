package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import Constants.GameConstants;
import Services.PlayerService;
import Utils.Command;


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


    public Player(String p_playerName) {
        this.d_name = p_playerName;
        this.d_noOfUnallocatedArmies = 0;
        this.d_ordersToExecute = new ArrayList<>();
    }


    public Player() {

    }


    public String getPlayerName() {
        return d_name;
    }


    public void setPlayerName(String p_name) {
        this.d_name = p_name;
    }


    public String getD_color() {
        return d_color;
    }


    public void setD_color(String p_color) {
        d_color = p_color;
    }


    public List<Country> getD_coutriesOwned() {
        return d_coutriesOwned;
    }

    public void setD_coutriesOwned(List<Country> p_coutriesOwned) {
        this.d_coutriesOwned = p_coutriesOwned;
    }


    public List<Continent> getD_continentsOwned() {
        return d_continentsOwned;
    }


    public void setD_continentsOwned(List<Continent> p_continentsOwned) {
        this.d_continentsOwned = p_continentsOwned;
    }


    public List<Order> getD_ordersToExecute() {
        return d_ordersToExecute;
    }


    public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
        this.d_ordersToExecute = p_ordersToExecute;
    }


    public Integer getD_noOfUnallocatedArmies() {
        return d_noOfUnallocatedArmies;
    }


    public void setD_noOfUnallocatedArmies(Integer p_numberOfArmies) {
        this.d_noOfUnallocatedArmies = p_numberOfArmies;
    }


    public List<String> getCountryNames(){
        List<String> l_countryNames=new ArrayList<String>();
        for(Country c: d_coutriesOwned){
            // need to call and add by getting country names from country class WIP
        }
        return l_countryNames;
    }


    public List<String> getContinentNames(){
        List<String> l_continentNames = new ArrayList<String>();
        if (d_continentsOwned != null) {
            for(Continent c: d_continentsOwned){
                // need to call and add by getting continents names from country class WIP
            }
            return l_continentNames;
        }
        return null;
    }


    public void issue_order() throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        PlayerService l_playerService = new PlayerService();
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


    public Order next_order() {
        if (this.d_ordersToExecute==null||this.d_ordersToExecute.isEmpty()) {
            return null;
        }
        Order l_order = this.d_ordersToExecute.get(0);
        this.d_ordersToExecute.remove(l_order);
        return l_order;
    }
}
