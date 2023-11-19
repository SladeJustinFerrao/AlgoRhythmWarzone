package Services;
import java.util.List;
import Models.GameState;
import Models.Map;

public class MapReaderAdapter extends MapFileReader{
    /**
     * FileReader Object.
     */
    private ConquestMapReader l_conquestMapReader;

    /**
     * Adapter constructor for setting conquest map file reader.
     *
     * @param p_conquestMapReader conquest map file reader
     */
    public MapReaderAdapter(ConquestMapReader p_conquestMapReader) {
        this.l_conquestMapReader = p_conquestMapReader;
    }


    /**
     * Adapter for reading different type of map file through adaptee.
     *
     * @param p_gameState current state of the game
     * @param p_map map to be set
     * @param p_linesOfFile lines of loaded file
     */
    public void extractMap(GameState p_gameState, Map p_map, List<String> p_linesOfFile) {
        l_conquestMapReader.extractConquestMap(p_gameState, p_map, p_linesOfFile);
    }
}
