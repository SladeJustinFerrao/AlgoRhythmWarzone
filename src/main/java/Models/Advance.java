package Models;

import java.util.List;

public class Advance implements Order {
    private final String d_targetCountryName;
    private final String d_sourceCountryName;
    private final int d_numberOfArmiesToPlace;
    private final Player d_playerInitiator;
    private String d_orderExecutionLog;

    public Advance(Player p_playerInitiator, String p_sourceCountryName, String p_targetCountry, int p_numberOfArmiesToPlace) {
        this.d_targetCountryName = p_targetCountry;
        this.d_sourceCountryName = p_sourceCountryName;
        this.d_playerInitiator = p_playerInitiator;
        this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
    }

    @Override
    public void execute(GameState p_gameState) {
        if (valid(p_gameState)) {
            Player l_playerOfTargetCountry = getPlayerOfTargetCountry(p_gameState);
            Country l_targetCountry = p_gameState.getMap().getCountryByName(d_targetCountryName);
            Country l_sourceCountry = p_gameState.getMap().getCountryByName(d_sourceCountryName);
            int l_sourceArmiesToUpdate = l_sourceCountry.getArmies() - d_numberOfArmiesToPlace;
            l_sourceCountry.setArmies(l_sourceArmiesToUpdate);

            if (l_playerOfTargetCountry.getPlayerName().equalsIgnoreCase(d_playerInitiator.getPlayerName())) {
                deployArmiesToTarget(l_targetCountry);
            } else if (l_targetCountry.getArmies() == 0) {
                conquerTargetCountry(p_gameState, l_playerOfTargetCountry, l_targetCountry);
                d_playerInitiator.assignCard();
            } else {
                produceOrderResult(p_gameState, l_playerOfTargetCountry, l_targetCountry, l_sourceCountry);
            }
        } else {
            p_gameState.updateLog(orderExecutionLog(), "effect");
        }
    }

    private void produceOrderResult(GameState p_gameState, Player p_playerOfTargetCountry, Country p_targetCountry,
                                    Country p_sourceCountry) {
        int l_armiesInAttack = Math.min(d_numberOfArmiesToPlace, p_targetCountry.getArmies());

        List<Integer> l_attackerArmies = generateRandomArmyUnits(l_armiesInAttack, "attacker");
        List<Integer> l_defenderArmies = generateRandomArmyUnits(l_armiesInAttack, "defender");

        produceBattleResult(p_sourceCountry, p_targetCountry, l_attackerArmies, l_defenderArmies, p_playerOfTargetCountry);
        p_gameState.updateLog(orderExecutionLog(), "effect");
        updateContinents(d_playerInitiator, p_playerOfTargetCountry, p_gameState);
    }

    private void conquerTargetCountry(GameState p_gameState, Player p_playerOfTargetCountry, Country p_targetCountry) {
        p_targetCountry.setArmies(d_numberOfArmiesToPlace);
        p_playerOfTargetCountry.removeCountry(p_targetCountry);
        d_playerInitiator.addCountry(p_targetCountry);

        setD_orderExecutionLog("Player : " + d_playerInitiator.getPlayerName() + " is assigned with Country : " +
                p_targetCountry.getCountryName() + " and armies : " + p_targetCountry.getArmies(), "default");
        p_gameState.updateLog(orderExecutionLog(), "effect");
        updateContinents(d_playerInitiator, p_playerOfTargetCountry, p_gameState);
    }

    private Player getPlayerOfTargetCountry(GameState p_gameState) {
        for (Player l_player : p_gameState.getPlayers()) {
            String l_cont = l_player.getCountryNames().stream()
                    .filter(l_country -> l_country.equalsIgnoreCase(d_targetCountryName)).findFirst().orElse(null);
            if (l_cont != null) {
                return l_player;
            }
        }
        return null;
    }

    public void deployArmiesToTarget(Country p_targetCountry) {
        int l_updatedTargetContArmies = p_targetCountry.getArmies() + d_numberOfArmiesToPlace;
        p_targetCountry.setArmies(l_updatedTargetContArmies);
    }

    private void produceBattleResult(Country p_sourceCountry, Country p_targetCountry, List<Integer> p_attackerArmies,
                                     List<Integer> p_defenderArmies, Player p_playerOfTargetCountry) {
        int l_attackerArmiesLeft = d_numberOfArmiesToPlace > p_targetCountry.getArmies() ?
                d_numberOfArmiesToPlace - p_targetCountry.getArmies() : 0;
        int l_defenderArmiesLeft = d_numberOfArmiesToPlace < p_targetCountry.getArmies() ?
                p_targetCountry.getArmies() - d_numberOfArmiesToPlace : 0;

        for (int l_i = 0; l_i < p_attackerArmies.size(); l_i++) {
            if (p_attackerArmies.get(l_i) > p_defenderArmies.get(l_i)) {
                l_attackerArmiesLeft++;
            } else {
                l_defenderArmiesLeft++;
            }
        }

        handleSurvivingArmies(l_attackerArmiesLeft, l_defenderArmiesLeft, p_sourceCountry, p_targetCountry, p_playerOfTargetCountry);
    }

    public void handleSurvivingArmies(int p_attackerArmiesLeft, int p_defenderArmiesLeft, Country p_sourceCountry,
                                      Country p_targetCountry, Player p_playerOfTargetCountry) {
        if (p_defenderArmiesLeft == 0) {
            p_playerOfTargetCountry.removeCountry(p_targetCountry);
            p_targetCountry.setArmies(p_attackerArmiesLeft);
            d_playerInitiator.addCountry(p_targetCountry);

            setD_orderExecutionLog("Player : " + d_playerInitiator.getPlayerName() + " is assigned with Country : " +
                    p_targetCountry.getCountryName() + " and armies : " + p_targetCountry.getArmies(), "default");

            d_playerInitiator.assignCard();
        } else {
            p_targetCountry.setArmies(p_defenderArmiesLeft);

            int l_sourceArmiesToUpdate = p_sourceCountry.getArmies() + p_attackerArmiesLeft;
            p_sourceCountry.setArmies(l_sourceArmiesToUpdate);

            String l_country1 = "Country : " + p_targetCountry.getCountryName() + " is left with " +
                    p_targetCountry.getArmies() + " armies and is still owned by player : " +
                    p_playerOfTargetCountry.getPlayerName();
            String l_country2 = "Country : " + p_sourceCountry.getCountryName() + " is left with " +
                    p_sourceCountry.getArmies() + " armies and is still owned by player : " +
                    d_playerInitiator.getPlayerName();

            setD_orderExecutionLog(l_country1 + System.lineSeparator() + l_country2, "default");
        }
    }

    @Override
    public boolean valid(GameState p_gameState) {
        Country l_country = d_playerInitiator.getCoutriesOwned().stream()
                .filter(l_pl -> l_pl.getCountryName().equalsIgnoreCase(d_sourceCountryName.toString()))
                .findFirst().orElse(null);
        if (l_country == null) {
            setD_orderExecutionLog(currentOrder() + " is not executed since Source country : " + d_sourceCountryName);
        }
        return true;
    }
}
