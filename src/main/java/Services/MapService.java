package Services;

import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * The MapService Class holds all operations for the map.
 */
public class MapService {

    public void editMap(GameState p_gameState, String p_editFile) throws IOException {

    }

    public void editContinent(GameState p_gameState, String p_argument, String p_operation) throws IOException {

    }

    public Map continentsToMap(Map p_map, String p_argument, String p_operation) {
        return p_map;
    }

    public void editCountry(GameState p_gameState, String p_argument, String p_operation) {

    }

    public Map countryToMap(Map p_map, String p_argument, String p_operation) {
        return p_map;
    }

    public void editNeighbour(GameState p_gameState, String p_argument, String p_operation) {

    }

    public Map neighbourToMap(Map p_map, String p_argument, String p_operation) {
        return p_map;
    }

    public List<Continent> countryToContinents(List<Country> p_countries, List<Continent> p_continents) {
        return p_continents;
    }

    public boolean saveMap(GameState p_gameState, String p_fileName) throws IOException {
        return true;
    }

    private void writeCountryAndNeighbourData(GameState p_gameState, FileWriter p_writer) throws IOException {

    }

    private void writeContinentdata(GameState p_gameState, FileWriter p_writer) throws IOException {

    }
    public Map loadMap(GameState p_gameState, String p_loadFileName) {
        Map l_map = new Map();

        return l_map;
    }

    public List<String> loadFile(String p_loadFileName) {
        List<String> l_fileLines = new ArrayList<>();

        return l_fileLines;
    }

    public List<String> getFileData(List<String> p_fileLines, String p_switchParameter) {
        return p_fileLines;
    }

    public List<Continent> parseContinentsData(List<String> p_continentList) {
        List<Continent> l_continents = new ArrayList<Continent>();

        return l_continents;
    }

    public List<Country> parseCountriesData(List<String> p_countriesList) {
        List<Country> l_countries = new ArrayList<Country>();

        return l_countries;
    }

    public List<Country> parseNeighbourData(List<Country> p_countriesList, List<String> p_bordersList) {
        return p_countriesList;
    }

}
