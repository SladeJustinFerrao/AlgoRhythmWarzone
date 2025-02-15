package Models;

import Constants.GameConstants;

import java.io.Serializable;

/**
 * Concrete command of command pattern
 */
public class Deploy implements Order, Serializable {
    /**
     * Name of target country
     */
    String d_targetCountryName;

    /**
     * Number of armies to place
     */
    Integer d_numberOfArmiesToPlace;

    /**
     * Player Initiator
     */
    Player d_playerInitiator;

    /**
     * Sets the Log containing information about orders
     */
    String d_orderExecutionLog;

    /**
     * The constructor receives all the parameters necessary to implement the order
     * These are then encapsulated in the order
     * @param p_playerInitiator player that created the order
     * @param p_targetCountryName country name that will receive new armies
     * @param p_numberOfArmiesToPlace number of armies to place
     */
    public Deploy(Player p_playerInitiator, String p_targetCountryName, Integer p_numberOfArmiesToPlace) {
        this.d_targetCountryName = p_targetCountryName;
        this.d_playerInitiator = p_playerInitiator;
        this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
    }

    /**
     * Executes the deployed order
     * @param p_gameState current game state
     */
    @Override
    public void execute(GameState p_gameState) {
        if(valid(p_gameState)) {
            for(Country l_country : p_gameState.getD_map().getD_countries()) {
                if(l_country.getD_countryName().equalsIgnoreCase(this.d_targetCountryName)) {
                    Integer l_armiesToUpdate = l_country.getD_armies() == 0 ? this.d_numberOfArmiesToPlace : l_country.getD_armies() + this.d_numberOfArmiesToPlace;
                    l_country.setD_armies(l_armiesToUpdate);
                    this.setD_orderExecutionLog(+l_armiesToUpdate+" armies have been deployed successfully on country : "+l_country.getD_countryName(), "default");
                }
            }
        }
        else {
            this.setD_orderExecutionLog("Deploy Order = " + "deploy" + " " + this.d_targetCountryName + " "
                + this.d_numberOfArmiesToPlace + " is not executed since target country : " + this.d_targetCountryName
                + " given in deploy command does not belongs to the player : " + d_playerInitiator.getPlayerName(), "error");
            d_playerInitiator.setD_noOfUnallocatedArmies(d_playerInitiator.getD_noOfUnallocatedArmies() + this.d_numberOfArmiesToPlace);
        }
        p_gameState.updateLog(orderExecutionLog(), GameConstants.OUTCOME);
    }

    /**
     * Checks whether target country belongs to the player or not
     * @param p_gameState current game state
     * @return true if country belongs to the player or else false
     */
    @Override
    public boolean valid(GameState p_gameState) {
        Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
                .filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryName.toString()))
                .findFirst().orElse(null);
        return l_country != null;
    }

    /**
     * Prints the deploy order
     */
    @Override
    public void printOrder() {
        this.d_orderExecutionLog = "\n---------- Deploy order issued by player " + this.d_playerInitiator.getPlayerName() + " ----------\n" + System.lineSeparator() + "Deploy " + this.d_numberOfArmiesToPlace + " armies to " + this.d_targetCountryName;
        System.out.println(this.d_orderExecutionLog);
    }

    /**
     * Gets order execution log
     * @return order execution log
     */
    @Override
    public String orderExecutionLog() {
        return d_orderExecutionLog;
    }

    /**
     * Prints and sets order execution log
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
     * Get order name
     * @return String which contains order name
     */
    @Override
    public String getOrderName() {
        return "deploy";
    }
}
