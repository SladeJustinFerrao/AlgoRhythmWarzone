package Models;

import Views.LogWriter;
import Constants.GameConstants;
import java.util.Observable;

/**
 * Class used for logging of the game.
 */
public class LogEntryBuffer extends Observable {

    String d_logInput;

    /**
     * Constructor
     */
    public LogEntryBuffer(){
        LogWriter l_logWriter = new LogWriter();
        this.addObserver(l_logWriter);
    }

    /**
     * Getter for the Log Input.
     * @return Log Input.
     */
    public String getD_logMessage(){
        return d_logInput;
    }

    /**
     * Sets the log value and category.
     * @param p_log Log Input Value
     * @param p_category Category of the log.
     */
    public void currentLog(String p_log, String p_category){

        switch(p_category.toLowerCase()){
            case GameConstants.COMMAND:
                d_logInput =GameConstants.COMMANDLOG + p_log;
                break;
            case GameConstants.ORDER:
                d_logInput =GameConstants.ORDERLOG + p_log;
                break;
            case GameConstants.PHASE:
                d_logInput = GameConstants.SEP + p_log + GameConstants.SEP;
                break;
            case GameConstants.OUTCOME:
                d_logInput = GameConstants.LOG + p_log;
                break;
            case GameConstants.STARTLOG:
            case GameConstants.ENDLOG:
                d_logInput = p_log;
                break;
        }
        setChanged();
        notifyObservers();
    }
}
