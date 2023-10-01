package Models;

public class Order {
    /**
     * Action of order
     */
    String d_ActionOfOrder;
    /**
     * Name of the targeted country
     */
    String d_CountryTargeted;
    /**
     * Name of the Source Country
     */
    String d_CountryOfSource;
    /**
     * Number of armies to be place
     */
    Integer d_ArmiesToPlace;
    /**
     * Object of the order class
     */
    Order OrderObj;

    /**
     * Un-parameterized constructor
     */
    public Order() {
    }

    /**
     * Parameterized constructor
     * @param p_ActionOfOrder action of order
     * @param p_CountryTargeted name of the targeted country
     * @param p_ArmiesToPlace number of armies to be placed
     */
    public Order(String p_ActionOfOrder, String p_CountryTargeted, Integer p_ArmiesToPlace) {
        this.d_ActionOfOrder = p_ActionOfOrder;
        this.d_CountryTargeted = p_CountryTargeted;
        this.d_ArmiesToPlace = p_ArmiesToPlace;
    }

    /**
     * Method to get action of order
     * @return action of order
     */
    public String getD_ActionOfOrder() {
        return d_ActionOfOrder;
    }

    /**
     * Method to set action of order
     * @param p_ActionOfOrder action of order
     */
    public void setD_ActionOfOrder(String p_ActionOfOrder) {
        this.d_ActionOfOrder = p_ActionOfOrder;
    }

    /**
     * Method to get name of the targeted country
     * @return name of the targeted country
     */
    public String getD_CountryTargeted() {
        return d_CountryTargeted;
    }

    /**
     * Method to set name of the targeted country
     * @param p_CountryTargeted name of the targeted country
     */
    public void setD_CountryTargeted(String p_CountryTargeted) {
        this.d_CountryTargeted = p_CountryTargeted;
    }

    /**
     * Method to get name of the source country
     * @return name of the source country
     */
    public String getD_CountryOfSource() {
        return d_CountryOfSource;
    }

    /**
     * Method to set name of the source country
     * @param p_CountryOfSource name of the source country
     */
    public void setD_CountryOfSource(String p_CountryOfSource) {
        this.d_CountryOfSource = p_CountryOfSource;
    }

    /**
     * Mathod to get number of armies to be place
     * @return number of armies to be place
     */
    public Integer getD_ArmiesToPlace() {
        return d_ArmiesToPlace;
    }

    /**
     * Method to set number of armies to be place
     * @param p_ArmiesToPlace number of armies to be place
     */
    public void setD_ArmiesToPlace(Integer p_ArmiesToPlace) {
        this.d_ArmiesToPlace = p_ArmiesToPlace;
    }

    /**
     * Enact the order object and change the state of game accordingly
     * @param p_gameState current state of the game
     * @param p_player player whose order is being executed
     */
    public void execute(GameState p_gameState, Player p_player) {
        switch(this.d_ActionOfOrder) {
            case "deploy": {
                if (this.doesPlayerHaveDeployCountry(p_player, this)) {
                    this.executeDeployedOrder(this, p_gameState, p_player);
                    System.out.println("Order has successfull been executed. " + this.getD_ArmiesToPlace() + " numbers of armies are deployed to target country " + this.getD_CountryTargeted());
                }
                else {
                    System.out.println("Target country does not belong to player: " + p_player.getPlayerName());
                }
                break;
            }
            default: {
                System.out.println("Invalid order command");
            }
        }
    }

    /**
     * Method to check if the country armies are deployed belongs to player or not
     * @param p_player player whose order is being executed
     * @param p_order order given by the player to be executed
     * @return true or false
     */
    public boolean doesPlayerHaveDeployCountry(Player p_player, Order p_order) {
        return false;
    }

    /**
     * Method to execute the order and update the state of the game
     * @param p_order order given by the player to be executed
     * @param p_gameState current state of the game
     * @param p_player player whose order is being executed
     */
    public void executeDeployedOrder(Order p_order, GameState p_gameState, Player p_player) {

    }
}
