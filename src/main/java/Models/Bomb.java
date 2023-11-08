package Models;

public abstract class Bomb implements Card{

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
            p_gameState.updateLog(orderExecutionLog(), "effect");
        }
    }

    private String currentOrder() {
        return "Bomb card order : " + "bomb" + " " + this.d_targetCountryID;
    }

    @Override
    public boolean valid(GameState p_gameState) {
        Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
                .filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryID)).findFirst()
                .orElse(null);

        // Player cannot bomb own territory
        if (l_country != null) {
            this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Target country : "
                    + this.d_targetCountryID + " given in bomb command is owned by the player : "
                    + d_playerInitiator.getPlayerName() + " VALIDATES:- You cannot bomb your own territory!", "error");
            p_gameState.updateLog(orderExecutionLog(), "effect");
            return false;
        }

        if(!d_playerInitiator.negotiationValidation(this.d_targetCountryID)){
            this.setD_orderExecutionLog(this.currentOrder() + " is not executed as "+ d_playerInitiator.getPlayerName()+ " has negotiation pact with the target country's player!", "error");
            p_gameState.updateLog(orderExecutionLog(), "effect");
            return false;
        }
        return true;
    }

    @Override
    public void printOrder() {
        this.d_orderExecutionLog = "----------Bomb card order issued by player "
                + this.d_playerInitiator.getPlayerName() + "----------" + System.lineSeparator()
                + "Creating a bomb order = " + "on country ID. " + this.d_targetCountryID;
        System.out.println(System.lineSeparator() + this.d_orderExecutionLog);

    }

    public String orderExecutionLog() {
        return this.d_orderExecutionLog;
    }

    @Override
    public Boolean checkValidOrder(GameState p_gameState) {
        Country l_targetCountry = p_gameState.getD_map().getCountryByName(d_targetCountryID);
        if (l_targetCountry == null) {
            this.setD_orderExecutionLog("Invalid Target Country! Doesn't exist on the map!", "error");
            return false;
        }
        return true;
    }

    @Override
    public String getOrderName() {
        return "bomb";
    }

}
