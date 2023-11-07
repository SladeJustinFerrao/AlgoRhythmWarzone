package Models;

public interface Card extends Order {
    public Boolean checkValidOrder(GameState p_gameState);
}
