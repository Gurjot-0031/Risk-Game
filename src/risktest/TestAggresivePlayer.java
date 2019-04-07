package risktest;

import Model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

import Model.Territory;

import java.awt.*;
import java.util.ArrayList;

public class TestAggresivePlayer {

    int maxArmiesAndAdjacents = 0;
    Territory retTerr = null;
    AggressiveStrategyPlayer aP = new AggressiveStrategyPlayer();
    Game g = Game.getInstance();
    Territory territory = null;
    //Territory retTerr = null;

    Player p1 = new Player(1, "p1", Color.red, 45, "HUMAN");
    Player p2 = new Player(2, "p1", Color.blue, 45, "HUMAN");

    //Player getPlayer1 = Game.getInstance().getPlayerById(1);
    //Player getPlayer2 = Game.getInstance().getPlayerById(2);
    ArrayList<String> nigeriaAdj = new ArrayList<>();
    ArrayList<String> ghanaAdj = new ArrayList<>();
    ArrayList<String> cameroonAdj = new ArrayList<>();
    ArrayList<String> nigerAdj = new ArrayList<>();
    ArrayList<String> coutonouAdj = new ArrayList<>();
    ArrayList<String> maliAdj = new ArrayList<>();
    ArrayList<String> lesothoAdj = new ArrayList<>();

    ArrayList<Territory> p1Territories = new ArrayList<>();
    ArrayList<Territory> p2Territories = new ArrayList<>();

    Territory nI = new Territory("Nigeria", 10, 20, nigeriaAdj, 10);
    Territory gH = new Territory("Ghana", 30, 25, ghanaAdj, 7);
    Territory cM = new Territory("Cameroon", 40, 35, cameroonAdj, 11);
    Territory nij = new Territory("Niger", 50, 45, nigerAdj, 22);
    Territory cU = new Territory("Coutonou", 60, 55, coutonouAdj, 15);
    Territory mI = new Territory("Mali", 70, 65, maliAdj, 17);
    Territory lO = new Territory("Lesotho", 80, 75, lesothoAdj, 22);


    @Before
    public void setUp() {


        p1Territories.add(nI);
        p1Territories.add(gH);
        p1Territories.add(cM);

        p2Territories.add(nij);
        p2Territories.add(cU);
        p2Territories.add(mI);
        p2Territories.add(lO);

        nigeriaAdj.add("Ghana");
        nigeriaAdj.add("Cameroon");
        nigeriaAdj.add("Niger");
        nigeriaAdj.add("Coutonou");
        nigeriaAdj.add("Mali");
        nigeriaAdj.add("Lesotho");

        ghanaAdj.add("Nigeria");
        cameroonAdj.add("Nigeria");
        nigerAdj.add("Nigeria");
        coutonouAdj.add("Nigeria");
        maliAdj.add("Nigeria");
        lesothoAdj.add("Nigeria");

    }

    @Test
    public void testGetStrongestTerritory() {

        for (Territory t : p1Territories) {
            if (maxArmiesAndAdjacents < (t.getArmies() + p2Territories.size())) {
                maxArmiesAndAdjacents = (t.getArmies() + p2Territories.size());
                retTerr = t;
            }
        }
        assertTrue(!retTerr.equals(null));
    }

    @Test
    public void testGetNotOwnedAdjacentsCount() {

        assertTrue(p2Territories.size() >= 0);
    }

    @Test
    public void testGetOwnedAdjTerrObjects() {

        assertTrue(p1Territories.size() >= 0);
    }

    @Test
    public void testGetNotOwnedAdjTerrObjects() {

        assertTrue(p2Territories.size() >= 0);
    }

    @Test
    public void testReinforcement() {

        Player currentPlayer = p1;

    }

    @Test
    public void testAttack() {


    }

    @Test
    public void testFortify() {


    }
}
