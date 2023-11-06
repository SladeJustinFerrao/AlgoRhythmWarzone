package Views;

import Constants.GameConstants;
import Models.LogEntryBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Observable;
import java.util.Observer;

/**
 * Class to Write the logs to the file.
 */
public class LogWriter implements Observer {

    /**
     * LogEntry Observable Object
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

    private boolean isGameStartLog(String p_log) {
        return p_log.equals(GameConstants.GAMESTART + System.lineSeparator() + System.lineSeparator());
    }

    private void truncateLog(File p_logFile) throws IOException {
        Files.newBufferedWriter(p_logFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING).write(" ");
    }

    private void appendLog(File p_logFile, String p_log) throws IOException {
        Files.write(p_logFile.toPath(), p_log.getBytes(StandardCharsets.US_ASCII),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
