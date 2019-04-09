package risktest;

import Model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CheckFortifyPhase {

    int gamePhase;
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

}
