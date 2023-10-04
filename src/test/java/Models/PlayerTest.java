package Models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getCountryNames() {
        Country l_country1= new Country("India");
        Country l_country2= new Country("Canada");
        Country l_country3= new Country("China");
        Country l_country4= new Country("USA");
        List l_listOfCountries = new ArrayList<>();
        l_listOfCountries.add(l_country1);
        l_listOfCountries.add(l_country2);
        l_listOfCountries.add(l_country3);
        Player l_player1= new Player("Lucifer");
        l_player1.setD_coutriesOwned(l_listOfCountries);
        List l_listOFCountriesForPlayer=l_player1.getCountryNames();
        List l_assertList= new ArrayList();
        l_assertList.add(l_country1.getD_countryName());
        l_assertList.add(l_country2.getD_countryName());
        l_assertList.add(l_country3.getD_countryName());
        assertEquals(l_assertList,l_listOFCountriesForPlayer);
    }
}