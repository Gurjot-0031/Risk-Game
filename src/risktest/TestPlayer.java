package risktest;

import Model.Card;

import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestPlayer {

    ArrayList<String> cards = new ArrayList<>();
    Player pObject = new Player();
    Card cardObject = new Card();
    ArrayList<Integer> diceValue = new ArrayList<Integer>();
    ArrayList<Integer> output = new ArrayList<Integer>();

    /**
     * Function called before each test case
     */
    @Before
    public void setUp() {


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

        for (String c : cards) {
            if (c.equalsIgnoreCase(cards.get(0))) {
                assertTrue(c.equalsIgnoreCase(cards.get(0)));
            }
        }
    }

    /**
     * Testing method hascalvaryCard
     */
    @Test
    public void testHasCalvaryCard() {

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
        int numberOfDie = 3;
        assertEquals(numberOfDie, pObject.rollDice(numberOfDie).size());
    }

    /**
     * Testing method isThereAWinner
     */
    @Test
    public void testIsThereAWinner() {
        assertFalse(pObject.isThereAWinner());
    }

    /**
     * Testing method isCardExchangePossible
     */
    @Test
    public void testIsCardExchangePossible() {

        assertTrue(pObject.isCardExchangePossible(cards));
    }

}
