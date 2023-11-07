package Controller;

import Constants.GameConstants;
import Models.GameState;
import Models.Phase;
import Models.StartUpPhase;
import Models.OrderExecutionPhase;
import Models.IssueOrderPhase;

/**
 * This is the entry point of the Game and keeps the track of current Game State.
 */
public class GameEngine {

	/**
	 * d_gameState stores the information about current GamePlay.
	 */
	public GameState d_gameState = new GameState();

	Phase d_currentPhase = new StartUpPhase(this, d_gameState);

	private void setD_CurrentPhase(Phase p_phase){
		d_currentPhase = p_phase;
	}

	public void setIssueOrderPhase(){
		this.setD_gameEngineLog("Issue Order Phase", GameConstants.PHASE);
		setD_CurrentPhase(new IssueOrderPhase(this, d_gameState));
		getD_CurrentPhase().initPhase();
	}

	public void setOrderExecutionPhase(){
		this.setD_gameEngineLog("Order Execution Phase", GameConstants.PHASE);
		setD_CurrentPhase(new OrderExecutionPhase(this, d_gameState));
		getD_CurrentPhase().initPhase();
	}

	public Phase getD_CurrentPhase(){
		return d_currentPhase;
	}

	public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
		d_currentPhase.getD_gameState().updateLog(p_gameEngineLog, p_logType);
		String l_consoleLogger = p_logType.equalsIgnoreCase(GameConstants.PHASE)
				? "\n************ " + p_gameEngineLog + " ************\n"
				: p_gameEngineLog;
		System.out.println(l_consoleLogger);
	}

	public static void main(String[] p_args) {
		GameEngine l_game = new GameEngine();

		l_game.getD_CurrentPhase().getD_gameState().updateLog(GameConstants.GAMESTART+System.lineSeparator(), GameConstants.STARTLOG);
		l_game.setD_gameEngineLog("Game Startup Phase", GameConstants.PHASE);
		l_game.getD_CurrentPhase().initPhase();
	}
}
