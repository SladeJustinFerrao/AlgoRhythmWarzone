package Controller;

import Constants.GameConstants;
import Models.*;

import java.io.Serializable;

/**
 * This is the entry point of the Game and keeps the track of current Game State.
 */
public class GameEngine implements Serializable {

	/**
	 * d_gameState stores the information about current GamePlay.
	 */
	public GameState d_gameState = new GameState();

	/**
	 * This method is getter for current Phase of Game State.
	 *
	 * @return current Phase of Game Context
	 */
	public Phase getD_CurrentPhase(){
		return d_currentPhase;
	}

	/**
	 * Gets current state of the game.
	 *
	 * @return State of the game
	 */
	public GameState getD_gameState() {
		return d_gameState;
	}

	/**
	 * Sets state of the game.
	 *
	 * @param p_gameState of the game
	 */
	public void setD_gameState(GameState p_gameState) {
		this.d_gameState = p_gameState;
	}

	/**
	 *	It is the current game play phase as per state pattern.
	 */
	Phase d_currentPhase = new StartUpPhase(this, d_gameState);

	/**
	 * Tournament mode or single game mode.
	 */
	static boolean d_isTournamentMode = false;

	/**
	 * Tournament mode information.
	 *
	 * @return true if tournament is being played or else false
	 */
	public boolean isD_isTournamentMode() {
		return d_isTournamentMode;
	}

	/**
	 * Sets tournament mode information.
	 *
	 * @param p_isTournamentMode true if tournament is being played or else false
	 */
	public void setD_isTournamentMode(boolean p_isTournamentMode) {
		GameEngine.d_isTournamentMode = p_isTournamentMode;
	}

	/**
	 *
	 * @param p_phase new Phase to set in Game context
	 */
	private void setD_CurrentPhase(Phase p_phase){
		d_currentPhase = p_phase;
	}

	/**
	 * Handle load game feature by setting phase from Object stream.
	 *
	 * @param p_phase to set new Phase
	 */
	public void loadPhase(Phase p_phase){
		d_currentPhase = p_phase;
		d_gameState = p_phase.getD_gameState();
		getD_CurrentPhase().initPhase(d_isTournamentMode);
	}

	/**
	 * These methods update the current phase to StartUp Phase as per State Pattern.
	 */
	public void setStartUpPhase(){
		this.setD_gameEngineLog("Start Up Phase", "phase");
		setD_CurrentPhase(new StartUpPhase(this, d_gameState));
		getD_CurrentPhase().initPhase(d_isTournamentMode);
	}

	/**
	 * These methods update the current phase to Issue Order Phase as per State Pattern.
	 */
	public void setIssueOrderPhase(boolean p_isTournamentMode){
		this.setD_gameEngineLog("Issue Order Phase", GameConstants.PHASE);
		setD_CurrentPhase(new IssueOrderPhase(this, d_gameState));
		//getD_CurrentPhase().initPhase(); // Yug changes
	}

	/**
	 * These methods update the current phase to Order Execution Phase as per State Pattern.
	 */
	public void setOrderExecutionPhase(){
		this.setD_gameEngineLog("Order Execution Phase", GameConstants.PHASE);
		setD_CurrentPhase(new OrderExecutionPhase(this, d_gameState));
		//getD_CurrentPhase().initPhase(); //Yug changes
	}

	/**
	 * Shows and Writes GameEngine Logs.
	 *
	 * @param p_gameEngineLog String of Log message.
	 * @param p_logType Type of Log.
	 */
	public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
		d_currentPhase.getD_gameState().updateLog(p_gameEngineLog, p_logType);
		String l_consoleLogger = p_logType.equalsIgnoreCase(GameConstants.PHASE)
				? "\n************ " + p_gameEngineLog + " ************\n"
				: p_gameEngineLog;
		System.out.println(l_consoleLogger);
	}

	/**
	 * The main method responsible for accepting command from users and redirecting
	 * those to corresponding logical flows.
	 *
	 * @param p_args the program doesn't use default command line arguments
	 */
	public static void main(String[] p_args) {
		GameEngine l_game = new GameEngine();

		l_game.getD_CurrentPhase().getD_gameState().updateLog(GameConstants.GAMESTART+System.lineSeparator(), GameConstants.STARTLOG);
		l_game.setD_gameEngineLog(GameConstants.GAMESTART, GameConstants.PHASE);
		System.out.println(GameConstants.AVAILABLECOMMANDS);
		//l_game.getD_CurrentPhase().initPhase(); //Yug Changes
	}
}