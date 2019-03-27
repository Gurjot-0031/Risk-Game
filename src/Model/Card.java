package Model;

//import javafx.collections.ObservableArrayBase;

import java.awt.*;
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
    private Color cardColor;

    public Color getCardColor() {
        return cardColor;
    }

    public void setCardColor() {
        if(this.getCardType()=="INFANTRY")
            this.cardColor = new Color(255,0,0);
        else if(this.getCardType()=="CAVALRY")
            this.cardColor = new Color(0,255,0);
        else
            this.cardColor = new Color(255,255,0);


    }

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

