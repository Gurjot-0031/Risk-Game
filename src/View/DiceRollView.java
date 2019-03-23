package View;

import Controller.GameController;
import Model.Game;
import Event.GameEvents;
import Model.Territory;
//Test goung on
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DiceRollView implements Observer {

    private static DiceRollView instance;
    String fromPhaseViewActionListener;

    public String getFromPhaseViewActionListener() {
        return fromPhaseViewActionListener;
    }

    public void setFromPhaseViewActionListener(String fromPhaseViewActionListener) {
        this.fromPhaseViewActionListener = fromPhaseViewActionListener;
    }



    public JFrame getDiceRollFrame() {
        return diceRollFrame;
    }

    public JLabel getDiceInfoLabel() {
        return diceInfoLabel;
    }

    private JFrame diceRollFrame;

    private DiceRollView() {
    }

    private JLabel diceInfoLabel;

    public static DiceRollView getInstance() {
        if (instance == null) {
            instance = new DiceRollView();
        }
        return instance;
    }

    /**
     * Load the Frame
     */
    public void loadFrame() {
        if (diceRollFrame == null) {
            initDiceRollFrame();
        }
        diceRollFrame.setVisible(true);
        Game.getInstance().addObserver(this);
        //Game.getInstance().attackerObj.addObserver(this);
    }

    public void initDiceRollFrame() {
        diceRollFrame = new JFrame("Dice Roll");
        diceRollFrame.setSize(800, 600);
        diceRollFrame.setSize(800, 600);
        diceRollFrame.setResizable(false);
        diceRollFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        diceRollFrame.getContentPane().setBackground(Color.WHITE);
        diceRollFrame.getContentPane().setLayout(null);

        JPanel dicePanel = new JPanel();
        dicePanel.setBounds(0, 20, 800, 300);

        diceInfoLabel = new JLabel();
        diceInfoLabel.setBounds(0, 20, 800, 300);

        //diceInfoLabel.setText("<html>Attacker:"+ Game.getInstance().getAttacker()+" Armies left: "+
        //      Game.getInstance().getAttackerObj().getArmies()+"<br/>Attacked:"+Game.getInstance().getAttacked()
        //    +" Armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/></html>");

        diceInfoLabel.setVisible(true);

        dicePanel.add(diceInfoLabel);
        diceRollFrame.add(dicePanel);

        JButton diceRollBtn = new JButton("Roll Dices");
        diceRollBtn.setBounds(380, 20, 20, 30);
        diceRollBtn.setVisible(true);
        dicePanel.add(diceRollBtn);

        diceRollBtn.addActionListener(new Action() {
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
                diceRollBtn.setVisible(false);
                GameEvents evntObj = new GameEvents();
                evntObj.setEventData(fromPhaseViewActionListener);
                evntObj.setEventInfo("Roll Dices Event");
                //PhaseView.getInstance().territoryActionListener
                GameController.getInstance().eventTriggered(evntObj);
            }


        });
    }


    @Override
    public void update(Observable observable, Object o) {
        //if(o instanceof Player || o instanceof Game){
        //  diceInfoLabel.setText("<html>Attacker:"+ Game.getInstance().getAttacker()+" Armies left: "+
        //        Game.getInstance().getAttackerObj().getArmies()+"<br/>Attacked:"+Game.getInstance().getAttacked()
        //      +" Armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/></html>");
        //}

    }

    public void displayContent(ArrayList<String> runTimeMessagesFromAttack){

        String uiOutput="<html><head><h2>Attacker: "+ Game.getInstance().getAttacker()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Defender: "+Game.getInstance().getAttacked()+"</h2></head><body>";

        for(String s:runTimeMessagesFromAttack){
            uiOutput+=s;
        }
        diceInfoLabel.setVisible(true);
        diceInfoLabel.setText(uiOutput+"</body></html>");


    }
}



