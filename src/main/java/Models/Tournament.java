package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Constants.GameConstants;
import Controller.GameEngine;
import Services.MapService;
import Utils.Command;

/**
 * Implements the tournament mode in the gameplay
 */
public class Tournament implements Serializable {
    /**
     * Map service object.
     */
    MapService d_mapService = new MapService();

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
     * Parses a tournament command into a tournament object, considering the current state of the game,
     * the given operation, the argument values provided in the command, and the game engine.
     *
     * @param p_gameState    The current state of the game.
     * @param p_operation    The operation specified in the command.
     * @param p_argument     The argument values provided in the command.
     * @param p_gameEngine   The game engine responsible for managing the tournament.
     * @return True if parsing is successful, false otherwise.
     * @throws Exception     Thrown if the map given in the command is invalid.
     */
    public boolean parseTournamentCommand(GameState p_gameState, String p_operation, String p_argument,
                                          GameEngine p_gameEngine) throws  Exception {

        // tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns

        if (p_operation.equalsIgnoreCase("M")) {
            return parseMapArguments(p_argument, p_gameEngine);
        }
        if (p_operation.equalsIgnoreCase("P")) {
            return parseStrategyArguments(p_gameState, p_argument, p_gameEngine);
        }
        if (p_operation.equalsIgnoreCase("G")) {
            return parseNoOfGameArgument(p_argument, p_gameEngine);
        }
        if (p_operation.equalsIgnoreCase("D")) {
            return parseNoOfTurnsArguments(p_argument, p_gameEngine);
        }
        throw new Exception(GameConstants.INVALIDCOMMANDTOURNAMENTMODE);
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

    /**
     * Parses strategy arguments into tournament object.
     *
     * @param p_gameState  current state of the game
     * @param p_argument   strategy arguments provided in game
     * @param p_gameEngine game engine object
     * @return true if parsing of strategy information is successful or else false
     */
    private boolean parseStrategyArguments(GameState p_gameState, String p_argument, GameEngine p_gameEngine) {
        String[] l_listOfPlayerStrategies = p_argument.split(" ");
        int l_playerStrategiesSize = l_listOfPlayerStrategies.length;
        List<Player> l_playersInTheGame = new ArrayList<>();
        List<String> l_uniqueStrategies = new ArrayList<>();

        for (String l_strategy : l_listOfPlayerStrategies) {
            if (l_uniqueStrategies.contains(l_strategy)) {
                p_gameEngine.setD_gameEngineLog(
                        "Repeti    tive strategy : " + l_strategy + " given. Kindly provide set of unique strategies.",
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
                    "There has to be at least 2 or more non human players eligible to play the tournament.", "effect");
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
        List<Player> p_playersToCopy = new ArrayList<>();
        for (Player l_pl : p_playersList) {
            Player l_player = new Player(l_pl.getPlayerName());

            if (l_pl.getD_playerBehavior() instanceof AggressivePlayer)
                l_player.setStrategy(new AggressivePlayer());
            else if (l_pl.getD_playerBehavior() instanceof RandomPlayer)
                l_player.setStrategy(new RandomPlayer());
            else if (l_pl.getD_playerBehavior() instanceof BenevolentPlayer)
                l_player.setStrategy(new BenevolentPlayer());
            else if (l_pl.getD_playerBehavior() instanceof CheaterPlayer)
                l_player.setStrategy(new CheaterPlayer());

            p_playersToCopy.add(l_player);
        }
        return p_playersToCopy;
    }

    /**
     * Sets information about players which are to be added for playing tournament.
     *
     * @param p_gameEngine             game engine object
     * @param p_listOfPlayerStrategies list of player strategies given in command
     * @param p_listOfPlayers          list of players in game state
     * @param p_playersInTheGame       players of the tournament
     */
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

    /**
     * Parses the number of games given in a tournament command into an object.
     *
     * @param p_argument     The number of games specified in the tournament command.
     * @param p_gameEngine   The game engine responsible for managing the tournament.
     * @return True if parsing is successful, false otherwise.
     * @throws Exception   Thrown if the map given in the command is invalid.
     */
    private boolean parseNoOfGameArgument(String p_argument, GameEngine p_gameEngine) throws Exception {
        int l_noOfGames = Integer.parseInt(p_argument.split(" ")[0]);

        if (l_noOfGames >= 1 && l_noOfGames <= 5) {
            List<GameState> l_additionalGameStates = new ArrayList<>();

            for (int l_gameNumber = 0; l_gameNumber < l_noOfGames - 1; l_gameNumber++) {
                for (GameState l_gameState : d_gameStateList) {
                    GameState l_gameStateToAdd = new GameState();
                    Models.Map l_loadedMap = d_mapService.loadMap(l_gameStateToAdd,
                            l_gameState.getD_map().getD_mapFile());
                    l_loadedMap.setD_mapFile(l_gameState.getD_map().getD_mapFile());

                    List<Player> l_playersToCopy = getPlayersToAdd(l_gameState.getD_players());
                    l_gameStateToAdd.setD_players(l_playersToCopy);

                    l_gameStateToAdd.setD_loadCommand();
                    l_additionalGameStates.add(l_gameStateToAdd);
                }
            }
            d_gameStateList.addAll(l_additionalGameStates);
            return true;
        } else {
            p_gameEngine.setD_gameEngineLog(
                    "User entered invalid number of games in command, Range of games :- 1<=number of games<=5",
                    "effect");
            return false;
        }
    }

    /**
     * Parses map arguments given in a command into the tournament object.
     *
     * @param p_argument     List of maps information provided in the command.
     * @param p_gameEngine   The game engine object responsible for managing the tournament.
     * @return True if parsing of maps to the tournament object is successful, false otherwise.
     * @throws Exception   Thrown if the map given in the command is invalid.
     */
    private boolean parseMapArguments(String p_argument, GameEngine p_gameEngine) throws Exception {
        String[] l_listOfMapFiles = p_argument.split(" ");
        int l_mapFilesSize = l_listOfMapFiles.length;

        if (l_mapFilesSize >= 1 & l_mapFilesSize <= 5) {
            for (String l_mapToLoad : l_listOfMapFiles) {
                GameState l_gameState = new GameState();
                // Loads the map if it is valid or resets the game state
                Models.Map l_loadedMap = d_mapService.loadMap(l_gameState, l_mapToLoad);
                l_loadedMap.setD_mapFile(l_mapToLoad);
                if (l_loadedMap.Validate()) {
                    l_gameState.setD_loadCommand();
                    p_gameEngine.setD_gameEngineLog(l_mapToLoad + " has been loaded to start the game", "effect");
                    d_gameStateList.add(l_gameState);
                } else {
                    d_mapService.resetMap(l_gameState, l_mapToLoad);
                    return false;
                }
            }
        } else {
            p_gameEngine.setD_gameEngineLog("User entered invalid number of maps in command, Range of map :- 1<=map<=5",
                    "effect");
            return false;
        }
        return true;
    }

    /**
     * Validates a tournament command and checks if the required information is present in the command.
     *
     * @param p_operations_list List of operations given in the command.
     * @param p_command         The tournament command.
     * @return True if the command is valid, false otherwise.
     */
    public boolean requiredTournamentArgPresent(List<java.util.Map<String, String>> p_operations_list, Command p_command) {
        String l_argumentKey = new String();
        if (p_operations_list.size() != 4)
            return false;

        for (java.util.Map<String, String> l_map : p_operations_list) {
            if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                    && p_command.checkRequiredKeysPresent(GameConstants.OPERATIONS, l_map)) {
                l_argumentKey.concat(l_map.get(GameConstants.OPERATIONS));
            }
        }
        if (!l_argumentKey.equalsIgnoreCase("MPGD"))
            return false;
        return true;
    }


}
