package Models;
import java.util.ArrayList;
import java.util.List;
public class Country {

    /**
     * country ID
     */
    int d_countryId;
    /**
     * country Name
     */
    String d_countryName;
    /**
     * continent ID
     */
    int d_continentId;
    /**
     * How much armies it contains
     */
    int d_armies;

    /**
     * parameterised constructor contains 3 parameters
     *
     * @param p_countryId   country ID
     * @param p_countryName country Name
     * @param p_continentId continent Id
     */
    public Country(int p_countryId, String p_countryName, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_countryName = p_countryName;
        this.d_continentId = p_continentId;
    }

    /**
     * parameterised constructor contains 2 parameters
     *
     * @param p_countryId   country ID
     * @param p_continentId continent Id
     */
    public Country(int p_countryId, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_continentId = p_continentId;
    }

    /**
     * parameterised constructor contains 1 parameter
     *
     * @param p_countryName country Name
     */
    public Country(String p_countryName) {
        this.d_countryName = p_countryName;
    }
    /**
     * getter method to get the armies.
     * @return armies
     */
    public int getD_armies() {
        return d_armies;
    }
    /**
     * setter method to set armies
     * @param p_armies armies
     */
    public void setD_armies(int p_armies) {
        this.d_armies = p_armies;
    }
}
 
