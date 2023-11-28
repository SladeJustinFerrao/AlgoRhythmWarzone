package Views;

import Constants.GameConstants;
import Models.GameState;
import Models.Tournament;

import java.util.List;

public class TournamentView {

    /**
     * Tournament Object to be displayed.
     */
    Tournament d_tournament;

    /**
     * List of GameState Objects from tournament.
     */
    List<GameState> d_gameStateObjects;

    /**
     * Constructor setting for tournament object.
     *
     * @param p_tournament tournament object
     */
    public TournamentView(Tournament p_tournament) {
        d_tournament = p_tournament;
        d_gameStateObjects = d_tournament.getD_gameStateList();
    }

    /**
     * Renders the Center String for Heading.
     *
     * @param p_width Defined width in formatting.
     * @param p_s String to be rendered.
     */
    private void renderCenteredString (int p_width, String p_s) {
        String l_centeredString = String.format("%-" + p_width  + "s", String.format("%" + (p_s.length() + (p_width - p_s.length()) / 2) + "s", p_s));
        System.out.format(l_centeredString+"\n");
    }

    /**
     * Renders the Separator for heading.
     *
     */
    private void renderSeparator(){
        StringBuilder l_separator = new StringBuilder();

        for (int i = 0; i< GameConstants.CONSOLE_WIDTH -2; i++){
            l_separator.append("-");
        }
        System.out.format("+%s+%n", l_separator.toString());
    }
}
