package Services;

import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Map File Reader class to Read Domination format map files
 */
public class MapFileReader implements Serializable {

    /**
     * Method to extract map file data.
     * @param p_gameState Game State
     * @param p_map Map.
     * @param p_lines Lines of Map file.
     */
    public void extractMap(GameState p_gameState, Map p_map, List<String> p_lines) {

            List<String> l_continentData = getFileData(p_lines, "continent");
            List<Continent> l_continentObjects = parseContinentsData(l_continentData);
            List<String> l_countryData = getFileData(p_lines, "country");
            List<String> l_neighbourData = getFileData(p_lines, "neighbour");
            List<Country> l_countryObjects = parseCountriesData(l_countryData);

            l_countryObjects = parseNeighbourData(l_countryObjects, l_neighbourData);
            l_continentObjects = countryToContinents(l_countryObjects, l_continentObjects);
            p_map.setD_continents(l_continentObjects);
            p_map.setD_countries(l_countryObjects);
            p_gameState.setD_map(p_map);

    }

    /**
     * Retrieve Contents of the file lines.
     *
     * @param p_linesOfFile Line of File
     * @param p_case Switch Case
     * @return List of String with particular lines
     */
    public List<String> getFileData(List<String> p_linesOfFile, String p_case) {
        switch (p_case) {
            case "continent":
                List<String> l_continentLines = p_linesOfFile.subList(
                        p_linesOfFile.indexOf("[continents]") + 1,
                        p_linesOfFile.indexOf("[countries]") - 1);
                return l_continentLines;
            case "country":
                List<String> l_countryLines = p_linesOfFile.subList(p_linesOfFile.indexOf("[countries]") + 1,
                        p_linesOfFile.indexOf("[borders]") - 1);
                return l_countryLines;
            case "neighbour":
                List<String> l_neightboursLines = p_linesOfFile.subList(p_linesOfFile.indexOf("[borders]") + 1,
                        p_linesOfFile.size());
                return l_neightboursLines;
            default:
                return null;
        }
    }

    /**
     * Retrieve formatted Continents data from Line of Files
     *
     * @param p_continentList Unformatted Continents data
     * @return Formatted Continents data
     */
    public List<Continent> parseContinentsData(List<String> p_continentList) {
        List<Continent> l_continents = new ArrayList<Continent>();
        int l_continentId = 1;

        for (String l_continent : p_continentList) {
            String[] l_data = l_continent.split(" ");
            l_continents.add(new Continent(l_continentId, l_data[0], Integer.parseInt(l_data[1])));
            l_continentId++;
        }
        return l_continents;
    }

    /**
     * Retrieve formatted Countries data from Line of Files
     *
     * @param p_countriesList Unformatted Countries data
     * @return Formatted Countries data
     */
    public List<Country> parseCountriesData(List<String> p_countriesList) {
        List<Country> l_countriesList = new ArrayList<Country>();

        for (String country : p_countriesList) {
            String[] l_data = country.split(" ");
            l_countriesList.add(new Country(Integer.parseInt(l_data[0]), l_data[1],
                    Integer.parseInt(l_data[2])));
        }

        return l_countriesList;
    }

    /**
     * Retrieve formatted Neighbour data from Line of Files
     *
     * @param p_countriesList Countries data without neighbours
     * @param p_neighbourList Unformatted neighbours data
     * @return Formatted Countries data with Neighbour
     */
    public List<Country> parseNeighbourData(List<Country> p_countriesList, List<String> p_neighbourList) {

        LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();

        for (String l_neighbour : p_neighbourList) {
            if (null != l_neighbour && !l_neighbour.isEmpty()) {
                ArrayList<Integer> l_neighbours = new ArrayList<Integer>();
                String[] l_splitString = l_neighbour.split(" ");
                for (int i = 1; i <= l_splitString.length - 1; i++) {
                    l_neighbours.add(Integer.parseInt(l_splitString[i]));
                }
                l_countryNeighbors.put(Integer.parseInt(l_splitString[0]), l_neighbours);
            }
        }
        for (Country l_country : p_countriesList) {
            List<Integer> l_neighbourCountries = l_countryNeighbors.get(l_country.getD_countryId());
            l_country.setD_neighbourCountryId(l_neighbourCountries);
        }
        return p_countriesList;
    }

    /**
     * Link Country to Continents
     *
     * @param p_countries List of Countries
     * @param p_continents List of Continents
     * @return List of Continents
     */
    public List<Continent> countryToContinents(List<Country> p_countries, List<Continent> p_continents) {
        for (Country l_country : p_countries) {
            for (Continent l_continent : p_continents) {
                if (l_continent.getD_continentID().equals(l_country.getD_continentId())) {
                    l_continent.addCountry(l_country);
                }
            }
        }
        return p_continents;
    }
}
