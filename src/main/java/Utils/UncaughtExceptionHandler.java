package Utils;

import Constants.GameConstants;
import Models.GameState;

import java.io.Serializable;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler, Serializable {

    /**
     * GameState of the Exception
     */
    GameState d_gameState;

    /**
     * Constructor.
     *
     * @param p_gameState Current GameState
     */
    public UncaughtExceptionHandler(GameState p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * Updates the Log in the GameState.
     *
     * @param p_thread Exception's Thread.
     * @param p_exp    Throwable Instance of Exception
     */
    @Override
    public void uncaughtException(Thread p_thread, Throwable p_exp) {
        d_gameState.updateLog(p_exp.getMessage(), GameConstants.OUTCOME);
    }
}