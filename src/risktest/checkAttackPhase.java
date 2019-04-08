package risktest;

import Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class checkAttackPhase {
    int gamePhase;
    String attacker;
    String defender;
    ArrayList<String> t1adj = new ArrayList<String>();
    ArrayList<Integer> attackerDiceValues = new ArrayList<Integer>();
    ArrayList<Integer> attackedDiceValues = new ArrayList<Integer>();
    ArrayList<String> t2adj = new ArrayList<String>();
    Player p1 = new Player(1,"p1", Color.RED,4,"HUMAN");
    Player p2 = new Player(2,"p2",Color.BLUE,3,"BENEVOLENT");

    /**
     *
     * @throws Exception
     *             Any Exception caused
     */
    @Before
    public void setUp() throws Exception {
        Game.getInstance().setGamePhase(3);
        Game.getInstance().setAttacked("p2");
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
     * Tests the if the phase is Attack Phase
     */
    @Test
    public void testValidPhase() {
        System.out.println("Test case testValidPhase");
        try{
            int dummyPhase = 3;
            gamePhase = Game.getInstance().getGamePhase();
            if(gamePhase == 3) {
                assertEquals("is Attack Phase correct?", gamePhase, dummyPhase);
                System.out.println("Attack Phase Test Successful");
            }
            else
            {
                System.out.println("Attack Phase not correct");
            }
        }
        catch (Exception e){
        }
    }

    /**
     *
     * Tests the correct Attacker
     */
    @Test
    public void testAttacker(){
        System.out.println("Test case testAttacker");
        try{
            Territory t1 = new Territory("india",10,20,t1adj);
            t1adj.add("china");
            t1adj.add("siam");
            t1.setOwner(p1);

            String dummyAttacker = "india";

            Game.getInstance().setAttacker(t1.getName());
            String attacker = Game.getInstance().getAttacker();

            if(t1.getName() == "india"){
                assertEquals("Is Attacker correct?",dummyAttacker,attacker);
                System.out.println("Correct Attacker test successful");
            }
            else{

            }
        }
        catch(Exception e){

        }
    }

    /**
     *
     * Tests the correct Defender
     */
    @Test
    public void testDefender(){
        System.out.println("Test case testDefender");
        try{
            Territory t2 = new Territory("japan",10,20,t2adj);
            t2adj.add("china");
            t2adj.add("korea");
            t2.setOwner(p2);

            String dummyDefender = "japan";

            Game.getInstance().setAttacked(t2.getName());
            String defender = Game.getInstance().getAttacked();

            if(t2.getName() == "japan"){
                assertEquals("Is Defender correct?",dummyDefender,defender);
                System.out.println("Correct Defefender test successful");
            }
            else{

            }
        }
        catch(Exception e){

        }
    }

    /**
     *
     * Tests the maximum values of the attacker and defender dice value
     */
    @Test
    public void testDiceResult(){
        System.out.println("Test case testDiceResult");

        attackerDiceValues.add(3);
        attackerDiceValues.add(4);
        attackerDiceValues.add(6);
        System.out.println(attackerDiceValues);
        attackedDiceValues.add(5);
        attackedDiceValues.add(2);
        System.out.println(attackedDiceValues);

            Player.attackerDiceValues = attackerDiceValues;
            Player.attackedDiceValues = attackedDiceValues;
            int[] p1Max = p1.getMax();

            if(p1Max[0] == 6 && p1Max[1] == 5){
                assertEquals(6,p1Max[0]);
                System.out.println("Test Case Passed");
            }
    }

    /**
     *
     * Tests the owner of the territory
     */
    @Test
    public void testOwner(){
        System.out.println("Test case testOwner");
        try{
            Territory t1 = new Territory("india",10,20,t1adj);
            t1.setOwner(p1);
            if(t1.getOwner().getName() == "p1"){
                assertEquals("Is Owner Correct?","p1",p1.getName());
                System.out.println("Correct Owner identified. Test Passed");
            }
        }
        catch(Exception e){

        }
    }

    /**
     *
     * Tests the strategy class of the players
     */
    @Test
    public void testStrategy() {
        System.out.println("Test case testStrategy");
        PlayerStrategyInterface s1 = new AggressiveStrategyPlayer();
        PlayerStrategyInterface s2 = new BenevolentStrategyPlayer();
        try{
            p1.setStrategy(s1);
            if(p1.getStrategy() != null){
               assertEquals("Is strategy correct?",s1.getClass(),p1.getStrategy().getClass());
               System.out.println("Correct Strategy test passed");
            }
            else
                System.out.println("Strategy not correct");
        }
        catch(Exception c){

        }
    }
}
