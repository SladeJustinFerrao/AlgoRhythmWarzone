package Models;

import Constants.GameConstants;

public class Bomb implements Card{

    /**
     * Bomb card will be owned by this player.
     */
    Player d_playerInitiator;

    /**
     * name of the target country.
     */
    String d_targetCountryID;

    /**
     * Sets the Log containing Information about orders.
     */
    String d_orderExecutionLog;

    /**
     *
     * This constructor gathers all the parameters to implement the order.
     *
     * @param p_playerInitiator Player
     * @param p_targetCountry Target Country
     */
    public Bomb(Player p_playerInitiator, String p_targetCountry) {
        this.d_playerInitiator = p_playerInitiator;
        this.d_targetCountryID = p_targetCountry;
    }

    /**
     * Executes Bomb order.
     *
     * @param p_gameState current game state
     */
    @Override
    public void execute(GameState p_gameState) {
        if (valid(p_gameState)) {
            Country l_targetCountryID = p_gameState.getD_map().getCountryByName(d_targetCountryID);
            Integer l_noOfArmiesOnTargetCountry = l_targetCountryID.getD_armies() == 0 ? 1
                    : l_targetCountryID.getD_armies();
            Integer l_newArmies = (int) Math.floor(l_noOfArmiesOnTargetCountry / 2);
            l_targetCountryID.setD_armies(l_newArmies);
            d_playerInitiator.removeCard("bomb");
            this.setD_orderExecutionLog(
                    "\nPlayer : " + this.d_playerInitiator.getPlayerName() + " is executing Bomb card on country :  "
                            + l_targetCountryID.getD_countryName() + " with armies :  " + l_noOfArmiesOnTargetCountry
                            + ". New armies: " + l_targetCountryID.getD_armies(),
                    "default");
            p_gameState.updateLog(orderExecutionLog(), GameConstants.OUTCOME);
        }
    }

    /**
     * Gives currently executed bomb order
     *
     * @return order command
     */
    private String currentOrder() {
        return "Bomb card order : " + "bomb" + " " + this.d_targetCountryID;
    }

    /**
     * Validates if the target country belongs to the Player who executed the order or not.
     *
     * @param p_gameState current game state
     * @return order command
     */
    @Override
    public boolean valid(GameState p_gameState) {
        Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
                .filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryID)).findFirst()
                .orElse(null);

        // Player cannot bomb own territory
        if (l_country == null) {
            this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Target country : "
                    + this.d_targetCountryID + " given in bomb command is owned by the player : "
                    + d_playerInitiator.getPlayerName() + " VALIDATES:- You cannot bomb your own territory!", GameConstants.ERROR);
            p_gameState.updateLog(orderExecutionLog(), GameConstants.OUTCOME);
            return false;
        }

        if(!d_playerInitiator.negotiationValidation(this.d_targetCountryID)){
            this.setD_orderExecutionLog(this.currentOrder() + " is not executed as "+ d_playerInitiator.getPlayerName()+ " has negotiation pact with the target country's player!", GameConstants.ERROR);
            p_gameState.updateLog(orderExecutionLog(), GameConstants.OUTCOME);
            return false;
        }
        return true;
    }

    /**
     * Printing Bomb Order.
     */
    @Override
    public void printOrder() {
        this.d_orderExecutionLog = "----------Bomb card order issued by player "
                + this.d_playerInitiator.getPlayerName() + "----------" + System.lineSeparator()
                + "Creating a bomb order = " + "on country ID. " + this.d_targetCountryID;
        System.out.println(System.lineSeparator() + this.d_orderExecutionLog);

    }

    /**
     * Execution log.
     *
     * @return String return execution log
     */
    public String orderExecutionLog() {
        return this.d_orderExecutionLog;
    }

    /**
     * Prints and Sets the order execution log.
     *
     * @param p_orderExecutionLog String to be set as log
     * @param p_logType type of log : error, default
     */
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {
        this.d_orderExecutionLog = p_orderExecutionLog;
        if (p_logType.equals(GameConstants.ERROR)) {
            System.err.println(p_orderExecutionLog);
        } else {
            System.out.println(p_orderExecutionLog);
        }
    }

    /**
     * Validation of Card type order.
     *
     * @param p_gameState Gamestate
     * @return true or false
     */
    @Override
    public Boolean checkValidOrder(GameState p_gameState) {
        Country l_targetCountry = p_gameState.getD_map().getCountryByName(d_targetCountryID);
        if (l_targetCountry == null) {
            this.setD_orderExecutionLog("Invalid Target Country! Doesn't exist on the map!", GameConstants.ERROR);
            return false;
        }
        return true;
    }

    /**
     * Return order name.
     *
     * @return String order name
     */
    @Override
    public String getOrderName() {
        return "bomb";
    }
}
