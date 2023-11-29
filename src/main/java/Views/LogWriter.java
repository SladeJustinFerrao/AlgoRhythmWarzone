package Views;

import Constants.GameConstants;
import Models.LogEntryBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Observable;
import java.util.Observer;

/**
 * Class to Write the logs to the file.
 */
public class LogWriter implements Observer {

    /**
     * LogEntry Observable Object.
     */
    LogEntryBuffer d_logEntryBuffer;

    /**
     * Writes the LogEntryBuffer Object into Log file.
     * @param p_observable LogEntryBuffer Object.
     * @param p_obj Object
     */
    @Override
    public void update(Observable p_observable, Object p_obj) {
        d_logEntryBuffer = (LogEntryBuffer) p_observable;
        String l_log = d_logEntryBuffer.getCurrentLog();

        try {
            File l_logFile = new File(GameConstants.LOGFILENAME);
            if (isGameStartLog(l_log)) {
                truncateLog(l_logFile);
            }
            appendLog(l_logFile, l_log);
        } catch (IOException l_exp) {
            l_exp.printStackTrace();
        }
    }

    /**
     * Checks if the Log message is start of the game.
     * @param p_log Current log Message.
     * @return Boolean if it's the start of the game or not.
     */
    private boolean isGameStartLog(String p_log) {
        return p_log == null ? false : p_log.equals(GameConstants.GAMESTART + System.lineSeparator() + System.lineSeparator());
    }

    /**
     * Truncates the file.
     * @param p_logFile Log File.
     * @throws IOException
     */
    private void truncateLog(File p_logFile) throws IOException {
        Files.newBufferedWriter(p_logFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING).write(" ");
    }

    /**
     * Write to the Log File.
     * @param p_logFile Log File.
     * @param p_log Log Message to write.
     * @throws IOException
     */
    private void appendLog(File p_logFile, String p_log) throws IOException {
        Files.write(p_logFile.toPath(), (p_log == null ? "" : p_log).getBytes(StandardCharsets.US_ASCII),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
