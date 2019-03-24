package Model;

import javafx.collections.ObservableArrayBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.List;


/**
 * @author Team 38(Ufuoma Ubor)
 */
public class Card extends Observable implements Serializable {

    private String cardType;

    public Card() {

    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType() {
        ArrayList<String> types = new ArrayList<>();
        types.add("INFANTRY");
        types.add("CAVALRY");
        types.add("ARTILLERY");

        Collections.shuffle(types);
        this.cardType = types.get(0);
    }


}

