package Services;

import Models.GameState;

import java.io.FileWriter;
import java.io.IOException;

public class MapWriterAdapter extends MapFileWriter{
    private ConquestMapWriter l_conquestMapWriter;

    /**
     * Adapter constructor for setting conquest map file Writer.
     *
     * @param p_conquestMapWriter conquest map file Writer
     */
    public MapWriterAdapter(ConquestMapWriter p_conquestMapWriter) {
        this.l_conquestMapWriter = p_conquestMapWriter;
    }


    /**
     * Adapter for writing to different type of map file through adaptee.
     *
     * @param p_gameState current state of the game
     * @param l_writer file writer
     * @throws IOException Io exception
     */
    public void writeToFile(GameState p_gameState, FileWriter l_writer) throws IOException {
        l_conquestMapWriter.writeToConquestFile(p_gameState, l_writer);
    }
}
