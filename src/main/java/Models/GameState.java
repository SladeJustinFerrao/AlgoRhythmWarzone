package Models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is used to test functionality of GameState class functions.
 */
public class GameState implements Serializable {

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
     * Checks if user has used load command.
     */
    Boolean d_loadCommand = false;

    /**
     * Log Entry object
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Number of turns in tournament.
     */
    int d_maxNumberOfTurns = 0;

    /**
     * Number of remaining turns in tournament.
     */
    int d_numberOfTurnsLeft = 0;

    /**
     * Maintains list of players lost in the game.
     */
    List<Player> d_playersFailed = new ArrayList<Player>();

    /**
     * Winner Player.
     */
    Player d_winner;

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

    /**
     * Adding logs to log file.
     * @param p_log Log Input Value
     * @param p_category Category of Log.
     */
    public void updateLog(String p_log, String p_category) {
        d_logEntryBuffer.currentLog(p_log, p_category);
    }

    /**
     * Gets the current log.
     * @return Current Log
     */
    public String getRecentLog(){return d_logEntryBuffer.getCurrentLog();}

    /**
     * Returns if load command is used.
     *
     * @return bool value if map is loaded
     */
    public boolean getD_loadCommand(){
        return this.d_loadCommand;
    }
    /**
     * Sets the Boolean load map variable.
     */
    public void setD_loadCommand() {
        this.d_loadCommand = true;
    }

    /**
     * Returns max number of turns allowed in tournament.
     *
     * @return int number of turns
     */
    public int getD_maxNumberOfTurns() {
        return d_maxNumberOfTurns;
    }

    /**
     * Sets max number of turns allowed in tournament.
     *
     * @param d_maxNumberOfTurns number of turns
     */
    public void setD_maxNumberOfTurns(int d_maxNumberOfTurns) {
        this.d_maxNumberOfTurns = d_maxNumberOfTurns;
    }

    /**
     * Gets number of turns left at any stage of tournament.
     *
     * @return number of remaining turns
     */
    public int getD_numberOfTurnsLeft() {
        return d_numberOfTurnsLeft;
    }

    /**
     * Sets number of turns left at any stage of tournament.
     *
     * @param d_numberOfTurnsLeft number of remaining turns
     */
    public void setD_numberOfTurnsLeft(int d_numberOfTurnsLeft) {
        this.d_numberOfTurnsLeft = d_numberOfTurnsLeft;
    }

    /**
     * Adds the Failed Player in GameState.
     *
     * @param p_player Player instance to be removed
     */
    public void removePlayer(Player p_player){d_playersFailed.add(p_player);}

    /**
     * Retrieves the list of failed players.
     *
     * @return List of Players that lost the game.
     */
    public List<Player> getD_playersFailed() {return d_playersFailed;}

    /**
     * Returns the winner player object.
     *
     * @return returns winning player
     */
    public Player getD_winner(){return d_winner;}

    /**
     * Sets the winner player object.
     *
     * @param p_player winner player object
     */
    public void setD_winner(Player p_player){d_winner = p_player;}
}