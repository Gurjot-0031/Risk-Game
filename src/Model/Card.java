package Model;

import javafx.collections.ObservableArrayBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.List;


/**
 * @author Team 38(Ufuoma Ubor)
 */
public class Card extends Observable implements Serializable {

    //private String cardName;
    //private String cardType;

    private CardTypes cardType;
    private List<Card> cardsToExchange;
    /**
     *
     * @param cardType a type of card
     */
    public Card(CardTypes cardType) {
        this.cardType = cardType;
    }

    public CardTypes getCardType() {

        return cardType;
    }

    public boolean equals(Object o) {

        if (!(o instanceof Card)) {

            return false;
        } else if (o == this) {

            return true;
        } else {

            Card card = (Card) o;

            return card.getCardType().toString().equalsIgnoreCase(cardType.toString());
        }
    }

    /**
     * @return list of card to exchange
     */
    public List<Card> getCardsToExchange() {
        return cardsToExchange;
    }

    /**
     * @param cardsToBeExchanged of type Card
     */
    public void setCardsToBeExchanged(List<Card> cardsToBeExchanged) {
        this.cardsToExchange = cardsToBeExchanged;
    }

    public enum CardTypes {

        INFANTRY, CAVALRY, ARTILLERY
    }



}
