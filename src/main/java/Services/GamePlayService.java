package Services;

import Models.Phase;

import java.io.*;

/**
 * The GamePlayService class save and load file of gane.
 */
public class GamePlayService {


    /**
     * Save the current game phase to a designated file through serialization.
     *
     * @param p_phase    Instance of the current game phase.
     * @param p_filename Name of the file.
     */
    public static void saveGame(Phase p_phase, String p_filename) {
        try (ObjectOutputStream l_gameSaveFileObjectStream =
                     new ObjectOutputStream(
                             new BufferedOutputStream(
                                     new FileOutputStream("src/main/resources" + "/" + p_filename)))) {
            l_gameSaveFileObjectStream.writeObject(p_phase);
            l_gameSaveFileObjectStream.flush();
        } catch (IOException l_e) {
            l_e.printStackTrace();
        }
    }


    /**
     * Deserialize the game phase stored in the specified file.
     *
     * @param p_filename Name of the file from which to load the game phase.
     * @return The game phase saved in the file.
     * @throws IOException            Signals an input/output exception when the file is not found.
     * @throws ClassNotFoundException If the corresponding Phase class is not found during deserialization.
     */
    public static Phase loadGame(String p_filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream l_inputStream =
                     new ObjectInputStream(
                             new BufferedInputStream(
                                     new FileInputStream("src/main/resources" + "/" + p_filename)))) {
            return (Phase) l_inputStream.readObject();
        }
    }

}
