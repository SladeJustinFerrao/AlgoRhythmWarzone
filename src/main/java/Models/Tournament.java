package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Controller.GameEngine;

public class Tournament implements Serializable {

    /**
     * Game states of the tournament.
     */
    List<GameState> d_gameStateList = new ArrayList<GameState>();

    /**
     * Parses number of turns given in tournament command to an object.
     *
     * @param p_argument   no of turns
     * @param p_gameEngine game engine
     * @return true if parsing is successful or else false
     */
    private boolean parseNoOfTurnsArguments(String p_argument, GameEngine p_gameEngine) {
        int l_maxTurns = Integer.parseInt(p_argument.split(" ")[0]);
        if (l_maxTurns >= 10 && l_maxTurns <= 50) {
            for (GameState l_gameState : d_gameStateList) {
                l_gameState.setD_maxNumberOfTurns(l_maxTurns);
                l_gameState.setD_numberOfTurnsLeft(l_maxTurns);
            }
            return true;
        } else {
            p_gameEngine.setD_gameEngineLog(
                    "User entered invalid number of turns in command, Range of turns :- 10<=number of turns<=50",
                    "effect");
            return false;
        }
    }
}
