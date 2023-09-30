package Models;

public class Order {
    /**
     * Action of order
     */
    String d_ActionOfOrder;
    /**
     * Name of the targeted country
     */
    String d_CountryTargeted;
    /**
     * Name of the Source Country
     */
    String d_CountryOfSource;
    /**
     * Number of armies to be place
     */
    Integer d_ArmiesToPlace;
    /**
     * Object of the order class
     */
    Order OrderObj;

    public Order() {
    }
    
    public Order(String p_ActionOfOrder, String p_CountryTargeted, Integer p_ArmiesToPlace) {
        this.d_ActionOfOrder = p_ActionOfOrder;
        this.d_CountryTargeted = p_CountryTargeted;
        this.d_ArmiesToPlace = p_ArmiesToPlace;
    }
    
    public String getD_ActionOfOrder() {
        return d_ActionOfOrder;
    }
    
    public void setD_ActionOfOrder(String p_ActionOfOrder) {
        this.d_ActionOfOrder = p_ActionOfOrder;

    }
    
    public String getD_CountriesTargeted() {
        return d_CountryTargeted;

    }
    
    public void setD_CountriesTargeted(String p_CountriesTargeted) {
        this.d_CountryTargeted = p_CountriesTargeted;

    }
    
    public String getD_CountryOfSource() {
        return d_CountryOfSource;

    }
    
    public void setD_CountryOfSource(String p_CountryOfSource) {
        this.d_CountryOfSource = p_CountryOfSource;

    }
    
    public Integer getD_ArmiesToPlace() {
        return d_ArmiesToPlace;

    }
    
    public void setD_ArmiesToPlace(Integer p_ArmiesToPlace) {
        this.d_ArmiesToPlace = p_ArmiesToPlace;

    }
}
