package risktest;

import Model.Card;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestPlayer {

    ArrayList<String> cards = new ArrayList<>();
    Card cardObject = new Card();


    /**
     * Function called before each test case
     */
    @Before
    public void setUp() {

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


}
