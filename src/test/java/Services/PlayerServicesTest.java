package Services;

import Models.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServicesTest {

    /**
     * Test to validate that the name of the player is unique or not
     */
    @Test
    void isPlayerNameUnique() {
        Player l_player1= new Player();
        Player l_player2= new Player();
        Player l_player3= new Player();
        Player l_player4= new Player();
        Player l_player5= new Player();
        Player l_player6= new Player();
        l_player1.setPlayerName("Darshan");
        l_player2.setPlayerName("Yug");
        l_player3.setPlayerName("Slade");
        l_player4.setPlayerName("Harshil");
        l_player5.setPlayerName("Beavans");
        l_player6.setPlayerName("Arjun");
        List<Player> l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_listOfPlayers.add(l_player3);
        l_listOfPlayers.add(l_player4);
        l_listOfPlayers.add(l_player5);
        l_listOfPlayers.add(l_player6);
        PlayerServices l_playerService = new PlayerServices();
        Boolean l_isUnique=l_playerService.isPlayerNameUnique(l_listOfPlayers,"Darshan");
        assertEquals(false,l_isUnique);
    }


    /**
     * Test to check the availability of the players
     */
    @Test
    void checkPlayersAvailability() {
        GameState l_gameState = new GameState();
        Player l_player1= new Player("Parth");
        Player l_player2= new Player("Jarvis");
        List l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_gameState.setD_players(l_listOfPlayers);
        PlayerServices l_playerServices= new PlayerServices();
        Boolean l_isAvailable = l_playerServices.checkPlayersAvailability(l_gameState);
        assertEquals(true,l_isAvailable);
    }


    /**
     * test to check that player cannot deploy more armies that there is in
     * their reinforcement pool
     */
    @Test
    void validateDeployOrderArmies() {
        Player l_player1= new Player("W3");
        PlayerServices l_playerServices= new PlayerServices();
        l_player1.setD_noOfUnallocatedArmies(15);
        String l_noOfArmies= "12";
        Boolean l_isValidDeployableArmy= l_playerServices.validateDeployOrderArmies(l_player1,l_noOfArmies);
        assertEquals(false,l_isValidDeployableArmy);

    }

    @Test
    void calculateArmiesForPlayer() {
        Player l_player1= new Player("Lucifer");
        Continent l_cont= new Continent();
        Country l_country= new Country("INDIA");
        l_cont.setD_continentName("ASIA");
        l_cont.setD_continentID(1);
        l_cont.setD_continentValue(6);
        List<Country> l_listOfCountries= new ArrayList<>();
        l_listOfCountries.add(l_country);
        l_cont.setD_countries(l_listOfCountries);
        List<Continent> l_ListOfContinent = new ArrayList<>();
        l_ListOfContinent.add(l_cont);
        l_player1.setD_continentsOwned(l_ListOfContinent);
        l_player1.setD_coutriesOwned(l_listOfCountries);
        PlayerServices l_playerService = new PlayerServices();
        int l_armyNumber=l_playerService.calculateArmiesForPlayer(l_player1);
        assertEquals(9,l_armyNumber);

    }





    @Test
    void unassignedArmiesExists() {
        PlayerServices l_playerServices= new PlayerServices();
        Player l_player1= new Player();
        Player l_player2= new Player();
        Player l_player3= new Player();
        l_player1.setPlayerName("Darshan");
        l_player2.setPlayerName("Yug");
        l_player3.setPlayerName("Slade");
        List<Player> l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_listOfPlayers.add(l_player3);
        l_player1.setD_noOfUnallocatedArmies(0);
        l_player2.setD_noOfUnallocatedArmies(0);
        l_player3.setD_noOfUnallocatedArmies(0);
        Boolean l_unassignedArmies= l_playerServices.unassignedArmiesExists(l_listOfPlayers);
        assertEquals(false,l_unassignedArmies);
    }



    @Test
    void isMapLoaded() {
        GameState l_gameState= new GameState();
        Player l_player1= new Player("Parth");
        Player l_player2= new Player("Jarvis");
        List l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_gameState.setD_players(l_listOfPlayers);
        PlayerServices l_playerServices= new PlayerServices();
        Boolean l_isMapload=l_playerServices.isMapLoaded(l_gameState);
        assertEquals(false,l_isMapload);

    }
}