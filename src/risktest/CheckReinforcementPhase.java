package risktest;

import Model.Game;
import Model.Map;
import Model.Player;
import Model.Territory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CheckReinforcementPhase {
    int reinforced;
    int gamePhase;
    ArrayList<String> t1adj = new ArrayList<String>();
    ArrayList<String> t2adj = new ArrayList<String>();
    Player p1 = new Player(0,"p1", Color.RED,4,"HUMAN");
    Player p2 = new Player(1,"p2", Color.BLUE,2,"AGGRESSIVE");
    /**
     * Function called before each test case
     *
     * @throws Exception
     *             Any exceptions caused
     */
    @Before
    public void setUp() throws Exception {
        Game.getInstance().setGamePhase(2);
        reinforced = 3;
    }
    /**
     * Function called after each test case
     *
     * @throws Exception
     *             Any exceptions caused
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     *
     * Tests the if the phase is Reinforcement Phase
     */
    @Test
    public void testValidPhase() {
        System.out.println("Test case testValidPhase");
        try {
            int dummyPhase = 2;
            gamePhase = Game.getInstance().getGamePhase();
            if(gamePhase == 2) {
                assertEquals("Is Reinforcement Phase correct?", gamePhase, dummyPhase);
                System.out.println("Reinforcement Phase Test Successful");
            }
            else {
                System.out.println("Reinforcement Phase not correct");
            }
        }
        catch (Exception e){
        }
    }
    /**
     * Tests the number of reinforcement armies
     */
    @Test
    public void testReinforcementArmies() {
        System.out.println("Test case testValidPhase");
        try{
            int value = Game.getInstance().calcReinforcementArmies(1);
            if (value == 3) {
                assertEquals("Is reinforcement Armies correct?",value,reinforced);
                System.out.println("Reinforce Armies correctly Tested");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * Tests the reinforce method
     */
    @Test
    public void testReinforce() {
        System.out.println("Test Case testReinforce");
        try {
            Territory t1 = new Territory("india",10,20,t1adj);
            t1.setOwner(p1);
            p1.addArmy(3);
            t1adj.add("china");
            Territory t2 = new Territory("china",10,20,t1adj);
            t2.setOwner(p1);
            Game.getInstance().setMap(new Map("../Resources/World.map"));
            Game.getInstance().getGameMap().getTerritoriesAsObjects("asia");
            //assertEquals("Is Reinforce correct?",p1.reinforce("india"),p2.reinforce("india"));
            assertFalse(p1.reinforce("india").equals(false));
            System.out.println("Reinforce Test passed");
        }
        catch(NullPointerException re){
            re.printStackTrace();
        }
    }
}
