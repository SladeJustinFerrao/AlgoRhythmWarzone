package Models;

import java.util.List;

/**
 * This class is used to test functionality of GameState class functions.
 */
public class GameState {

    /**
     * map object.
     */
    Map d_map;

    /**
     * list of players.
     */
    List<Player> d_players;

    /**
     * list of pending orders.
     */
    List<Order> d_pendingOrders;

    /**
     * error message.
     */
    String d_error;

    /**
     * getter method to get the map.
     *
     * @return map object
     */
    public Map getD_map() {
        return d_map;
    }

    /**
     * setter method to set the map.
     *
     * @param p_map map object
     */
    public void setD_map(Map p_map) {
        this.d_map = p_map;
    }

    /**
     * getter method to get the list of players.
     *
     * @return list of players
     */
    public List<Player> getD_players() {
        return d_players;
    }

    /**
     * setter method to set the players.
     *
     * @param p_players list of players
     */
    public void setD_players(List<Player> p_players) {
        this.d_players = p_players;
    }

    /**
     * getter method to get the list of orders which are pending.
     *
     * @return list of orders
     */
    public List<Order> getD_pendingOrders() {
        return d_pendingOrders;
    }

    /**
     * setter method to set the pending orders.
     *
     * @param p_pendingOrders list of pending orders
     */
    public void setD_pendingOrders(List<Order> p_pendingOrders) {
        this.d_pendingOrders = p_pendingOrders;
    }

    /**
     * getter method to get the error message.
     *
     * @return error message
     */
    public String getError() {
        return d_error;
    }

    /**
     * setter method to set the error message.
     *
     * @param p_error error message
     */
    public void setError(String p_error) {
        this.d_error = p_error;
    }
}