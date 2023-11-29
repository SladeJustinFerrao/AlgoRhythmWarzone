package Services;

import Constants.GameConstants;
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The MapService Class holds all operations for the map.
 */
public class MapService implements Serializable {

    /**
     * Method used to modify the Map
     *
     * @param p_gameState Current State of the game
     * @param p_editFile File name
     * @throws IOException Exception
     */
    public void editMap(GameState p_gameState, String p_editFile) throws IOException {

        String l_filePath = getFilePath(p_editFile);
        File l_fileToBeEdited = new File(l_filePath);

        if (l_fileToBeEdited.createNewFile()) {
            Map l_map = new Map();
            l_map.setD_mapFile(p_editFile);
            p_gameState.setD_map(l_map);
            p_gameState.updateLog(l_filePath+ " File has been created for user to edit", GameConstants.OUTCOME);
            System.out.println("New Map File has been Created");
        } else {
            System.out.println("Map File is present");
            p_gameState.setD_map(new Map());
            this.loadMap(p_gameState, p_editFile);
            p_gameState.getD_map().setD_mapFile(p_editFile);
            p_gameState.updateLog(l_filePath+ " already exists and is loaded for editing", GameConstants.OUTCOME);
        }
    }

    /**
     * Function to update map with continents,country,neighbour
     * @param p_gameState GameState
     * @param p_argument Arguments
     * @param p_operation Operation
     * @param p_switch Switch parameter
     * @throws IOException Exception
     */
    public void editFunctions(GameState p_gameState, String p_argument, String p_operation, Integer p_switch) throws Exception {
        Map l_updatedMap;
        String l_mapFileName = p_gameState.getD_map().getD_mapFile();
        Map l_map = ((p_gameState.getD_map().getD_continents())==null && (p_gameState.getD_map().getD_countries())==null) ? this.loadMap(p_gameState, l_mapFileName) : p_gameState.getD_map();

        // Edit Control Logic for Continent, Country & Neighbor
        if(l_map!=null){
            switch(p_switch){
                case 1:
                    l_updatedMap = continentsToMap(p_gameState, l_map, p_argument, p_operation);
                    break;
                case 2:
                    l_updatedMap = countryToMap(p_gameState, l_map, p_argument, p_operation);
                    break;
                case 3:
                    l_updatedMap = neighbourToMap(p_gameState, l_map, p_argument, p_operation);
                    break;
                default:
                    throw new IllegalStateException("Error!! Illegal value: " + p_switch);
            }
            p_gameState.setD_map(l_updatedMap);
            p_gameState.getD_map().setD_mapFile(l_mapFileName);
        }
    }

    /**
     * Link Continents to Map
     *
     * @param p_gameState GameState
     * @param p_map       Map object
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map continentsToMap(GameState p_gameState, Map p_map, String p_argument, String p_operation) throws Exception{
        try {
            String[] l_arguments = p_argument.split(" ");
            if (p_operation.equalsIgnoreCase("add") && l_arguments.length == 2) {
                p_map.addContinent(l_arguments[0], Integer.parseInt(l_arguments[1]));
                this.setMapServiceLog("Continent " + l_arguments[0] + " added successfully!", p_gameState);
            } else if (p_operation.equalsIgnoreCase("remove") && l_arguments.length == 1) {
                p_map.removeContinent(l_arguments[0]);
                this.setMapServiceLog("Continent " + l_arguments[0] + " removed successfully!", p_gameState);
            } else {
                throw new Exception("Continent " +l_arguments[0] + " couldn't be added/removed. Changes are not made due to Invalid Command Passed.");
            }
        } catch(Exception l_exp){
            this.setMapServiceLog(l_exp.getMessage(), p_gameState);
        }
        return p_map;
    }

    /**
     * Link Countries to Map
     *
     * @param p_gameState GameState
     * @param p_map       Map object
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map countryToMap(GameState p_gameState, Map p_map, String p_argument, String p_operation) throws Exception {
        try {
            String[] l_arguments = p_argument.split(" ");
            if (p_operation.equalsIgnoreCase("add") && l_arguments.length == 2) {
                p_map.addCountry(l_arguments[0], l_arguments[1]);
                this.setMapServiceLog("Country " + l_arguments[0] + " added successfully!", p_gameState);
            } else if (p_operation.equalsIgnoreCase("remove") && l_arguments.length == 1) {
                p_map.removeCountry(l_arguments[0]);
                this.setMapServiceLog("Country " + l_arguments[0] + " removed successfully!", p_gameState);
            } else {
                throw new Exception("Country " + l_arguments[0] + " could not be " + p_operation + "ed!");
            }
        } catch(Exception l_exp){
            this.setMapServiceLog(l_exp.getMessage(), p_gameState);
        }
        return p_map;
    }

    /**
     * Link Neighbours to Map
     *
     * @param p_gameState GameState
     * @param p_map       Map object
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map neighbourToMap(GameState p_gameState, Map p_map, String p_argument, String p_operation) throws Exception {
        String[] l_arguments = p_argument.split(" ");
        if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length==2){
            p_map.addCountryNeighbour(l_arguments[0], l_arguments[1]);
            this.setMapServiceLog("Neighbour Pair "+ l_arguments[0] +" "+p_argument.split(" ")[1]+" added successfully!", p_gameState);
        }else if(p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length==2){
            p_map.removeCountryNeighbour(l_arguments[0], l_arguments[1]);
            this.setMapServiceLog("Neighbour Pair "+l_arguments[0]+" "+p_argument.split(" ")[1]+" removed successfully!", p_gameState);
        }else{
            throw new Exception("Neighbour could not be "+ p_operation +"ed!");
        }

        return p_map;
    }

    /**
     * Method to save the map
     * @param p_gameState Current Game State
     * @param p_fileName Name of the file
     * @return boolean true if map save was successful else false
     */
    public boolean saveMap(GameState p_gameState, String p_fileName){
        try {
            if (!p_fileName.equalsIgnoreCase(p_gameState.getD_map().getD_mapFile())) {
                p_gameState.setError("Filename to save does not match with the name provided for edit. Kindly provide the same name.");
                return false;
            } else {
                if (null != p_gameState.getD_map()) {
                    Models.Map l_currentMap = p_gameState.getD_map();

                    this.setMapServiceLog("Running Map Validation........", p_gameState);
                    boolean l_mapValidationStatus = l_currentMap.Validate();
                    if (l_mapValidationStatus) {
                        String l_format = this.getSaveFormat();
                        Files.deleteIfExists(Paths.get(getFilePath(p_fileName)));
                        FileWriter l_writer = new FileWriter(getFilePath(p_fileName));

                        writeToFile(p_gameState, l_writer, l_format);
                        p_gameState.updateLog("Map Saved Successfully", GameConstants.OUTCOME);
                        l_writer.close();
                    }
                } else {
                    p_gameState.updateLog("Validation failed! Cannot Save the Map file!", GameConstants.OUTCOME);
                    p_gameState.setError("Validation Failed");
                    return false;
                }
            }
            return true;
        } catch (IOException l_e) {
            this.setMapServiceLog(l_e.getMessage(), p_gameState);
            p_gameState.updateLog("Couldn't save the changes in map file!", GameConstants.OUTCOME);
            p_gameState.setError("Error in saving map file");
            return false;
        }
    }

