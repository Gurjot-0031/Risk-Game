package View;

import Model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 */
public class CardExchangeView implements Observer {

    private static CardExchangeView instance;
    JLabel CardExchangeViewLabel = new JLabel();
    ArrayList<Territory> territoriesList;
    ArrayList<Territory> continentTerritory;
    private JFrame homeFrame;

    private CardExchangeView() {

    }

    public static CardExchangeView getInstance() {
        if (instance == null) {
            instance = new CardExchangeView();
        }
        return instance;
    }

    public void initCardExchangeView() {

        homeFrame = new JFrame("CARD");
        homeFrame.setSize(400, 400);
        //homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.getContentPane().setBackground(Color.PINK);
        homeFrame.setVisible(true);

    }

    @Override
    public void update(Observable o, Object arg) {


    }
}
