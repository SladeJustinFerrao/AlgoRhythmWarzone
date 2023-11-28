package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * This class contains functions for map manipulations and validation
 */
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
     * All countries one can reach from exiting position are put in a hashmap.
     */
    HashMap<Integer, Boolean> d_countryConnectedStatus = new HashMap<Integer, Boolean>();

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
        for (Country country : d_countries) {
            System.out.println("Country Id " + country.getD_countryId());
            System.out.println("Continent Id " + country.getD_continentId());
            System.out.println("Neighbours:");
            for (int i : country.getD_neighbourCountryId()) {
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
     * Validate if map is correct
     *
     * @return true if map is successfully validated else false
     */
    public Boolean Validate() {
        return (checkNullObjects() && isContinentsConnected() && isCountriesConnected());
    }

    /**
     * Checks if objects in map are null
     *
     * @return False if there is no null objects else true
     */
    public Boolean checkNullObjects() {
        if(d_continents==null || d_continents.isEmpty()){
            System.out.println("Map must contain at least one continent!");
        }
        if(d_countries==null || d_countries.isEmpty()){
            System.out.println("Map must contain at least one country!");
        }
        for(Country c: d_countries){
            if(c.getD_neighbourCountryId().isEmpty()){
                System.out.println(c.getD_countryName()+" does not possess any neighbour, hence isn't reachable!");
            }
        }
        return true;
    }

    /**
     * Check if all continents are connected
     *
     * @return True if all continents are connected else false
     */
    public boolean isContinentsConnected() {
        boolean l_flagConnected = true;
        for (Continent continent : d_continents) {
            if (continent.getD_countries() == null || continent.getD_countries().isEmpty()) {
                System.out.println("Continent " + continent.getD_continentName() + " has no countries");
            }
            if (!continentsGraphConnected(continent)) {
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

        for (Country country : p_continent.getD_countries()) {
            l_countriesInContinent.put(country.getD_countryId(), false);
        }
        dfsContinents(p_continent.getD_countries().get(0), l_countriesInContinent, p_continent);

        for (java.util.Map.Entry<Integer, Boolean> entry : l_countriesInContinent.entrySet()) {
            if (!entry.getValue()) {
                Country l_country = retrieveCountry(entry.getKey());
                System.out.println(l_country.getD_countryName() + " is not connected");
            }
        }
        return !l_countriesInContinent.containsValue(false);
    }

    /**
     * Depth first search algorithm to check if countries in continents are connected
     *
     * @param p_country              Country to check
     * @param p_countriesInContinent Country ID along with boolean values denoting visited or not
     * @param p_continent            Continent to check
     */
    private void dfsContinents(Country p_country, HashMap<Integer, Boolean> p_countriesInContinent, Continent p_continent) {
        p_countriesInContinent.put(p_country.getD_countryId(), true);
        for (Country country : p_continent.getD_countries()) {
            if (p_country.getD_neighbourCountryId().contains(country.getD_countryId())) {
                if (!p_countriesInContinent.get(country.getD_countryId())) {
                    dfsContinents(country, p_countriesInContinent, p_continent);
                }
            }
        }
    }

    /**
     * Retrieve neighbour Country Objects.
     *
     * @param p_country neighbour country
     * @return list of neighbour Country Objects
     */
    public List<Country> getNeighbourCountry(Country p_country) {
        List<Country> l_neighbourCountries = new ArrayList<Country>();

        if (p_country.getD_neighbourCountryId().size() > 0) {
            for (int i : p_country.getD_neighbourCountryId()) {
                l_neighbourCountries.add(retrieveCountry(i));
            }
        } else {
            System.out.println(p_country.getD_countryName() + " doesn't contain any neighbour countries");
        }
        return l_neighbourCountries;
    }

    /**
     * Dfs search is applied to input data
     *
     * @param p_country First country visited
     */
    public void dfsCountry(Country p_country) {
        d_countryConnectedStatus.put(p_country.getD_countryId(), true);
        for (Country l_country : getNeighbourCountry(p_country)) {
            if (!d_countryConnectedStatus.get(l_country.getD_countryId())) {
                dfsCountry(l_country);
            }
        }
    }

    /**
     * connectivity of country is checked.
     *
     * @return Return true if all the countries are connected else return false
     */
    public boolean isCountriesConnected() {
        for (Country country : d_countries) {
            d_countryConnectedStatus.put(country.getD_countryId(), false);
        }
        dfsCountry(d_countries.get(0));

        for (java.util.Map.Entry<Integer, Boolean> entry : d_countryConnectedStatus.entrySet()) {
            if (!entry.getValue()) {
                System.out.println(retrieveCountry(entry.getKey()).getD_countryName() + " country is not accessible");
            }
        }
        return !d_countryConnectedStatus.containsValue(false);
    }

    /**
     * From a given country ID find the Country object .
     *
     * @param p_countryId country object ID
     * @return country object
     */
    public Country retrieveCountry(Integer p_countryId) {
        for (Country country : d_countries) {
            if (p_countryId == country.getD_countryId()) {
                return country;
            }
        }
        return null;
    }

    /**
     * From a given country Name find the Country object
     *
     * @param p_countryName country object name
     * @return country object
     */
    public Country getCountryByName(String p_countryName){
        for(Country l_countryName: d_countries){
            if(p_countryName.equals(l_countryName.getD_countryName())){
                return l_countryName;
            }
        }
        return null;
    }

    /**
     * Add country function which adds countries to the map.
     *
     * @param p_countryName Country Name which is to be added
     * @param p_continentName Name of Continent in which this country will be added
     */
    public void addCountry(String p_countryName, String p_continentName){
        int l_countryId;
        if(d_countries==null){
            d_countries= new ArrayList<Country>();
        }
        if(getCountryByName(p_countryName)==null){
            l_countryId=d_countries.size()>0? Collections.max(retrieveCountryID())+1:1;
            if(d_continents!=null && retrieveContinentID().contains(retrieveContinent(p_continentName).getD_continentID())){
                Country l_country= new Country(l_countryId, p_countryName, retrieveContinent(p_continentName).getD_continentID());
                d_countries.add(l_country);
                for (Continent c: d_continents) {
                    if (c.getD_continentName().equals(p_continentName)) {
                        c.addCountry(l_country);
                    }
                }
            } else{
                System.out.println("Continent doesn't exist! so country is not added");
            }
        }else{
            System.out.println(p_countryName+" Country"+" already Exists!");
        }
    }

    /**
     * Remove country function which removes countries from the map.
     *
     * @param p_countryName Name of country to be removed
     */
    public void removeCountry(String p_countryName) {
        if(d_countries!=null && getCountryByName(p_countryName)!=null) {
            for(Continent continent: d_continents){
                if(continent.getD_continentID().equals(getCountryByName(p_countryName).getD_continentId())){
                    continent.removeCountries(getCountryByName(p_countryName));
                }
                continent.removeCountryForAllNeighbours(getCountryByName(p_countryName).getD_countryId());
            }
            removeAllNeighbours(getCountryByName(p_countryName).getD_countryId());
            d_countries.remove(getCountryByName(p_countryName));

        }else{
            System.out.println(p_countryName+" Country"+" does not exist!");
        }
    }

    /**
     * Adds continent to the map
     *
     * @param p_continentName         Name of continent to be added
     * @param p_continentControlBonus Bonus armies given to players when control full continent
     */
    public void addContinent(String p_continentName, int p_continentControlBonus) {
        int l_continentID;

        if (d_continents != null) {
            l_continentID = d_continents.size() > 0 ? Collections.max(retrieveContinentID()) + 1 : 1;
            if (retrieveContinent(p_continentName) == null) {
                d_continents.add(new Continent(l_continentID, p_continentName, p_continentControlBonus));
            } else {
                System.out.println("Continent is already created");
            }
        } else {
            d_continents = new ArrayList<>();
            d_continents.add(new Continent(1, p_continentName, p_continentControlBonus));
        }
    }

    /**
     * Returns continent object that matches with continent name
     *
     * @param p_continentName Name of continent to retrieve
     * @return Continent object
     */
    public Continent retrieveContinent(String p_continentName) {
        for (Continent continent : d_continents) {
            if (p_continentName.equals(continent.getD_continentName())) {
                return continent;
            }
        }
        return null;
    }

    /**
     * Returns continent object that matches with continent id
     *
     * @param p_continentId Id of continent to retrieve
     * @return Continent object
     */
    public Continent retrieveContinentById(Integer p_continentId) {
        for (Continent continent : d_continents) {
            if (p_continentId.equals(continent.getD_continentID())) {
                return continent;
            }
        }
        return null;
    }

    /**
     * Remove Continent from map
     *
     * @param p_continentName Continent to remove
     */
    public void removeContinent(String p_continentName) {
        if (d_continents != null) {
            if (retrieveContinent(p_continentName) != null) {
                if (retrieveContinent(p_continentName).getD_countries() != null) {
                    for (Country l_country : retrieveContinent(p_continentName).getD_countries()) {
                        removeAllNeighbours(l_country.getD_countryId());
                        removeNeighboursFromContinents(l_country.getD_countryId());
                        d_countries.remove(l_country);
                    }
                }
                d_continents.remove(retrieveContinent(p_continentName));
            } else {
                System.out.println("Continent doesn't exist");
            }
        } else {
            System.out.println("There are no continents in the map");
        }
    }

    /**
     * Remove the country as neighbour in the continent object
     *
     * @param p_countryID Country to remove
     */
    private void removeNeighboursFromContinents(int p_countryID) {
        for (Continent l_continent : d_continents) {
            l_continent.removeCountryForAllNeighbours(p_countryID);
        }
    }

    /**
     * Remove the country as neighbour for all other countries
     *
     * @param p_countryID Country to remove
     */
    private void removeAllNeighbours(int p_countryID) {
        for (Country l_country : d_countries) {
            if (l_country.getD_neighbourCountryId() != null) {
                if (l_country.getD_neighbourCountryId().contains(p_countryID)) {
                    l_country.removeNeighbourFromCountry(p_countryID);
                }
            }
        }
    }

    /**
     * Add Neighbour functionality.
     *
     * @param p_countryName This Country neighbours will be updated
     * @param p_neighbourName Neighbour country
     */
    public void addCountryNeighbour(String p_countryName, String p_neighbourName) {
        if(d_countries!=null){
            if(getCountryByName(p_countryName)!=null && getCountryByName(p_neighbourName)!=null){
                d_countries.get(d_countries.indexOf(getCountryByName(p_countryName))).addNeighbourToCountry(getCountryByName(p_neighbourName).getD_countryId());
            } else{
                System.out.println("Neighbour Pair Invalid!");
            }
        }
    }

    /**
     * Remove Neighbor functionality.
     *
     * @param p_countryName update neighbour of the country
     * @param p_neighbourName neighbour country to be removed
     */
    public void removeCountryNeighbour(String p_countryName, String p_neighbourName) {
        if(d_countries!=null){
            if(getCountryByName(p_countryName)!=null && getCountryByName(p_neighbourName)!=null) {
                d_countries.get(d_countries.indexOf(getCountryByName(p_countryName))).removeNeighbourFromCountry(getCountryByName(p_neighbourName).getD_countryId());
            } else{
                System.out.println("Neighbour Pair Invalid!");
            }
        }
    }
}