    /**
     * Checks in what format user wants to save the map file.
     *
     * @return String map format to be saved
     * @throws IOException exception in reading inputs from user
     */
    public String getSaveFormat() throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Kindly press \"C\" to save the map as Conquest Map format and \"D\" for Domination Map format.");
        String l_nextOrderCheck = l_reader.readLine();
        if (l_nextOrderCheck.equalsIgnoreCase("C")) {
            return "Conquest";
        } else if (l_nextOrderCheck.equalsIgnoreCase("D")) {
            return "Domination";
        } else {
            System.err.println("Invalid Input.");
            return this.getSaveFormat();
        }
    }

    /**
     * Parses the Map Object to File.
     *
     * @param p_gameState current gamestate
     * @param l_writer file writer object.
     * @throws IOException Exception
     */
    private void writeToFile(GameState p_gameState, FileWriter l_writer, String l_format) throws IOException {
        if(l_format.equalsIgnoreCase("Conquest")) {
            MapWriterAdapter l_mapWriterAdapter = new MapWriterAdapter(new ConquestMapWriter());
            l_mapWriterAdapter.writeToFile(p_gameState, l_writer);
        } else {
            new MapFileWriter().writeToFile(p_gameState, l_writer);
        }
    }

    /**
     * Method to load Existing Map file
     *
     * @param p_gameState Current State of the Game
     * @param p_fileName Name of the file.
     * @return
     */
    public Map loadMap(GameState p_gameState, String p_fileName) {
        Map l_map = new Map();
        l_map.setD_mapFile(p_fileName);
        String l_filepath = this.getFilePath(p_fileName);
        List<String> l_linesOfFile = loadFile(l_filepath,p_gameState);

        if (null != l_linesOfFile && !l_linesOfFile.isEmpty()) {

            if(l_linesOfFile.contains("[Territories]")) {
                MapReaderAdapter l_mapReaderAdapter = new MapReaderAdapter(new ConquestMapReader());
                l_mapReaderAdapter.extractMap(p_gameState, l_map, l_linesOfFile);
            } else if(l_linesOfFile.contains("[countries]")) {
                new MapFileReader().extractMap(p_gameState, l_map, l_linesOfFile);
            }

        }

        return l_map;
    }

    /**
     * Method to extract contents of the file
     *
     * @param p_filePath Path of the filename provided
     * @return Lines of file
     */
    public List<String> loadFile(String p_filePath,GameState p_gameState) {
        List<String> l_fileLines = new ArrayList<>();
        BufferedReader l_reader;
        try {
            l_reader = new BufferedReader(new FileReader(p_filePath));
            l_fileLines = l_reader.lines().collect(Collectors.toList());
            l_reader.close();
        } catch (IOException l_e1) {
            System.out.println("File not Found!");
            this.setMapServiceLog("File not Found!",p_gameState);
        }
        return l_fileLines;
    }

    /**
     * Fetch the file path.
     *
     * @param p_fileName Name of file.
     * @return Path of file
     */
    public String getFilePath(String p_fileName) {
        return new File("").getAbsolutePath() + File.separator + "src/main/resources" + File.separator + p_fileName;
    }

    /**
     * Method to reset the map in case of Invalid map
     * @param p_gameState GameState
     * @param p_fileToLoad File Name
     */
    public void resetMap(GameState p_gameState, String p_fileToLoad) {
        System.out.println("Invalid Map!! Map cannot be loaded. Kindly provide a valid map");
        p_gameState.updateLog(p_fileToLoad+" map could not be loaded as it is invalid!", GameConstants.OUTCOME);
        p_gameState.setD_map(new Models.Map());
    }

    /**
     * Method to set logs
     * @param p_Log String containing log
     * @param p_gameState current gamestate instance
     */
    public void setMapServiceLog(String p_Log, GameState p_gameState){
        System.out.println(p_Log);
        p_gameState.updateLog(p_Log, GameConstants.OUTCOME);
    }
}
