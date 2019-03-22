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

    public boolean checkTradePossible(List<Card> selectedCards) {
        boolean returnFlag = false;
        if (selectedCards.size() == 3) {
            int infantry = 0, cavalry = 0, artillery = 0;
            for (Card card : selectedCards) {
                if (card.getCardType().toString().equals(CardTypes.INFANTRY.toString())) {
                    infantry++;
                } else if (card.getCardType().toString().equals(CardTypes.CAVALRY.toString())) {
                    cavalry++;
                } else if (card.getCardType().toString().equals(CardTypes.ARTILLERY.toString())) {
                    artillery++;
                }
            }
            if ((infantry == 1 && cavalry == 1 && artillery == 1) || infantry == 3 || cavalry == 3 || artillery == 3) {
                returnFlag = true;
            }
        }
        return returnFlag;
    }

}
