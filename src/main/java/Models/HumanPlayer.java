package Models;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HumanPlayer extends PlayerBehaviourStrategy {
    @Override
    public String getPlayerBehaviour() {
        return "Human";
    }

    @Override
    public String createOrder(Player p_Player, GameState p_gameState) throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nPlease enter command to issue order for player : " + p_Player.getPlayerName()
        + " or give showmap command to view current state of the game.");
        String l_commandEntered = l_reader.readLine();
        return l_commandEntered;
    }

    @Override
    public String createDeployOrder(Player p_Player, GameState p_gameState) {
        return null;
    }

    @Override
    public String createAdvanceOrder(Player p_Player, GameState p_gameState) {
        return null;
    }

    @Override
    public String createCardOrder(Player p_Player, GameState p_gameState) {
        return null;
    }
}
