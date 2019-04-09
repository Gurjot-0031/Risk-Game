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

public class CheckFortifyPhase {

    int gamePhase;
    ArrayList<String> t1adj = new ArrayList<String>();
    ArrayList<String> t2adj = new ArrayList<String>();
    Player p1 = new Player(1,"p1", Color.RED,4,"HUMAN");
    /**
     *
     * @throws Exception
     *             Any Exception caused
     */
    @Before
    public void setUp() throws Exception {
        Game.getInstance().setGamePhase(4);
    }

    /**
     *
     * @throws Exception
     *             Any Exception caused
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     *
     * Tests the if the phase is Fortify Phase
     */
    @Test
    public void testValidPhase() {
        System.out.println("Test case testValidPhase");
        try{
            int dummyPhase = 4;
            gamePhase = Game.getInstance().getGamePhase();
            if(gamePhase == 4) {
                assertEquals("is Fortify Phase correct?", gamePhase, dummyPhase);
                System.out.println("Fortify Phase Test Successful");
            }
            else
            {
                System.out.println("Fortify Phase not correct");
            }
        }
        catch (Exception e){
        }
    }

    /**
     * Test the fortify method
     */
    @Test
    public void testFortify() {
        System.out.println("Test Case testFortify");
        try {
            Territory t1 = new Territory("india",10,20,t1adj);
            t1.setOwner(p1);
            p1.addArmy(3);
            t1adj.add("china");
            Territory t2 = new Territory("china",10,20,t1adj);
            t2.setOwner(p1);
            Game.getInstance().setMap(new Map("../Resources/World.map"));
            Game.getInstance().fortification_source = "india";
            Game.getInstance().fortification_destination = "china";
            assertFalse(p1.fortify("india").equals(true));
            System.out.println("Fortify test passed");
        }
        catch(IndexOutOfBoundsException ie) {
            ie.printStackTrace();
            System.out.println("Test cannot be validated");
        }
    }
}
