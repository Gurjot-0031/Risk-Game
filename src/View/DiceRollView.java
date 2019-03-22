package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class DiceRollView implements Observer {

    private static DiceRollView instance;

    public JFrame getDiceRollFrame() {
        return diceRollFrame;
    }

    public JLabel getDiceInfoLabel() {
        return diceInfoLabel;
    }

    private JFrame diceRollFrame;
    private DiceRollView() {}

    private JLabel diceInfoLabel ;

    public static DiceRollView getInstance() {
        if(instance == null) {
            instance = new DiceRollView();
        }
        return instance;
    }

    /**
     * Load the Frame
     */
    public void loadFrame() {
        if(diceRollFrame == null) {
            initDiceRollFrame();
        }
        diceRollFrame.setVisible(true);
        Game.getInstance().addObserver(this);
        //Game.getInstance().attackerObj.addObserver(this);
    }

    public void initDiceRollFrame(){
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
        diceInfoLabel.setBounds(0,20, 800,300);

        //diceInfoLabel.setText("<html>Attacker:"+ Game.getInstance().getAttacker()+" Armies left: "+
          //      Game.getInstance().getAttackerObj().getArmies()+"<br/>Attacked:"+Game.getInstance().getAttacked()
            //    +" Armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/></html>");

        diceInfoLabel.setVisible(true);

        dicePanel.add(diceInfoLabel);
        diceRollFrame.add(dicePanel);


    }


    @Override
    public void update(Observable observable, Object o) {
        //if(o instanceof Player || o instanceof Game){
          //  diceInfoLabel.setText("<html>Attacker:"+ Game.getInstance().getAttacker()+" Armies left: "+
            //        Game.getInstance().getAttackerObj().getArmies()+"<br/>Attacked:"+Game.getInstance().getAttacked()
              //      +" Armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/></html>");
        //}


    }
}
