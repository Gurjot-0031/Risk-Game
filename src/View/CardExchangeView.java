package View;

import Controller.GameController;
import Model.*;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Card;

/**
 * @author Team 38(Ufuoma Ubor)
 * CardExchangeView class, implements an Observer class
 */
public class CardExchangeView implements Observer {

    private static CardExchangeView instance;
    private JLabel cardExchangeViewLabel = new JLabel();
    ArrayList<Territory> territoriesList;
    ArrayList<Territory> continentTerritory;
    private JFrame cardViewFrame;

    /**
     * class constructor
     */
    private CardExchangeView() {

    }

    /**
     * @return an instance of the class because it's a Singleton class
     */
    public static CardExchangeView getInstance() {
        if (instance == null) {
            instance = new CardExchangeView();
        }
        return instance;
    }

    /**
     * displays cards owned by a player for possible exchange
     */
    public void initCardExchangeView() {

        GameController.getInstance().addObserver(CardExchangeView.getInstance());
        cardViewFrame = new JFrame("CARD");
        cardViewFrame.setSize(400, 400);
        //homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //cardViewFrame.getContentPane().setBackground(Color.PINK);
        cardViewFrame.setVisible(true);

        JPanel cardPanel = new JPanel();
        cardPanel.setBounds(0, 0, 400, 400);
        cardPanel.setVisible(true);
        cardViewFrame.add(cardPanel);
        cardExchangeViewLabel.setBounds(0, 0, 400, 400);
        cardExchangeViewLabel.setVisible(true);
        cardPanel.add(cardExchangeViewLabel);


    }

    /**
     *
     * @param o an object of the Observable class
     * @param arg an object  of Object class
     */
    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof GameController) {
            cardExchangeViewLabel.setText("Cards owned by : " + Game.getInstance().getCurrPlayerName() + " \n "
                    + Game.getInstance().getCurrPlayer().getCardList().get(0).getCardType());
        }
    }
}
