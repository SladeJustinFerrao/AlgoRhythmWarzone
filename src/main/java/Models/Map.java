package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    /**
     * stores the map file name.
     */
    String d_mapFile;

    /**
     * List of Continents
     */
    List<Continent> d_continents;

    /**
     * List of Countries
     */
    List<Country> d_countries;

    /**
     * getter method for map file.
     *
     * @return d_mapfile
     */
    public String getD_mapFile() {
        return d_mapFile;
    }

    /**
     * setter method for map file.
     *
     * @param p_mapFile file name of map
     */
    public void setD_mapFile(String p_mapFile) {
        this.d_mapFile = p_mapFile;
    }

    /**
     * Getter method to get list of continents
     *
     * @return List of continents
     */
    public List<Continent> getD_continents() {
        return d_continents;
    }

    /**
     * Setter method to set list of continents
     *
     * @param d_continents List of continents
     */
    public void setD_continents(List<Continent> d_continents) {
        this.d_continents = d_continents;
    }

    /**
     * Getter method to get the list of countries
     *
     * @return List of countries
     */
    public List<Country> getD_countries() {
        return d_countries;
    }

    /**
     * Setter method to set the list of countries
     *
     * @param d_countries List of countries
     */
    public void setD_countries(List<Country> d_countries) {
        this.d_countries = d_countries;
    }

    /**
     * adds country to the list of countries
     *
     * @param p_country country to append
     */
    public void appendCountry(Country p_country) {
        d_countries.add(p_country);
    }

    /**
     * Gives you list of all country Ids in a List.
     *
     * @return countryId List
     */
    public List<Integer> retrieveCountryID() {
        List<Integer> l_countryIDs = new ArrayList<>();
        if (!d_countries.isEmpty()) {
            for (Country country : d_countries) {
                l_countryIDs.add(country.getD_countryId());
            }
        }
        return l_countryIDs;
    }
    /**
     * shows Info related to all countries
     */
    public void showCountriesInfo() {
        for (Country country: d_countries) {
            System.out.println("Country Id "+ country.getD_countryId());
            System.out.println("Continent Id "+country.getD_continentId());
            System.out.println("Neighbours:");
            for (int i: country.getD_neighbourCountryId()) {
                System.out.println(i);
            }
        }
    }

    /**
     * Adds the continent to the list of continents
     *
     * @param p_continent Continent to add to list
     */
    public void appendContinent(Continent p_continent) {
        d_continents.add(p_continent);
    }

    /**
     * Obtain a list of all the Continent IDs in play
     *
     * @return List of continent IDs
     */
    public List<Integer> retrieveContinentID() {
        List<Integer> l_continentID = new ArrayList<>();
        if (!d_continents.isEmpty()) {
            for (Continent continent : d_continents) {
                l_continentID.add(continent.getD_continentID());
            }
        }
        return l_continentID;
    }

    /**
     * Check continents in play and print out their IDs
     */
    public void showContinentInfo() {
        for (Continent continent : d_continents) {
            System.out.println(continent.getD_continentID());
        }
    }

    /**
     * Check if all continents are connected
     *
     * @return True if all continents are connected else false
     */
    public boolean isContinentsConnected() {
        boolean l_flagConnected = true;
        for (Continent continent : d_continents) {
            if(continent.getD_countries()== null || continent.getD_countries().size()<1) {
                System.out.println("Continent " + continent.getD_continentName() + " has no countries");
            }
            if(!continentsGraphConnected(continent)) {
                l_flagConnected = false;
            }
        }
        return l_flagConnected;
    }

    /**
     * check if the continents are connected through their countries
     *
     * @param p_continent Continent to check
     * @return True if continents are connected else false
     */
    private boolean continentsGraphConnected(Continent p_continent) {
        HashMap<Integer, Boolean> l_countriesInContinent = new HashMap<>();

        for(Country country : p_continent.getD_countries()) {
            l_countriesInContinent.put(country.getD_countryId(), false);
        }
        dfsContinents(p_continent.getD_countries().get(0), l_countriesInContinent, p_continent);

        for(java.util.Map.Entry<Integer, Boolean> entry : l_countriesInContinent.entrySet()) {
            if(!entry.getValue()) {
                System.out.println("Country ID " + entry.getKey() + "is not reachable");
            }
        }
        return !l_countriesInContinent.containsValue(false);
    }

    /**
     * Depth first search algorithm to check if countries in continents are connected
     *
     * @param p_country Country to check
     * @param p_countriesInContinent Country ID along with boolean values denoting visited or not
     * @param p_continent Continent to check
     */
    private void dfsContinents(Country p_country, HashMap<Integer, Boolean> p_countriesInContinent, Continent p_continent) {
         p_countriesInContinent.put(p_country.getD_countryId(), true);
         for(Country country: p_continent.getD_countries()) {
             if(p_country.getD_neighbourCountryId().contains(country.getD_countryId())) {
                 if(!p_countriesInContinent.get(country.getD_countryId())) {
                     dfsContinents(country, p_countriesInContinent, p_continent);
                 }
             }
         }
    }

}