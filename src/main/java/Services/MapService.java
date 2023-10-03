package Services;

import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The MapService Class holds all operations for the map.
 */
public class MapService {

    /**
     * Method used to modify the Map
     *
     * @param p_gameState Current State of the game
     * @param p_editFile File name
     * @throws IOException
     */
    public void editMap(GameState p_gameState, String p_editFile) throws IOException {

        String l_filePath = getFilePath(p_editFile);
        File l_fileToBeEdited = new File(l_filePath);

        if (l_fileToBeEdited.createNewFile()) {
            Map l_map = new Map();
            l_map.setD_mapFile(p_editFile);
            p_gameState.setD_map(l_map);
            System.out.println("New Map File has been Created");
        } else {
            System.out.println("Map File is present");
            this.loadMap(p_gameState, l_filePath);
            p_gameState.setD_map(new Map());
            p_gameState.getD_map().setD_mapFile(p_editFile);
        }
    }

    /**
     * Method to Modify Continents
     * @param p_gameState Current state of the game
     * @param p_argument Arguments in the command
     * @param p_operation Operation of the command
     * @throws IOException
     */
    public void editContinent(GameState p_gameState, String p_argument, String p_operation) throws IOException {
        String l_fileName = p_gameState.getD_map().getD_mapFile();
        Map l_map = p_gameState.getD_map();

        if(l_map!=null) {
            Map l_updatedMap = continentsToMap(l_map, p_argument, p_operation);
            p_gameState.setD_map(l_updatedMap);
        }
    }

    /**
     * Link Continents to Map
     * @param p_map Map object
     * @param p_argument Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map continentsToMap(Map p_map, String p_argument, String p_operation) {
        String[] l_arguments = p_argument.split(" ");
        if (p_operation.equalsIgnoreCase("add") && l_arguments.length==2) {
            p_map.addContinent(l_arguments[0], Integer.parseInt(l_arguments[1]));
        } else if (p_operation.equalsIgnoreCase("remove") && l_arguments.length==1) {
            p_map.removeContinent(l_arguments[0]);
        } else {
            System.out.println("Error! Continents could not be modified, no changes made.");
        }

        return p_map;
    }

    /**
     * Method to Modify Countries
     * @param p_gameState Current state of the game
     * @param p_argument Arguments in the command
     * @param p_operation Operation of the command
     */
    public void editCountry(GameState p_gameState, String p_argument, String p_operation) {
        String l_fileName= p_gameState.getD_map().getD_mapFile();
        Map l_map = p_gameState.getD_map();

        if(l_map!=null){
            Map l_updatedMap = countryToMap(l_map, p_argument, p_operation);
            p_gameState.setD_map(l_updatedMap);
        }
    }

    /**
     * Link Countries to Map
     * @param p_map Map object
     * @param p_argument Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map countryToMap(Map p_map, String p_argument, String p_operation) {
        String[] l_arguments = p_argument.split(" ");
        if (p_operation.equalsIgnoreCase("add") && l_arguments.length==2){
            p_map.addCountry(l_arguments[0], l_arguments[1]);
        }else if(p_operation.equalsIgnoreCase("remove") && l_arguments.length==1){
            p_map.removeCountry(l_arguments[0]);
        }else{
            System.out.println("Error! Contries could not be modified, no changes made.");
        }

        return p_map;
    }

    /**
     * Method to Modify neighbouring countries
     * @param p_gameState Current state of the game
     * @param p_argument Arguments in the command
     * @param p_operation Operation of the command
     */
    public void editNeighbour(GameState p_gameState, String p_argument, String p_operation) {
        String l_fileName= p_gameState.getD_map().getD_mapFile();
        Map l_map = p_gameState.getD_map();

        if(l_map!=null){
            Map l_updatedMap = neighbourToMap(l_map, p_argument, p_operation);
            p_gameState.setD_map(l_updatedMap);
        }
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

    /**
     * Method to load Existing Map file
     *
     * @param p_gameState Current State of the Game
     * @param p_filePath Path of the filename provided
     * @return
     */
    public Map loadMap(GameState p_gameState, String p_filePath) {
        Map l_map = new Map();
        List<String> l_linesOfFile = loadFile(p_filePath);

        //Set Map content
        return l_map;
    }

    /**
     * Method to extract contents of the file
     *
     * @param p_filePath Path of the filename provided
     * @return
     */
    public List<String> loadFile(String p_filePath) {
        List<String> l_fileLines = new ArrayList<>();

        BufferedReader l_reader;
        try {
            l_reader = new BufferedReader(new FileReader(p_filePath));
            l_fileLines = l_reader.lines().collect(Collectors.toList());
            l_reader.close();
        } catch (IOException l_e1) {
            System.out.println("File not Found!");
        }
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

    public static String getFilePath(String p_fileName) {
        return new File("").getAbsolutePath() + File.separator + "src/main/resources" + File.separator + p_fileName;
    }

}
