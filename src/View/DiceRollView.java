package View;

import Controller.GameController;
import Model.Game;
import Event.GameEvents;
import Model.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DiceRollView implements Observer {

    private static DiceRollView instance;

    public JButton getDiceRollBtn() {
        return diceRollBtn;
    }

    private JButton diceRollBtn = new JButton("Roll Dices");

    public JButton getContinueAttackBtn() {
        return continueAttackBtn;
    }

    private JButton continueAttackBtn = new JButton("Continue Attack?");
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

        JPanel dicePanel1 = new JPanel();
        dicePanel1.setBounds(0, 0, 800, 300);

        JPanel dicePanel2 = new JPanel();
        dicePanel2.setBounds(0, 300, 800, 300);

        diceInfoLabel = new JLabel();
        diceInfoLabel.setBounds(0, 20, 800, 300);

        //diceInfoLabel.setText("<html>Attacker:"+ Game.getInstance().getAttacker()+" Armies left: "+
        //      Game.getInstance().getAttackerObj().getArmies()+"<br/>Attacked:"+Game.getInstance().getAttacked()
        //    +" Armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/></html>");

        diceInfoLabel.setVisible(true);

        dicePanel1.add(diceInfoLabel);
        diceRollFrame.add(dicePanel1);


        diceRollBtn.setBounds(380, 20, 20, 30);
        diceRollBtn.setVisible(true);
        dicePanel1.add(diceRollBtn);


        continueAttackBtn.setBounds(380, 20, 20, 30);

        dicePanel2.add(continueAttackBtn);
        dicePanel2.setVisible(false);
        diceRollFrame.add(dicePanel2);

        //Fortrification
        JButton fortifyBtn = new JButton("Fortify");
        fortifyBtn.setBounds(420, 20, 20, 30);

        dicePanel2.add(fortifyBtn);
        dicePanel2.setVisible(false);
        diceRollFrame.add(dicePanel2);
        fortifyBtn.setVisible(true);
        //for(Con)
        if (Game.getInstance().getAttackerObj().getArmies() >= 2) {
            continueAttackBtn.setVisible(true);
        } else {
            continueAttackBtn.setVisible(false);
            JOptionPane.showMessageDialog(null, "<html>Attacker don't have sufficient armies to continue attack..<br/>Attacker can continue with fortification</html>");

        }


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
                dicePanel2.setVisible(true);
            }


        });

        continueAttackBtn.addActionListener(new Action() {
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
                int attackerDiceLimit;
                int defenderDiceLimit;
                if (Game.getInstance().getAttackerObj().getArmies() < 3)
                    attackerDiceLimit = Game.getInstance().getAttackerObj().getArmies();
                else
                    attackerDiceLimit = 3;

                if (Game.getInstance().getAttackedObj().getArmies() < 2)
                    defenderDiceLimit = Game.getInstance().getAttackedObj().getArmies();
                else
                    defenderDiceLimit = 2;

                //diceInfoLabel.setText("");
                Game.getInstance().setNumOfDiceAttacker(Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Dices to be thrown (ATTACKER): Min: 1 and not more than" +
                        attackerDiceLimit)));

                while (Game.getInstance().getNumOfDiceAttacker() > 3 || Game.getInstance().getNumOfDiceAttacker() > Game.getInstance().getAttackerObj().getArmies()) {
                    Game.getInstance().setNumOfDiceAttacker(Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Dices to be thrown (ATTACKER): Min: 1 and not more than" + attackerDiceLimit)));
                }

                Game.getInstance().setNumOfDiceAttacked(Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Dices to be thrown (DEFENDER): Min: 1 and not more than" + defenderDiceLimit)));

                while (Game.getInstance().getNumOfDiceAttacked() > 2 || Game.getInstance().getNumOfDiceAttacker() > Game.getInstance().getAttackerObj().getArmies()) {
                    Game.getInstance().setNumOfDiceAttacked(Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Dices to be thrown (DEFENDER): Min: 1 and not more than" + defenderDiceLimit)));
                }

                diceRollBtn.setVisible(true);
                diceInfoLabel.setText("");
                GameEvents evntObj = new GameEvents();
                evntObj.setEventData(fromPhaseViewActionListener);
                evntObj.setEventInfo("Continue Attack");
                //PhaseView.getInstance().territoryActionListener
                GameController.getInstance().eventTriggered(evntObj);

            }
        });
        fortifyBtn.addActionListener(new Action() {
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
                    diceRollFrame.setVisible(false);
                Game.getInstance().nextPhase();
                Game.getInstance().getCurrPlayer().fortify(fromPhaseViewActionListener);
            }
        });
    }



        @Override
        public void update (Observable observable, Object o){
            //if(o instanceof Player || o instanceof Game){
            //  diceInfoLabel.setText("<html>Attacker:"+ Game.getInstance().getAttacker()+" Armies left: "+
            //        Game.getInstance().getAttackerObj().getArmies()+"<br/>Attacked:"+Game.getInstance().getAttacked()
            //      +" Armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/></html>");
            //}

        }

        public void displayContent (ArrayList < String > runTimeMessagesFromAttack) {

            String uiOutput = "<html><head><h2>Attacker: " + Game.getInstance().getAttacker() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "Defender: " + Game.getInstance().getAttacked() + "</h2></head><body>";

            for (String s : runTimeMessagesFromAttack) {
                uiOutput += s;
            }
            diceInfoLabel.setVisible(true);
            diceInfoLabel.setText(uiOutput + "</body></html>");


        }
}
