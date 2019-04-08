package risktest;

import Model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CheckReinforcementArmies {
    int reinforced;
    /**
     * Function called before each test case
     *
     * @throws Exception
     *             Any exceptions caused
     */
    @Before
    public void setUp() throws Exception {
        reinforced = 2;
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
     * Tests the number of reinforcement armies
     */
    @Test
    public void testValidPhase(){
        System.out.println("Test case testValidPhase");
        try{
            int value = Game.getInstance().calcReinforcementArmies(1);
            if (value == 0){
                assertEquals("Test passed",value,reinforced);
                System.out.println("Reinforcement Armies not proper");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
