package View;

import Controller.GameController;
import Model.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import Model.Card;

/**
 * @author Team 38(Ufuoma Ubor)
 * CardExchangeView class, implements an Observer class
 */
public class CardExchangeView implements Observer {

    public JButton getExcBtn() {
        return excSame;
    }

    private JButton excSame = new JButton("EXCHANGE SIMILAR");
    private JButton excDiff= new JButton("EXCHANGE DIFFERENT");
    private JButton closeBtn = new JButton("CLOSE");
    private static CardExchangeView instance;
    private JLabel cardExchangeViewLabel = new JLabel();
    //ArrayList<Territory> territoriesList;
    //ArrayList<Territory> continentTerritory;
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
    public void loadCardExchangeView() {
        if(cardViewFrame == null) {
            initCardExchangeView();
        }
        cardViewFrame.setVisible(true);
    }
    /**
     * displays cards owned by a player for possible exchange
     */
    public void initCardExchangeView() {

        //GameController.getInstance().addObserver(CardExchangeView.getInstance());
        cardViewFrame = new JFrame("CARD EXCHANGE");
        cardViewFrame.setSize(600, 600);
        cardViewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //cardViewFrame.getContentPane().setBackground(Color.PINK);
        cardViewFrame.setVisible(true);

        JPanel cardPanel = new JPanel();
        JPanel cardPanel2 = new JPanel();
        JPanel cardPanel3 = new JPanel();
        cardPanel.setBounds(0, 0, 600, 200);
        //cardPanel.set
        cardPanel3.setBounds(0, 200, 600, 200);
        cardPanel2.setBounds(0, 400, 600, 200);

        cardPanel.setBackground(Color.lightGray);
        cardPanel.setVisible(true);
        cardPanel2.setBackground(Color.gray);
        cardPanel2.setVisible(true);
        cardPanel3.setBackground(Color.PINK);
        cardPanel3.setVisible(true);

        cardExchangeViewLabel.setBounds(0, 0, 600, 200);
        cardExchangeViewLabel.setVisible(true);
        cardPanel.add(cardExchangeViewLabel);
        cardPanel2.add(excSame);
        cardPanel2.add(excDiff);
        cardPanel2.add(closeBtn);
        excSame.setVisible(true);
        closeBtn.setVisible(true);
        excDiff.setVisible(true);

        cardViewFrame.add(cardPanel);
        cardViewFrame.add(cardPanel2);
        cardViewFrame.add(cardPanel3);


        if(Game.getInstance().getCurrPlayer().getCardList().size()<5)
            closeBtn.setVisible(false);
        String msg="<html><head><h1>Cards owned by : " + Game.getInstance().getCurrPlayerName() + "</h1></head><br/><br/>";

        msg+="<body>Total :"+Game.getInstance().getCurrPlayer().getCardList().size()+" cards</body></html>";
        cardExchangeViewLabel.setText(msg);
        ArrayList<JButton> cardBtnList= new ArrayList<>();

        int x=0;
        for(Card c:Game.getInstance().getCurrPlayer().getCardList()){
            JButton temp = new JButton(c.getCardType());
            temp.setBackground(c.getCardColor());
            //temp.setEnabled(false);
            temp.setBounds(10+x,10,30,30);
            temp.setVisible(true);
            cardPanel3.add(temp);
            cardBtnList.add(temp);
            x+=40;

        }

        excSame.addActionListener(new Action() {
            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s ="";
                s=JOptionPane.showInputDialog(null,"Enter A:Artillery or C:CAVALRY or I:INFANTRY");

                //while(!s.equalsIgnoreCase("A") || !s.equalsIgnoreCase("C") || !s.equalsIgnoreCase("I"))
                  //  s=JOptionPane.showInputDialog(null,"Enter A:Artillery or C:CAVALRY or I:INFANTRY");
                int removedCount;
                int prevArmies = Game.getInstance().getCurrPlayerArmies();
                int newArmies= Game.getInstance().getCurrPlayerArmies();

                if(s.equalsIgnoreCase("A")){
                    if(Game.getInstance().getCurrPlayer().hasThreeArtillery()){
                        removedCount = 0;
                        for(int i=0;i<Game.getInstance().getCurrPlayer().getCardList().size();i++){
                            if(Game.getInstance().getCurrPlayer().hasArtilleryCard() &&
                                    removedCount<3 && Game.getInstance().getCurrPlayer().getCardList().get(i).getCardType().equalsIgnoreCase("ARTILLERY")) {
                                Game.getInstance().getCurrPlayer().getCardList().remove(i);
                                removedCount+=1;
                            }
                            else if(removedCount>=3) {
                                newArmies +=1;
                                removedCount=0;
                            }

                        }
                        Game.getInstance().getCurrPlayer().addArmy(newArmies-prevArmies);
                        JOptionPane.showMessageDialog(null, "You received "+(newArmies-prevArmies)
                                +"reinforcements in exchange of "+3*(newArmies-prevArmies)+" Artillery cards");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "You do not have 3 artillery cards");

                }
                else if(s.equalsIgnoreCase("C")){
                    if(Game.getInstance().getCurrPlayer().hasThreeCalvary()){
                        removedCount = 0;
                        for(int i=0;i<Game.getInstance().getCurrPlayer().getCardList().size();i++){
                            if(Game.getInstance().getCurrPlayer().hasCalvaryCard() &&
                                    removedCount<3 && Game.getInstance().getCurrPlayer().getCardList().get(i).getCardType().equalsIgnoreCase("CALVARY")) {
                                Game.getInstance().getCurrPlayer().getCardList().remove(i);
                                removedCount+=1;
                            }
                            else if(removedCount>=3) {
                                newArmies +=1;
                                removedCount=0;
                            }
                        }
                        Game.getInstance().getCurrPlayer().addArmy(newArmies-prevArmies);
                        JOptionPane.showMessageDialog(null, "You received "+(newArmies-prevArmies)
                                +"reinforcements in exchange of "+3*(newArmies-prevArmies)+" Cavalry cards");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "You do not have 3 cavalry cards");
                }

                else if(s.equalsIgnoreCase("I")){
                    if(Game.getInstance().getCurrPlayer().hasThreeInfantry()){
                        removedCount = 0;
                        for(int i=0;i<Game.getInstance().getCurrPlayer().getCardList().size();i++){
                            if(Game.getInstance().getCurrPlayer().hasInfantaryCard() &&
                                    removedCount<3 && Game.getInstance().getCurrPlayer().getCardList().get(i).getCardType().equalsIgnoreCase("INFANTRY")) {
                                Game.getInstance().getCurrPlayer().getCardList().remove(i);
                                removedCount+=1;
                            }
                            else if(removedCount>=3) {
                                newArmies +=1;
                                removedCount=0;
                            }

                        }
                        Game.getInstance().getCurrPlayer().addArmy(newArmies-prevArmies);
                        JOptionPane.showMessageDialog(null, "You received "+(newArmies-prevArmies)
                                +"reinforcements in exchange of "+3*(newArmies-prevArmies)+" Infantry cards");
                        closeBtn.setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(null, "You do not have 3 Infantry cards");
                }
            }
        });

        excDiff.addActionListener(new Action() {
            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int prevArmies = Game.getInstance().getCurrPlayerArmies();
                int newArmies= Game.getInstance().getCurrPlayerArmies();

                ArrayList<String> cardsRemoved = new ArrayList<>();
                if(Game.getInstance().getCurrPlayer().hasArtilleryCard() &&
                        Game.getInstance().getCurrPlayer().hasInfantaryCard()
                        && Game.getInstance().getCurrPlayer().hasCalvaryCard()) {

                    while(Game.getInstance().getCurrPlayer().hasArtilleryCard() &&
                            Game.getInstance().getCurrPlayer().hasInfantaryCard()
                            && Game.getInstance().getCurrPlayer().hasCalvaryCard()){
                        int tempIndex=0;
                        while(tempIndex<Game.getInstance().getCurrPlayer().getCardList().size()) {

                            if(!cardsRemoved.contains(Game.getInstance().getCurrPlayer().getCardList().get(tempIndex).getCardType())) {
                                Game.getInstance().getCurrPlayer().getCardList().remove(tempIndex);
                                cardsRemoved.add(Game.getInstance().getCurrPlayer().getCardList().get(tempIndex).getCardType());
                            }
                            tempIndex++;
                        }
                        newArmies += 1;

                    }
                    Game.getInstance().getCurrPlayer().addArmy(newArmies-prevArmies);
                    JOptionPane.showMessageDialog(null, "You received "+(newArmies-prevArmies)
                            +"reinforcements in exchange of "+3*(newArmies-prevArmies)+" Artillery/Cavalry/Infantry cards");
                    closeBtn.setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(null, "You do not have 3 different cards");
                }
        });

        closeBtn.addActionListener(new Action() {
            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardViewFrame.setVisible(false);
            }
        });

    }

    /**
     *
     * @param o an object of the Observable class
     * @param arg an object  of Objact class
     */
    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof GameController) {

        }
    }
}

