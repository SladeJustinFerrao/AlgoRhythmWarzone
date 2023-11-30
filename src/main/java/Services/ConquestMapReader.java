package Services;

import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ConquestMapReader implements Serializable {

    /**
     * Method to extract map file data.
     *
     * @param p_gameState Game State
     * @param p_map       Map.
     * @param p_lines     Lines of Map file.
     */
    public void extractConquestMap(GameState p_gameState, Map p_map, List<String> p_lines) {

        List<String> l_continentData = getFileData(p_lines, "continent");
        List<Continent> l_continentObjects = parseContinentsData(l_continentData);
        List<String> l_countryData = getFileData(p_lines, "country");
        List<Country> l_countryObjects = parseCountriesData(l_countryData, l_continentObjects);
        List<Country> l_updatedCountries = parseNeighbourData(l_countryObjects, l_countryData);

        l_continentObjects = countryToContinents(l_updatedCountries, l_continentObjects);
        p_map.setD_continents(l_continentObjects);
        p_map.setD_countries(l_countryObjects);
        p_gameState.setD_map(p_map);

    }

    /**
     * Retrieve Contents of the file lines.
     *
     * @param p_linesOfFile Line of File
     * @param p_case        Switch Case
     * @return List of String with particular lines
     */
    public List<String> getFileData(List<String> p_linesOfFile, String p_case) {
        switch (p_case) {
            case "continent":
                List<String> l_continentLines = p_linesOfFile.subList(
                        p_linesOfFile.indexOf("[Continents]") + 1,
                        p_linesOfFile.indexOf("[Territories]") - 1);
                return l_continentLines;
            case "country":
                List<String> l_countryLines = p_linesOfFile.subList(p_linesOfFile.indexOf("[Territories]") + 1,
                        p_linesOfFile.size());
                return l_countryLines;
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
            String[] l_data = l_continent.split("=");
            l_continents.add(new Continent(l_continentId, l_data[0], Integer.parseInt(l_data[1])));
            l_continentId++;
        }
        return l_continents;
    }

    /**
     * Retrieve formatted Countries data from Line of Files
     *
     * @param p_countriesList Unformatted Countries data
     * @param p_continentList List of Continents
     * @return Formatted Countries data
     */
    public List<Country> parseCountriesData(List<String> p_countriesList, List<Continent> p_continentList) {
        List<Country> l_countriesList = new ArrayList<Country>();
        int l_country_id = 1;
        for (String country : p_countriesList) {
            String[] l_metaDataCountries = country.split(",");
            Continent l_continent = this.getContinentByName(p_continentList, l_metaDataCountries[3]);
            Country l_countryObj = new Country(l_country_id, l_metaDataCountries[0],
                    l_continent.getD_continentID());
            l_countriesList.add(l_countryObj);
            l_country_id++;
        }
        return l_countriesList;
    }

    /**
     * Retrieve formatted Neighbour data from Line of Files
     *
     * @param p_countriesList Countries data without neighbours
     * @param p_countryLines  Unformatted country data
     * @return Formatted Countries data with Neighbour
     */
    public List<Country> parseNeighbourData(List<Country> p_countriesList, List<String> p_countryLines) {

        List<Country> l_updatedCountryList = new ArrayList<>(p_countriesList);
        String l_matchedCountry = null;
        for (Country l_cont : l_updatedCountryList) {
            for (String l_contStr : p_countryLines) {
                if ((l_contStr.split(",")[0]).equalsIgnoreCase(l_cont.getD_countryName())) {
                    l_matchedCountry = l_contStr;
                    break;
                }
            }
            if (l_matchedCountry.split(",").length > 4) {
                for (int i = 4; i < l_matchedCountry.split(",").length; i++) {
                    Country l_country = this.getCountryByName(p_countriesList, l_matchedCountry.split(",")[i]);
                    l_cont.getD_neighbourCountryId().add(l_country.getD_countryId());
                }
            }
        }
        return l_updatedCountryList;
    }

    /**
     * Link Country to Continents
     *
     * @param p_countries  List of Countries
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

    /**
     * Filters continent based on continent name.
     *
     * @param p_continentList list of continents from which filtering has to be done
     * @param p_continentName name of the continent which has to be matched
     * @return filtered continent based on name
     */
    public Continent getContinentByName(List<Continent> p_continentList, String p_continentName) {
        return p_continentList.stream().filter(l_cont -> l_cont.getD_continentName().equalsIgnoreCase(p_continentName))
                .findFirst().orElse(null);
    }
    /**
     * Filters country based on country name.
     *
     * @param p_countrytList list of countries from which filtering has to be done
     * @param p_countryName name of the country which has to be matched
     * @return filtered country based on name
     */
    public Country getCountryByName(List<Country> p_countrytList, String p_countryName) {
        return p_countrytList.stream().filter(l_cont -> l_cont.getD_countryName().equalsIgnoreCase(p_countryName))
                .findFirst().orElse(null);
    }
}
