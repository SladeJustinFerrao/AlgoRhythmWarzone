package Services;

import Models.GameState;
import Models.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServicesTest {

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

    @Test
    void addRemovePlayers() {

    }

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

    @Test
    void assignCountries() {
    }

    @Test
    void createDeployOrder() {
    }

    @Test
    void validateDeployOrderArmies() {
    }

    @Test
    void calculateArmiesForPlayer() {
    }

    @Test
    void assignArmies() {
    }

    @Test
    void unexecutedOrdersExists() {
    }

    @Test
    void unassignedArmiesExists() {
    }

    @Test
    void updatePlayers() {
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