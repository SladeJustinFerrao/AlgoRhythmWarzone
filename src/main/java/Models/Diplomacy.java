package Models;

import Constants.GameConstants;
import Services.PlayerServices;

/**
 * Handles diplomacy command
 */
public class Diplomacy implements Card {
    /**
     * Player issuing the negotiate order
     */
    Player d_IssuingPlayer;
    /**
     * player name to establish negotiation with
     */
    String d_targetPlayer;
    /**
     * Records the execution log
     */
    String d_orderExecutionLog;

    /**
     * Constructor to create diplomacy order
     * @param p_targetPlayer target player to negotiate with
     * @param p_IssuingPlayer negotiate issuing player
     */
    public Diplomacy(String p_targetPlayer, Player p_IssuingPlayer) {
        this.d_targetPlayer = p_targetPlayer;
        this.d_IssuingPlayer = p_IssuingPlayer;
    }

    /**
     * Executing the negotiate order
     * @param p_gameState current game state
     */
    @Override
    public void execute(GameState p_gameState) {
        PlayerServices l_playerService = new PlayerServices();
        Player l_targetPlayer = l_playerService.findPlayerByName(d_targetPlayer, p_gameState);
        l_targetPlayer.addPlayerNegotiation(d_IssuingPlayer);
        d_IssuingPlayer.addPlayerNegotiation(l_targetPlayer);
        d_IssuingPlayer.removeCard("negotiate");
        this.setD_orderExecutionLog("Negotiation with " + d_targetPlayer + " approached by " + d_IssuingPlayer.getPlayerName() + " successful !", "default");
        p_gameState.updateLog(d_orderExecutionLog, GameConstants.OUTCOME);
    }

    /**
     * Checks if order is valid or not
     * @param p_gameState current game state
     * @return true
     */
    @Override
    public boolean valid(GameState p_gameState) {
        return true;
    }

    /**
     * Prints orders
     */
    public void printOrder() {
        this.d_orderExecutionLog = "----------Diplomacy order issued by player " + this.d_IssuingPlayer.getPlayerName()
            + "----------" + System.lineSeparator() + "Request to " + "negotiate attacks from " + this.d_targetPlayer;
        System.out.println(System.lineSeparator() + this.d_orderExecutionLog);
    }

    /**
     * Sets execution log
     * @return String
     */
    @Override
    public String orderExecutionLog() {
        return this.d_orderExecutionLog;
    }

    /**
     * Check if order is valid or not
     * @param p_gameState current gamestate
     * @return true if order is valid, false otherwise
     */
    @Override
    public Boolean checkValidOrder(GameState p_gameState) {
        PlayerServices l_playerServices = new PlayerServices();
        Player l_targetPlayer = l_playerServices.findPlayerByName(d_targetPlayer, p_gameState);
        if(!p_gameState.getD_players().contains(l_targetPlayer)) {
            this.setD_orderExecutionLog("Player to negotiate doesn't exist", "error");
            p_gameState.updateLog(orderExecutionLog(), GameConstants.OUTCOME);
            return false;
        }
        return true;
    }

    /**
     * Print and sets the order execution log
     * @param p_orderExecutionLog string to set as log
     * @param p_logType type of log : error, default
     */
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {
        this.d_orderExecutionLog = p_orderExecutionLog;
        if(p_logType.equals("error")) {
            System.err.println(p_orderExecutionLog);
        }
        else {
            System.out.println(p_orderExecutionLog);
        }
    }

    /**
     * Method to return current order which is being executed
     * @return String
     */
    private String currentOrder() {
        return "Diplomacy Order : " + "negotiate" + " " + this.d_targetPlayer;
    }

    /**
     * Return order name
     * @return String
     */
    @Override
    public String getOrderName() {
        return "diplomacy";
    }
}
