package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Constants.GameConstants;
import Controller.GameEngine;

public class Tournament implements Serializable {

    /**
     * Game states of the tournament.
     */
    List<GameState> d_gameStateList = new ArrayList<GameState>();

    /**
     * Gets the list of game states
     *
     * @return List of game states
     */
    public List<GameState> getD_gameStateList() {
        return d_gameStateList;
    }

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

    private boolean parseStrategyArguments(GameState p_gameState, String p_argument, GameEngine p_gameEngine) {
        String[] l_listOfPlayerStrategies = p_argument.split(" ");
        int l_playerStrategiesSize = l_listOfPlayerStrategies.length;
        List<Player> l_playersInTheGame = new ArrayList<>();
        List<String> l_uniqueStrategies = new ArrayList<>();

        for (String l_strategy : l_listOfPlayerStrategies) {
            if (l_uniqueStrategies.contains(l_strategy)) {
                p_gameEngine.setD_gameEngineLog(
                        "Repeatative strategy : " + l_strategy + " given. Kindly provide set of unique strategies.",
                        "effect");
                return false;
            }
            l_uniqueStrategies.add(l_strategy);
            if (!GameConstants.TOURNAMENT_BEHAVIORS.contains(l_strategy)) {
                p_gameEngine.setD_gameEngineLog(
                        "Invalid Strategy passed in command. Only Aggressive, Benevolent, Random and Cheater strategies are allowed.",
                        "effect");
                return false;
            }
        }
        if (l_playerStrategiesSize >= 2 && l_playerStrategiesSize <= 4) {
            setTournamentPlayers(p_gameEngine, l_listOfPlayerStrategies, p_gameState.getD_players(),
                    l_playersInTheGame);
        } else {
            p_gameEngine.setD_gameEngineLog(
                    "User entered invalid number of strategies in command, Range of strategies :- 2<=strategy<=4",
                    "effect");
            return false;
        }
        if (l_playersInTheGame.size() < 2) {
            p_gameEngine.setD_gameEngineLog(
                    "There has to be atleast 2 or more non human players eligible to play the tournament.", "effect");
            return false;
        }
        for (GameState l_gameState : d_gameStateList) {
            l_gameState.setD_players(getPlayersToAdd(l_playersInTheGame));
        }
        return true;
    }

    /**
     * Gets players list to add in each game state.
     *
     * @param p_playersList list of players to be looked from
     * @return list of players to be added
     */
    private List<Player> getPlayersToAdd(List<Player> p_playersList) {
        //Add Player strategies to list
        return p_playersList;
    }

    private void setTournamentPlayers(GameEngine p_gameEngine, String[] p_listOfPlayerStrategies, List<Player> p_listOfPlayers, List<Player> p_playersInTheGame) {
        for (String l_strategy : p_listOfPlayerStrategies) {
            for (Player l_pl : p_listOfPlayers) {
                if (l_pl.getD_playerBehavior().getPlayerBehavior().equalsIgnoreCase(l_strategy)) {
                    p_playersInTheGame.add(l_pl);
                    p_gameEngine.setD_gameEngineLog("Player:  " + l_pl.getPlayerName() + " with strategy: " + l_strategy + " has been added in tournament.", "effect");
                }
            }
        }
    }
}
