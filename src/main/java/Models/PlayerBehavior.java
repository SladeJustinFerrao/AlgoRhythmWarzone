package Models;

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

}
