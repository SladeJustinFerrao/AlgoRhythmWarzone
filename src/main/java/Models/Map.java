package Models;

import java.util.ArrayList;
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

}