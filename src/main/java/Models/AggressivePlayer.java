package Models;

import java.io.IOException;

public class AggressivePlayer extends PlayerBehavior {

    /**
     * This method creates new order for different players with different strategies
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @return Order object of Order class
     * @throws IOException Exception
     */
    @Override
    public String createOrder(Player p_player, GameState p_gameState) throws IOException {
        return null;
    }

    /**
     * Deploy orders as per the player's strategy
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @return String of order
     */
    @Override
    public String createDeployOrder(Player p_player, GameState p_gameState) {
        return null;
    }

    /**
     * Advance orders as per the player's strategy
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @return String of order
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameState p_gameState) {
        return null;
    }

    /**
     * Card orders as per the player's strategy
     *
     * @param p_player    Object of Player class
     * @param p_gameState Object of GameState class
     * @param p_cardName  Card name for created Order
     * @return String of order
     */
    @Override
    public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
        return null;
    }

    /**
     * This method returns player's behavior
     *
     * @return String of Player's behavior
     */
    @Override
    public String getPlayerBehavior() {
        return null;
    }
}
