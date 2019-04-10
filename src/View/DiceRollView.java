package View;

import Controller.GameController;
import Model.Game;
import Event.GameEvents;
import Model.Player;
import Model.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DiceRollView implements Observer, Serializable {

    public static DiceRollView instance;

    /**
     * Get the fortify button
     */
    public JButton getFortifyBtn() {
        return fortifyBtn;
    }

    private JButton fortifyBtn = new JButton("Fortify");
    /**
     * Get the button for dice roll
     * @return
     */

    /**
     * Creating the dice roll button
     */

    /**
     * Get the button for continuing attack
     * @return
     */
    public JButton getContinueAttackBtn() {
        return continueAttackBtn;
    }

    /**
     * Creating the continue attack button
     */
    private JButton continueAttackBtn = new JButton("Continue Attack?");
    String fromPhaseViewActionListener;

    /**
     * getter for phase view actions
     * @return
     */
    public String getFromPhaseViewActionListener() {
        return fromPhaseViewActionListener;
    }

    /**
     * setter for phaseview actions
     * @param fromPhaseViewActionListener
     */
    public void setFromPhaseViewActionListener(String fromPhaseViewActionListener) {
        this.fromPhaseViewActionListener = fromPhaseViewActionListener;
    }

    /**
     * get the dice roll frame
     * @return
     */
    public JFrame getDiceRollFrame() {
        return diceRollFrame;
    }

    /**
     * get the dice info label
     * @return
     */
    public JLabel getDiceInfoLabel() {
        return diceInfoLabel;
    }

    private JFrame diceRollFrame;

    private DiceRollView() {
    }

    private JLabel diceInfoLabel;

    /**
     *
     * Get the singleton instance of the dice roll view
     * @return the singleton instance
     */
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
        diceInfoLabel.setVisible(true);
        if(Game.getInstance().isAlloutMode()) {
            continueAttackBtn.setVisible(false);
        }
        else {
            continueAttackBtn.setVisible(true);
        }

        diceRollFrame.setVisible(true);
        Game.getInstance().addObserver(this);
        //Game.getInstance().attackerObj.addObserver(this);
    }

    /**
     * intialize the dice roll frame and its contents
     */
    public void initDiceRollFrame() {
        diceRollFrame = new JFrame("Dice Roll");
        diceRollFrame.setSize(800, 600);
        diceRollFrame.setSize(800, 600);
        diceRollFrame.setResizable(false);
        diceRollFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        diceRollFrame.getContentPane().setBackground(Color.WHITE);
        diceRollFrame.getContentPane().setLayout(null);

        diceInfoLabel = new JLabel();
        diceInfoLabel.setBounds(0, 20, 800, 300);

        JScrollPane textPane = new JScrollPane(diceInfoLabel);
        textPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setBounds(0, 0, 800, 500);

        JPanel dicePanel2 = new JPanel();
        dicePanel2.setBounds(0, 500, 800, 100);



        //diceInfoLabel.setText("<html>Attacker:"+ Game.getInstance().getAttacker()+" Armies left: "+
        //      Game.getInstance().getAttackerObj().getArmies()+"<br/>Attacked:"+Game.getInstance().getAttacked()
        //    +" Armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/></html>");

        diceInfoLabel.setVisible(true);




        continueAttackBtn.setBounds(380, 20, 20, 30);

        dicePanel2.add(continueAttackBtn);
        dicePanel2.setVisible(true);


        //Fortrification
        JButton fortifyBtn = new JButton("Fortify");
        fortifyBtn.setBounds(420, 20, 20, 30);

        dicePanel2.add(fortifyBtn);
        dicePanel2.setVisible(true);


        //scrollPaneForPanel1.add(textPane);
        diceRollFrame.getContentPane().add(textPane);
        diceRollFrame.add(textPane);
        diceRollFrame.add(dicePanel2);
        fortifyBtn.setVisible(true);
        //for(Con)
        if(Game.getInstance().getAttackerObj().getArmies()>=2){
            continueAttackBtn.setVisible(true);
        }
        else{
            continueAttackBtn.setVisible(false);
            JOptionPane.showMessageDialog(null,"<html>Attacker don't have sufficient armies to continue attack..<br/>Attacker can continue with fortification</html>");

        }


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

            /**
             * Actions to be performed on click of continue attack button
             * @param actionEvent object of action event
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

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

            /**
             * Actions to be performed on click of fortify button on dice roll view
             * @param actionEvent object of action event
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                diceRollFrame.setVisible(false);
                Game.getInstance().nextPhase();
                if(Game.getInstance().getCurrPlayer().getPlayerType().equalsIgnoreCase("HUMAN"))
                    Game.getInstance().getCurrPlayer().fortify(fromPhaseViewActionListener);
                else
                    Game.getInstance().getCurrPlayer().getStrategy().fortify("ABCD");
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

    /**
     * Display content for the results after dice roll
     * @param runTimeMessagesFromAttack arraylist of messages due to attack execution
     */
    public void displayContent (ArrayList < String > runTimeMessagesFromAttack) {

        /*String uiOutput="<html><head><h2>Attacker: "+ Game.getInstance().getAttackerObj().getName()+
                "("+Game.getInstance().getAttackerObj().getOwner().getPlayerType()+")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "Defender: "+Game.getInstance().getAttackedObj().getName()+
                "("+Game.getInstance().getAttackedObj().getOwner().getPlayerType()+")</h2></head><body>";*/

        String uiOutput="<html><head><h2>Attacker: "+ "Attacker" +
                "("+"Armies"+")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "Defender: "+"Defender "+
                "("+"Armies"+")</h2></head><body>";

        for(String s:runTimeMessagesFromAttack){
            uiOutput+=s;
        }
        if(!Game.getInstance().getCurrPlayer().getPlayerType().equalsIgnoreCase("HUMAN")) {
            diceInfoLabel.setVisible(true);
            diceInfoLabel.setText(uiOutput + "</body></html>");
        }


    }
}