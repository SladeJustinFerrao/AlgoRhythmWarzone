package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for human player who will be played by the user
 */
public class HumanPlayer extends PlayerBehavior {
    /**
     * Method to get player behaviour
     * @return String
     */
    @Override
    public String getPlayerBehavior() {
        return "Human";
    }

    /**
     * Method to create order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String
     * @throws IOException
     */
    @Override
    public String createOrder(Player p_Player, GameState p_gameState) throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nPlease enter command to issue order for player : " + p_Player.getPlayerName()
        + " or give showmap command to view current state of the game.");
        String l_commandEntered = l_reader.readLine();
        return l_commandEntered;
    }

    /**
     * Method to create deploy order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String
     */
    @Override
    public String createDeployOrder(Player p_Player, GameState p_gameState) {
        return null;
    }

    /**
     * Method to create advance order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @return String
     */
    @Override
    public String createAdvanceOrder(Player p_Player, GameState p_gameState) {
        return null;
    }

    /**
     * Method to create card order
     * @param p_Player Object of Player class
     * @param p_gameState Object of GameState class
     * @param p_cardName Card name for created Order
     * @return
     */
    @Override
    public String createCardOrder(Player p_Player, GameState p_gameState, String p_cardName) {
        return null;
    }
}
