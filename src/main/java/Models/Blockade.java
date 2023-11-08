package Models;

public abstract class Blockade implements Card{

    /**
     * Blockade card will be owned by this player.
     */
    Player d_playerInitiator;

    /**
     * name of the target country.
     */
    String d_targetCountryID;

    /**
     * Sets the Log containing Information about orders.
     */
    String d_orderExecutionLog;

    /**
     * The constructor gathers all the parameters to implement the order.
     *
     * @param p_playerInitiator Player
     * @param p_targetCountry   target country ID
     */
    public Blockade(Player p_playerInitiator, String p_targetCountry) {
        this.d_playerInitiator = p_playerInitiator;
        this.d_targetCountryID = p_targetCountry;
    }
}
