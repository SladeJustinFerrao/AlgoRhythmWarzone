package Models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Order.java
 * @author Harshil Prajapati
 */
class OrderTest {
    /**
     * Test to check if player owns the country or not
     */
    @Test
    void doesPlayerHaveDeployCountry() {
        Player l_player = new Player("Harshil");
        Order l_order = new Order("deploy", "India", 5);

        Country India = new Country(1, "India", 1);
        Country China = new Country(2, "China", 1);
        Country Canada = new Country(4, "Canada", 2);
        List<Country> l_countriesOwned = new ArrayList<Country>();
        l_countriesOwned.add(India);
        l_countriesOwned.add(China);
        l_countriesOwned.add(Canada);
        l_player.setD_coutriesOwned(l_countriesOwned);

        boolean l_doesPlayerHaveDeployCountry = l_order.doesPlayerHaveDeployCountry(l_player, l_order);
        assertEquals(true, l_doesPlayerHaveDeployCountry);
        l_order.setD_CountryTargeted("USA");
        l_doesPlayerHaveDeployCountry = l_order.doesPlayerHaveDeployCountry(l_player, l_order);
        assertEquals(false, l_doesPlayerHaveDeployCountry);
    }

    /**
     * Test to check if armies of the country updates correctly after deploy command or not
     */
    @Test
    void executeDeployedOrder() {
        Order l_order = new Order("deploy", "India", 5);
        GameState l_gameState = new GameState();
        Player l_player = new Player("Harshil");

        Map l_map = new Map();
        Country India = new Country(1, "India", 1);
        Country China = new Country(2, "China", 1);
        Country Canada = new Country(4, "Canada", 2);
        List<Country> l_countries = new ArrayList<Country>();
        India.setD_armies(5);
        l_countries.add(India);
        l_countries.add(China);
        l_countries.add(Canada);
        l_map.setD_countries(l_countries);
        l_gameState.setD_map(l_map);

        l_order.executeDeployedOrder(l_order, l_gameState, l_player);
        assertEquals(10, India.getD_armies());
    }
}