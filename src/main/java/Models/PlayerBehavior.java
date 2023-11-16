package Models;

import java.io.IOException;
import java.io.Serializable;

/**
 * Class for Player Behavior
 */
public abstract class PlayerBehavior implements Serializable {

    /**
     * Object for player class
     */
    Player d_player;

    /**
     * Object for GameState class
     */
     GameState d_gameState;

    /**
     * This method creates new order for different players with different strategies
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     *
     * @return Order object of Order class
     * @throws IOException Exception
     */
    public abstract String createOrder(Player p_player, GameState p_gameState) throws IOException;

    /**
     * Deploy orders as per the player's strategy
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String of order
     */
    public abstract String createDeployOrder(Player p_player, GameState p_gameState);

    /**
     * Advance orders as per the player's strategy
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String of order
     */
    public abstract String createAdvanceOrder(Player p_player, GameState p_gameState);

    /**
     * Card orders as per the player's strategy
     *
     * @param p_player Object of Player class
     * @param p_gameState Object of GameState class
     * @param p_cardName Card name for created Order
     * @return String of order
     */
    public abstract String createCardOrder(Player p_player, GameState p_gameState, String p_cardName);

    /**
     * This method returns player's behavior
     * @return String of Player's behavior
     */
    public abstract String getPlayerBehavior();

}
