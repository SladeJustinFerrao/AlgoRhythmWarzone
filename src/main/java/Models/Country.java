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
     * list of countries neighbour to the given country
     */
    List<Integer> d_neighbourCountryId = new ArrayList<Integer>();

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
    /**
      * getter method to get country ID
      * @return country ID
     */
    public int getD_countryId() {
        return d_countryId;
    }
    /**
     * getter method which gives us country Name
     * @return country Name
     */
    public String getD_countryName() {
        return d_countryName;
    }
    /**
     * getter method to get the continent Id.
     * @return continent Id
     */
    public int getD_continentId() {
        return d_continentId;
    }
    /**
     * getter method which gives us neighbour country Ids
     * @return  neighbour Country Id
     */
    public List<Integer> getD_neighbourCountryId() {
        return d_neighbourCountryId;
    }
    /**
     * setter which sets neighbour country Id.
     * @param p_neighbourCountryId all neighbour country Id
     */
    public void setD_neighbourCountryId(List<Integer> p_neighbourCountryId) {
        this.d_neighbourCountryId = p_neighbourCountryId;
    }
    /**
     * setter method to set country Id
     * @param p_countryId country Id
     */
    public void setD_countryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }
    /**
     * setter method to set country Name
     * @param p_countryName country Name
     */
    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }
    /**
     * setter method to set continent Id
     * @param p_continentId continent Id
     */
    public void setD_continentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }
     /**
     * removeNeighbour method removes country Id from  neighbour Country Id list.
     *
     * @param p_countryId country Id which is to be removed
     */
    public void removeNeighbourFromCountry(Integer p_countryId){
        if(!d_neighbourCountryId.contains(p_countryId)){
            System.out.println("Neighbour does not Exists");

        }else{
            d_neighbourCountryId.remove(d_neighbourCountryId.indexOf(p_countryId));
        }
    }
     
}
 
