package risktest;

import Model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class checkStartupPhase {
    String invalidcheckPhase;

    /**
     * Function called before each test case
     *
     * @throws Exception
     *             Any exceptions caused
     */
    @Before
    public void setUp() throws Exception {
        invalidcheckPhase = "Game Phase: Setup";
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
     * Tests the valid phase
     */
    @Test
    public void testValidPhase(){
        System.out.println("Test case testValidPhase");
        try{
            int gamePhase = 1;
            String s = null;
            Game.getInstance().setGamePhase(1);
            s = Game.getInstance().getGamePhaseDesc();
            assertEquals("Test passed",invalidcheckPhase,s);
            System.out.println("Phase Match successful");

        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
}
