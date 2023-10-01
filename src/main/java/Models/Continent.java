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

    /**
     * Getter method to get continent ID
     *
     * @return Continent ID
     */
    public Integer getD_continentID(){
        return d_continentID;
    }

    /**
     * Setter method to set continent ID
     *
     * @param p_continentID Continent ID
     */
    public void setD_continentID(int p_continentID) {
        this.d_continentID = p_continentID;
    }

    /**
     * Getter method to get continent name
     *
     * @return Continent Name
     */
    public String getD_continentName() {
        return d_continentName;
    }

    /**
     * Setter method to set continent name
     *
     * @param p_continentName Continent name
     */
    public void setD_continentName(String p_continentName) {
        this.d_continentName = p_continentName;
    }

    /**
     * Getter method to get the continent value
     *
     * @return Continent value
     */
    public Integer getD_continentValue() {
        return d_continentValue;
    }

    /**
     * Setter method to set the continent value
     *
     * @param p_continentValue Continent value
     */
    public void setD_continentValue(int p_continentValue) {
        this.d_continentValue = p_continentValue;
    }

    /**
     * Getter method to get the list of countries in the continent
     *
     * @return List of Countries
     */
    public List<Integer> getD_countries() {
        return d_countries;
    }

    /**
     * Setter method to set the List of countries in the continent
     *
     * @param p_countries List of Countries
     */
    public void setD_countries(List<Integer> p_countries) {
        this.d_countries = p_countries;
    }


    public void addCountry(int p_country) {
        if(d_countries!=null) {
            d_countries.add(p_country);
        } else {
            d_countries = new ArrayList<Integer>();
            d_countries.add(p_country);
        }
    }

    public void removeCountries (int p_country) {
        if(d_countries==null) {
            System.out.println("There are no countries to remove");
        } else {
            d_countries.remove(p_country);
        }
    }
}
