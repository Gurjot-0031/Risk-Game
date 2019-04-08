package risktest;

import Model.Card;

import Model.Game;
import Model.Player;
import Model.Territory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.*;
import java.util.ArrayList;

public class TestPlayer {

    ArrayList<String> cards = new ArrayList<>();
    Player pObject = new Player(1,"Jai", Color.RED,5,"AGGRESSIVE");
    //Territory chn= new Territory("India",10,20,new ArrayList<>());
    //ArrayList<Territory> adj = new ArrayList<>();
    //adj.add(chn[3]);

    Territory ind = new Territory("India",10,20,new ArrayList<>());

    Card cardObject = new Card();


    ArrayList<Integer> diceValue = new ArrayList<Integer>();
    ArrayList<Integer> output = new ArrayList<Integer>();

    /**
     * Function called before each test case
     */
    @Before
    public void setUp() throws Exception {

        Game.getInstance().setAttackerObj(ind);
        diceValue.add(6);
        diceValue.add(5);
        diceValue.add(4);
        diceValue.add(3);
        diceValue.add(2);
        diceValue.add(1);

        cards.add("ARTILLERY");
        cards.add("ARTILLERY");
        cards.add("ARTILLERY");
        cards.add("INFANTRY");
        cards.add("INFANTRY");
        cards.add("INFANTRY");
        cards.add("CALVARY");
        cards.add("CALVARY");
        cards.add("CALVARY");

    }

    /**
     * Testing method hasArtilleryCard
     */
    @Test
    public void testHasArtilleryCard() {
        System.out.println("Test Case testHasArtilleryCard");
        for (String c : cards) {
            if (c.equalsIgnoreCase(cards.get(0))) {
                assertFalse(c.equalsIgnoreCase(cards.get(3)));
            }
        }
    }

    /**
     * Testing method hascalvaryCard
     */
    @Test
    public void testHasCalvaryCard() {
        System.out.println("Test Case testHasCalvaryCard");
        for (String c : cards) {
            if (c.equalsIgnoreCase(cards.get(2))) {
                assertTrue(c.equalsIgnoreCase(cards.get(2)));
            }
        }
    }

    /**
     * Testing method hasInfantryCard
     */
    @Test
    public void testHasInfantryCard() {
        System.out.println("Test Case testHasInfantryCard");
        for (String c : cards) {
            if (c.equalsIgnoreCase(cards.get(1))) {
                assertTrue(c.equalsIgnoreCase(cards.get(1)));
            }
        }
    }

    /**
     * Testing method hasThreeInfantryCard
     */
    @Test
    public void testHasThreeInfantryCard() {
        System.out.println("Test Case testHasThreeInfantryCard");
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {

            if (cards.get(i).equalsIgnoreCase(cards.get(3))) {
                count++;
            }
        }
        assertEquals(count, 3);
    }

    /**
     * Testing method hasThreeCalvaryCard
     */
    @Test
    public void testHasThreeCalvaryCard() {
        System.out.println("Test Case testHasThreeCalvaryCard");
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {

            if (cards.get(i).equalsIgnoreCase(cards.get(6))) {
                count++;
            }
        }
        assertEquals(count, 3);
    }

    /**
     * Testing method hasThreeArtilleryCard
     */
    @Test
    public void testHasThreeArtilleryCard() {
        System.out.println("Test Case testHasThreeArtilleryCard");
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {

            if (cards.get(i).equalsIgnoreCase(cards.get(0))) {
                count++;
            }
        }
        assertEquals(count, 3);
    }

    /**
     * Testing method rollDice
     */
    @Test
    public void testRollDice() {
        System.out.println("Test Case testRollDice");
        int numberOfDie = 3;
        assertEquals(numberOfDie, pObject.rollDice(numberOfDie).size());
    }

    /**
     * Testing method isThereAWinner
     */
    @Test
    public void testIsThereAWinner() {
        System.out.println("testIsThereAWinner");
        assertFalse(pObject.isThereAWinner());
    }

    /**
     * Testing method isCardExchangePossible
     */
    @Test
    public void testIsCardExchangePossible() {
        System.out.println("Test Case testIsCardExchangePossible");
        //assertTrue(pObject.isCardExchangePossible(cards));

        assertTrue(pObject.isCardExchangePossible());
    }

}
