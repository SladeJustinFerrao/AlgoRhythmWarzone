package Views;

import Models.GameState;
import Models.Tournament;

import java.util.List;

public class TournamentView {
    Tournament d_tournament;

    List<GameState> d_gameStateObjects;

    public TournamentView(Tournament p_tournament) {
        d_tournament = p_tournament;
        d_gameStateObjects = d_tournament.getD_gameStateList();
    }
}
