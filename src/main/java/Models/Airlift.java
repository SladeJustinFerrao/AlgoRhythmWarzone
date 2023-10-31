package Models;

/**
 * Class handles the execute and validation of Airlift Validate.
 */
public class Airlift implements Card {
    private Player d_player;
    private String d_sourceCountryName;
    private String d_targetCountryName;
    private Integer d_numberOfArmies;
    private String d_orderExecutionLog;

    public Airlift(String p_sourceCountryName, String p_targetCountryName, Integer p_noOfArmies, Player p_player) {
        this.d_numberOfArmies = p_noOfArmies;
        this.d_targetCountryName = p_targetCountryName;
        this.d_sourceCountryName = p_sourceCountryName;
        this.d_player = p_player;
    }

    @Override
    public void execute(GameState p_gameState) {
        if (valid(p_gameState)) {
            Country sourceCountry = p_gameState.getMap().getCountryByName(d_sourceCountryName);
            Country targetCountry = p_gameState.getMap().getCountryByName(d_targetCountryName);
            int updatedTargetArmies = targetCountry.getD_armies() + this.d_numberOfArmies;
            int updatedSourceArmies = sourceCountry.getD_armies() - this.d_numberOfArmies;
            targetCountry.setD_armies(updatedTargetArmies);
            sourceCountry.setD_armies(updatedSourceArmies);
            d_player.removeCard("airlift");
            this.setD_orderExecutionLog("Airlift Operation from " + d_sourceCountryName + " to " + d_targetCountryName + " successful!", "default");
            p_gameState.updateLog(d_orderExecutionLog, "effect");
        } else {
            this.setD_orderExecutionLog("Cannot Complete Execution of given Airlift Command!", "error");
            p_gameState.updateLog(d_orderExecutionLog, "effect");
        }
    }

    @Override
    public boolean valid(GameState p_gameState) {
        Country sourceCountry = d_player.getD_coutriesOwned().stream()
                .filter(country -> country.getD_countryName().equalsIgnoreCase(this.d_sourceCountryName))
                .findFirst().orElse(null);
        if (sourceCountry == null) {
            this.setD_orderExecutionLog(
                    this.currentOrder() + " is not executed since Source country : " + this.d_sourceCountryName
                            + " given in card order does not belong to the player : " + d_player.getPlayerName(),
                    "error");
            p_gameState.updateLog(orderExecutionLog(), "effect");
            return false;
        }
        Country targetCountry = d_player.getD_coutriesOwned().stream()
                .filter(country -> country.getD_countryName().equalsIgnoreCase(this.d_targetCountryName))
                .findFirst().orElse(null);
        if (targetCountry == null) {
            this.setD_orderExecutionLog(
                    this.currentOrder() + " is not executed since Target country : " + this.d_sourceCountryName
                            + " given in card order does not belong to the player : " + d_player.getPlayerName(),
                    "error");
            p_gameState.updateLog(orderExecutionLog(), "effect");
            return false;
        }
        if (this.d_numberOfArmies > sourceCountry.getD_armies()) {
            this.setD_orderExecutionLog(this.currentOrder()
                    + " is not executed as armies given in card order exceeds armies of source country : "
                    + this.d_sourceCountryName, "error");
            p_gameState.updateLog(orderExecutionLog(), "effect");
            return false;
        }
        return true;
    }

    @Override
    public void printOrder() {
        this.d_orderExecutionLog = "----------Airlift order issued by player " + this.d_player.getPlayerName()
                + "----------" + System.lineSeparator() + "Move " + this.d_numberOfArmies + " armies from "
                + this.d_sourceCountryName + " to " + this.d_targetCountryName;
        System.out.println(System.lineSeparator() + this.d_orderExecutionLog);
    }

    @Override
    public String orderExecutionLog() {
        return this.d_orderExecutionLog;
    }

    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {
        this.d_orderExecutionLog = p_orderExecutionLog;
        if (p_logType.equals("error")) {
            System.err.println(p_orderExecutionLog);
        } else {
            System.out.println(p_orderExecutionLog);
        }
    }

    private String currentOrder() {
        return "Airlift Order : " + "airlift" + " " + this.d_sourceCountryName + " " + this.d_targetCountryName + " "
                + this.d_numberOfArmies;
    }

    @Override
    public Boolean checkValidOrder(GameState p_GameState) {
        Country sourceCountry = p_GameState.getMap().getCountryByName(d_sourceCountryName);
        Country targetCountry = p_GameState.getMap().getCountryByName(d_targetCountryName);
        if (sourceCountry == null) {
            this.setD_orderExecutionLog("Invalid Source Country! Doesn't exist on the map!", "error");
            p_GameState.updateLog(orderExecutionLog(), "effect");
            return false;
        }
        if (targetCountry == null) {
            this.setD_orderExecutionLog("Invalid Target Country! Doesn't exist on the map!", "error");
            p_GameState.updateLog(orderExecutionLog(), "effect");
            return false;
        }
        return true;
    }

    @Override
    public String getOrderName() {
        return "airlift";
    }
}
