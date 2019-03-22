package Model;

import java.util.ArrayList;

/**
 * @author Team 38(Ufuoma Ubor)
 */
public class Card {

    private String cardName;
    private String cardType;

    /**
     * @param name of String data type
     * @param type of String data type
     */
    public Card(String name, String type) {

        this.cardType = type;
        this.cardName = name;
    }

    public static ArrayList<Card> generateCards() {
        ArrayList<Card> cards = new ArrayList<Card>();
        int i = 0;
        while (i < 44) {
            cards.add(new Card("Artillery", "Normal"));
            i++;
        }
        return cards;
    }

    /**
     * @return cardName of type String
     */
    public String getCardName() {

        return cardName;
    }

    /**
     * @return cardType of type String
     */
    public String getCardType() {

        return cardType;
    }

}
