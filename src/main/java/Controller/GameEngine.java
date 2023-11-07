package Controller;

import Models.GameState;
import Models.Phase;
import Models.StartUpPhase;

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

}
