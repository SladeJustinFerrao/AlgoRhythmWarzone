package Models;

/**
 * This model class manages the player's owned cards
 */
public interface Card extends Order {
    /**
     * Validation of card type order
     * @param p_gameState Gamestate
     * @return true or false
     */
    public Boolean checkValidOrder(GameState p_gameState);
}
