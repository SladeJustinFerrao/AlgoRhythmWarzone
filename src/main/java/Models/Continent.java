package Models;

import java.util.List;

public class Continent {
    /**
     * Continent ID
     */
    Integer d_continentID;

    /**
     * Continent name
     */
    String d_continentName;

    /**
     * Continent value
     */
    Integer d_continentValue;

    /**
     * Countries ID in Continent
     */
    List<Integer> d_countries;

    /**
     * Constructor
     *
     * @param d_continentID Continent ID
     * @param d_continentName Continent name
     * @param d_continentValue Continent value
     */
    public Continent(Integer d_continentID, String d_continentName, Integer d_continentValue) {
        this.d_continentID = d_continentID;
        this.d_continentName = d_continentName;
        this.d_continentValue = d_continentValue;
    }
  
    /**
     * Default constructor
     */
    public Continent() {

    }

    /**
     * Constructor
     *
     * @param d_continentName Continent name
     */
    public Continent(String d_continentName) {
        this.d_continentName = d_continentName;
    }
}