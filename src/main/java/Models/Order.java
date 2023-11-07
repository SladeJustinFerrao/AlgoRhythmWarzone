package Models;

/**
 * Class contains date members and methods for order given by a player
 */
public interface Order {
    /**
     * Method called by receiver to execute the order
     * @param p_gameState current game state
     */
    public void execute(GameState p_gameState);

    /**
     * Method to validate order
     * @param p_gameState current game state
     * @return instance of current game state
     */
    public boolean valid(GameState p_gameState);

    /**
     * Method to print order Information
     */
    public void printOrder();

    /**
     * Method to return log to game state with execution log
     * @return string which contains log message
     */
    public String orderExecutionLog();

    /**
     * Method to print and set the order execution log
     * @param p_orderExecutionLog string to set as log
     * @param p_logType type of log : error, default
     */
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType);

    /**
     * Method to return order name
     * @return string which contains order name
     */
    public String getOrderName();
}

