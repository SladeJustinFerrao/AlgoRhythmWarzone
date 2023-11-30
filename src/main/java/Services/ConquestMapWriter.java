package Services;

import Models.Continent;
import Models.Country;
import Models.GameState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class ConquestMapWriter implements Serializable {

    public void writeToConquestFile(GameState p_gameState, FileWriter p_writer) throws IOException {
        if (null != p_gameState.getD_map().getD_continents() && !p_gameState.getD_map().getD_continents().isEmpty()) {
            writeContinentdata(p_gameState, p_writer);
        }
        if (null != p_gameState.getD_map().getD_countries() && !p_gameState.getD_map().getD_countries().isEmpty()) {
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
        p_writer.write(System.lineSeparator() + "[Continents]" + System.lineSeparator());
        for (Continent l_continent : p_gameState.getD_map().getD_continents()) {
            p_writer.write(l_continent.getD_continentName() + "=" + (l_continent.getD_continentValue().toString()) + System.lineSeparator());
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

        p_writer.write(System.lineSeparator() + "[Territories]" + System.lineSeparator());
        for (Country l_country : p_gameState.getD_map().getD_countries()) {
            String l_countryData = new String();

            l_countryData = l_country.getD_countryName().concat(",t1,t2,")
                    .concat(p_gameState.getD_map().retrieveContinentById(l_country.getD_continentId()).getD_continentName());

            if (null != l_country.getD_neighbourCountryId() && !l_country.getD_neighbourCountryId().isEmpty()) {
                for (Integer l_adjCountry : l_country.getD_neighbourCountryId()) {
                    l_countryData = l_countryData.concat(",")
                            .concat(p_gameState.getD_map().retrieveCountry(l_adjCountry).getD_countryName());
                }
            }
            p_writer.write(l_countryData + System.lineSeparator());
        }
    }
}
