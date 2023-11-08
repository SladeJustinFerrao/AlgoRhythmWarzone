package Services;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PlayerServices.java
 * @author Darshan Kansara
 */
class PlayerServicesTest {

    /**
     * Player class reference.
     */
    Player d_playerInfo;

    /**
     * Player Service reference.
     */
    PlayerServices d_playerService;

    /**
     * Map reference to store its object.
     */
    Map d_map;

    /**
     * GameState reference to store its object.
     */
    GameState d_gameState;

    /**
     * MapService reference to store its object.
     */
    MapService d_mapservice;

    /**
     * Existing Player List.
     */
    List<Player> d_exisitingPlayerList = new ArrayList<>();

    private final ByteArrayOutputStream d_outContent = new ByteArrayOutputStream();

    /**
     * The setup is called before each test case of this class is executed.
     */
    @BeforeEach
    public void setup() {
        d_playerInfo = new Player();
        d_playerService = new PlayerServices();
        d_gameState = new GameState();
        d_exisitingPlayerList.add(new Player("Darshan"));
        d_exisitingPlayerList.add(new Player("Slade"));

    }

    /**
     * The testAddPlayers is used to test the add functionality of addRemovePlayers
     * function.
     */
    @Test
    public void testAddPlayers() {
        assertFalse(d_exisitingPlayerList.size()==0);
        List<Player> l_updatedPlayers = d_playerService.addRemovePlayers(d_exisitingPlayerList, "add", "Darshans");
        assertEquals("Darshans", l_updatedPlayers.get(2).getPlayerName());

        System.setOut(new PrintStream(d_outContent));
        d_playerService.addRemovePlayers(d_exisitingPlayerList, "add", "Slade");
        assertEquals("Player with name : Slade  Exists already. Changes not made.", d_outContent.toString().trim());
    }

    /**
     * The testRemovePlayers is used to t est the remove functionality of
     * addRemovePlayers function.
     */
    @Test
    public void testRemovePlayers() {
        List<Player> l_updatedPlayers = d_playerService.addRemovePlayers(d_exisitingPlayerList, "remove", "Darshan");
        assertEquals(1, l_updatedPlayers.size());

        System.setOut(new PrintStream(d_outContent));
        d_playerService.addRemovePlayers(d_exisitingPlayerList, "remove", "Ravi");
        assertEquals("Player with name : Ravi does not Exist. Changes are not made.", d_outContent.toString().trim());
    }

    /**
     * Used for checking whether players have been added or not
     */
    @Test
    public void testPlayersAvailability() {
        boolean l_playersExists = d_playerService.checkPlayersAvailability(d_gameState);
        assertFalse(l_playersExists);
    }

    /**
     * Used for checking whether players have been assigned with countries
     */
    @Test
    public void testPlayerCountryAssignment() throws Exception {
        d_mapservice = new MapService();
        d_map = new Map();
        d_map = d_mapservice.loadMap(d_gameState, "canada.map");
        d_gameState.setD_map(d_map);
        d_gameState.setD_players(d_exisitingPlayerList);
        d_playerService.assignCountries(d_gameState);

        int l_assignedCountriesSize = 0;
        for (Player l_pl : d_gameState.getD_players()) {
            assertNotNull(l_pl.getD_coutriesOwned());
            l_assignedCountriesSize = l_assignedCountriesSize + l_pl.getD_coutriesOwned().size();
        }
        assertEquals(l_assignedCountriesSize, d_gameState.getD_map().getD_countries().size());
    }

    /**
     * The testCalculateArmiesForPlayer is used to calculate number of reinforcement
     * armies
     */
    @Test
    public void testCalculateArmiesForPlayer() {
        Player l_playerInfo = new Player();
        List<Country> l_countryList = new ArrayList<Country>();
        l_countryList.add(new Country("Waadt"));
        l_countryList.add(new Country("Neuenburg"));
        l_countryList.add(new Country("Fribourg"));
        l_countryList.add(new Country("Geneve"));
        l_playerInfo.setD_coutriesOwned(l_countryList);
        List<Continent> l_continentList = new ArrayList<Continent>();
        l_continentList.add(new Continent(1, "Asia", 5));
        l_playerInfo.setD_continentsOwned(l_continentList);
        l_playerInfo.setD_noOfUnallocatedArmies(10);
        Integer l_actualResult = d_playerService.calculateArmiesForPlayer(l_playerInfo);
        Integer l_expectedresult = 18;
        assertEquals(l_expectedresult, l_actualResult);
    }
}