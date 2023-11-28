package Services;

import Models.Continent;
import Models.Country;
import Models.GameState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Map Writer Reader class to write to Domination format map files
 */
public class MapFileWriter implements Serializable {

    /**
     * Method to write to Domination Map File.
     * @param p_gameState Game State
     * @param p_writer File Writer Object
     * @throws IOException
     */
    public void writeToFile(GameState p_gameState, FileWriter p_writer) throws IOException {
        if (null != p_gameState.getD_map().getD_continents()
                && !p_gameState.getD_map().getD_continents().isEmpty()) {
            writeContinentdata(p_gameState, p_writer);
        }
        if (null != p_gameState.getD_map().getD_countries()
                && !p_gameState.getD_map().getD_countries().isEmpty()) {
            writeCountryAndNeighbourData(p_gameState, p_writer);
        }
    }

    /**
     * Write Continents into file
     *
     * @param p_gameState Current State of the Game
     * @param p_writer File Writer
     * @throws IOException
     */
    private void writeContinentdata(GameState p_gameState, FileWriter p_writer) throws IOException {
        p_writer.write(System.lineSeparator() + "[continents]" + System.lineSeparator());
        for (Continent l_continent : p_gameState.getD_map().getD_continents()) {
            p_writer.write(l_continent.getD_continentName() + " " + (l_continent.getD_continentValue().toString()) + System.lineSeparator());
        }
    }

    /**
     * Write Country and Neighbour into file
     *
     * @param p_gameState Current State of the Game
     * @param p_writer File Writer
     * @throws IOException
     */
    private void writeCountryAndNeighbourData(GameState p_gameState, FileWriter p_writer) throws IOException {
        List<String> l_bordersList = new ArrayList<>();

        p_writer.write(System.lineSeparator() + "[countries]" + System.lineSeparator());
        for (Country l_country : p_gameState.getD_map().getD_countries()) {
            String l_countryData = new String();

            l_countryData = ((Integer) l_country.getD_countryId()).toString() + " " + (l_country.getD_countryName()) + " " + (((Integer) l_country.getD_continentId()).toString());
            p_writer.write(l_countryData + System.lineSeparator());

            if (null != l_country.getD_neighbourCountryId() && !l_country.getD_neighbourCountryId().isEmpty()) {
                String l_bordersData = new String();
                l_bordersData = ((Integer) l_country.getD_countryId()).toString();
                for (Integer l_adjCountry : l_country.getD_neighbourCountryId()) {
                    l_bordersData = l_bordersData + " " + (l_adjCountry.toString());
                }
                l_bordersList.add(l_bordersData);
            }
        }

        if (!l_bordersList.isEmpty() && null != l_bordersList) {
            p_writer.write(System.lineSeparator() + "[borders]" + System.lineSeparator());
            for (String l_borderStr : l_bordersList) {
                p_writer.write(l_borderStr + System.lineSeparator());
            }
        }
    }
}
