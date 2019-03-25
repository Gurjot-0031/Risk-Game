package Model;

import View.DiceRollView;
import View.PhaseView;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.*;

/**
 * This class is the model player class
 * @author Team38
 *d
 */
public class Player extends Observable {
    private int id;
    private String name;
    private Color color;
    //boolean defenderConqueredFlag = false;

    int armies;

    private ArrayList<Card> cardList = new ArrayList<>();

    public ArrayList<Card> getCardList() {
        return this.cardList;
    }

    public static ArrayList<Integer> attackerDiceValues;
    public static ArrayList<Integer> attackedDiceValues;

    /**
     * The constructor
     * @param Id Player ID
     * @param name Player Name
     * @param color Player color
     * @param armies Player armies
     */
    public Player(int Id, String name, Color color, int armies) {
        this.setId(Id);
        this.setColor(color);
        this.setName(name);
        this.armies = armies;
    }

    public boolean isCardExchangePossible() {
        if (hasArtilleryCard(this.cardList) && hasCalvaryCard(this.cardList) && hasInfantaryCard(this.cardList)) {

            return true;

        } else
            return hasThreeArtillery(this.cardList) || hasThreeCalvary(this.cardList) || hasThreeInfantry(this.cardList);
    }

    public boolean hasArtilleryCard(ArrayList<Card> card) {

        for (Card o : card) {
            if (o.getCardType().equalsIgnoreCase("ARTILLERY")) {
                return true;
            }

        }
        return false;
    }
    public boolean hasInfantaryCard(ArrayList<Card> card) {

        for (Card o : card) {
            if (o.getCardType().equalsIgnoreCase("INFANTRY")) {
                return true;
            }

        }
        return false;
    }

    public boolean hasCalvaryCard(ArrayList<Card> card) {

        for (Card o : card) {
            if (o.getCardType().equalsIgnoreCase("CALVARY")) {
                return true;
            }

        }
        return false;
    }
    public boolean hasThreeArtillery(ArrayList<Card> card) {

        int count = 0;
        for (Card o : card) {

            if (o.getCardType().equalsIgnoreCase("ARTILLERY")) {
                count++;
            }
        }
        return count == 3;
    }

    public boolean hasThreeCalvary(ArrayList<Card> card) {

        int count = 0;
        for (Card o : card) {

            if (o.getCardType().equalsIgnoreCase("CALVARY")) {
                count++;
            }
        }
        return count == 3;
    }

    public boolean hasThreeInfantry(ArrayList<Card> card) {

        int count = 0;
        for (Card o : card) {

            if (o.getCardType().equalsIgnoreCase("INFANTRY")) {
                count++;
            }
        }
        return count == 3;
    }


    /**
     * Gets armies
     * @return The armies
     */
    public int getArmies() {
        return this.armies;
    }

    /**
     * Sets the armies for player
     * @param armies Input armies
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }

    /**
     * Removes the army from player
     * @param num Input count
     * @return Success or failure
     */
    public boolean removeArmy(int num) {
        if (num > this.armies) {
            System.out.println("Army removal Failed");
            return false;
        }
        this.armies -= num;
        return true;
    }

    /**
     * Sets the player id
     * @param id input id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the player id
     * @return Player id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the player name
     * @param name Player name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the player name
     * @return Player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the player color
     * @param color Player color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the player color
     * @return Player color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Perform reinforcement of the armies on territories
     * @param info the territory clicked for reinforcement
     * @return the event data and info
     */
    public String reinforce(String info){


        if (Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).removeArmy(1) == true) {
            Game.getInstance().getGameMap().getTerritory(info).addArmy(1);
            setChanged();
            notifyObservers(this);
        }


        if (Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() == 0) {
            Game.getInstance().nextTurn();
            //If a player has no army to deploy, the next player's turn comes
        }

        if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() > 0) {
            return "Event Processed";
        }

        boolean nextPhase = true;
        for (int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
            if(Game.getInstance().getPlayerById(i).getArmies() > 0) {
                Game.getInstance().setTurn(i);
                //Each Player deploys the reinforcement armies
                //one by one until all his reinforcement armies are deployed
                // then, the turn of next player comes.
                nextPhase = false;
                break;				//change
            }
        }
        if(nextPhase == true) {
            Game.getInstance().setTurn(0);
            Game.getInstance().nextPhase();
            return "Next Phase";
        }

        return "Event Processed";

    }

    /**
     * Perform fortification of the armies
     * @param info territory from which the fortification is to be done
     * @return the event data and info
     */
    public String fortify(String info){
        boolean fortificationPossible = false;
        for (Territory t : Game.getInstance().getGameMap().getTerritories()) {
            if(t.getOwner().getId() == Game.getInstance().getGameTurn() &&
                    t.getArmies() > 1) {
                for(String adjacent : t.getAdjacents()) {
                    Territory adjT = Game.getInstance().getGameMap().getTerritory(adjacent);
                    if(adjT.getOwner() == t.getOwner()) {
                        fortificationPossible = true;
                    }
                }
            }
        }

        if (fortificationPossible == false) {
            System.out.println("Fortification move not possible for Player " + Game.getInstance().getCurrPlayerName());
            JOptionPane.showMessageDialog(null,"Fortification move not possible for Player " + Game.getInstance().getCurrPlayerName());
            Game.getInstance().nextTurn();
            return "Processed";
        }

        Territory tmpTerritory = Game.getInstance().getGameMap().getTerritory(info);
        if(tmpTerritory == null || tmpTerritory.getOwner().getId() != Game.getInstance().getGameTurn()) {
            return "Territory does not belong to current player";
        }

        if (Game.getInstance().fortification_source == null) {
            for(String adjacent : tmpTerritory.getAdjacents()) {
                Territory adjT = Game.getInstance().getGameMap().getTerritory(adjacent);
                if(adjT != null && adjT.getOwner() == tmpTerritory.getOwner() && tmpTerritory.getArmies() > 1) {
                    Game.getInstance().fortification_source = info;
                    System.out.println("Please select target territory to fortify");
                    JOptionPane.showMessageDialog(null,"Please select target territory to fortify");
                    return "Fortification Source Event Processed";
                }
            }
            return "No adjacent territory can be fortified from " + info + ". Please select other territory";
        }
        else {
            if (tmpTerritory.getAdjacents().contains(Game.getInstance().fortification_source)) {
                Territory sourceT = Game.getInstance().getGameMap().getTerritory(Game.getInstance().fortification_source);
                int toMove = 0;
                while(true) {
                    try {
                        String num = JOptionPane.showInputDialog("How many armies to move? Max possible is: " + (sourceT.getArmies()-1));
                        toMove = Integer.parseInt(num);
                        if(toMove > sourceT.getArmies() || toMove < 0) {
                            System.out.println("Should be positive and Max possible move allowed is : " + (sourceT.getArmies()-1));
                            continue;
                        }
                        break;
                    }
                    catch(NumberFormatException e) {
                        System.out.println("Exception Handled");
                        System.out.println("Only numeric value >= 0 is allowed. Try again");
                    }
                }
                sourceT.removeArmies(toMove);
                tmpTerritory.addArmy(toMove);

                System.out.println(toMove + " armies moved from " + sourceT.getName() + " to " + tmpTerritory.getName());
                Game.getInstance().fortification_source = null;
                Game.getInstance().nextTurn();
                Game.getInstance().setGamePhase(3);
                Object obj = new Object();
                obj = sourceT.getName()+","+tmpTerritory.getName()+","+toMove;
                setChanged();
                notifyObservers(obj);
                return "";
            }
            else {
                return "Selected target: " + tmpTerritory.getName() +
                        " is not adjacent to selected source: " + Game.getInstance().fortification_source +
                        ". Please try again.";
            }
        }
    }

    /**
     * Perform attack phase of the game
     * @param attacker the attacking territory
     * @param attacked the defending territory
     * @param numOfDiceAttacker number of dice selected by the attacker to be thrown
     * @param numOfDiceAttacked number of dice selected by the defender to be thrown
     * @return
     */
    ArrayList<String> diceThrowResults= new ArrayList<>();
    public String attack(Territory attacker, Territory attacked,int numOfDiceAttacker,int numOfDiceAttacked) throws NullPointerException{

        if (attacker!=null && attacked!=null && numOfDiceAttacker!=-1 && numOfDiceAttacked!=-1) {
            //int remainingDiceAttacker = numOfDiceAttacker;
            //int remainingDiceAttacked = numOfDiceAttacker;
            //DiceRollView.getInstance().loadFrame();
            System.out.println("Attacker dice # "+Game.getInstance().getNumOfDiceAttacker());
            System.out.println("Attacked dice # "+Game.getInstance().getNumOfDiceAttacked());

            String runTimeMessageAttack = "";
            attackerDiceValues = rollDice(numOfDiceAttacker);
            attackedDiceValues = rollDice(numOfDiceAttacked);

            Game.getInstance().getAttackerObj().setContinueAttacking(true);
            diceThrowResults.add("First dice roll<br/>");
            //this.defenderConqueredFlag = false;
            while(Game.getInstance().getAttackerObj().ContinueAttacking()==true){
                if (attackerDiceValues.size()>0) {
                    if (attackedDiceValues.size() > 0) {
                        System.out.println("Next Dice Roll");
                        runTimeMessageAttack = "Next Dice Roll<br/>";
                        Game.getInstance().getAttackerObj().setContinueAttacking(!compareDiceResults());
                        diceThrowResults.add(runTimeMessageAttack);
                    }
                    else {
                        Game.getInstance().getAttackerObj().setContinueAttacking(false);
                        System.out.println("Defender has rolled all the dices..Defender Lost");
                        //attackerDiceValues = rollDice(attackerDiceValues.size());
                        runTimeMessageAttack = "<br/>";
                        //DiceRollView.getInstance().displayDefenderData(runTimeMessageAttack);
                        diceThrowResults.add(runTimeMessageAttack);
                    }
                }
                else {
                    Game.getInstance().getAttackerObj().setContinueAttacking(false);
                    System.out.println("Attacker has rolled all the dices..Attacker Lost");
                    runTimeMessageAttack = "<br/>";
                    diceThrowResults.add(runTimeMessageAttack);
                }
            }
        }

        DiceRollView.getInstance().displayContent(diceThrowResults);
        diceThrowResults.clear();
        return "Attack Phase";
    }
   /* public String allOutModeAttack(Territory attacker, Territory attacked,int numOfDiceAttacker,int numOfDiceAttacked){

        return "Attack Phase: All Out";
    }*/

    /**
     * Perform dice roll to get the random dice values
     * @param noOfDices number of dice selected for the play
     * @return
     */
    public ArrayList<Integer> rollDice(int noOfDices){
        ArrayList<Integer> diceVal = new ArrayList<Integer>();
        ArrayList<Integer> output = new ArrayList<Integer>();
        diceVal.add(1);
        diceVal.add(2);
        diceVal.add(3);
        diceVal.add(4);
        diceVal.add(5);
        diceVal.add(6);

        Collections.shuffle(diceVal);
        for (int i=0; i<noOfDices; i++)
        {
            Collections.shuffle(diceVal);
            output.add(diceVal.get(i));
        }
        return output;
    }

    /**
     * Compare the dice values to determine the winner of a particular attack
     * and notify the observers accordingly
     * @return whether the attack is finished or not
     */
    public boolean compareDiceResults() {
        boolean attackFinished = false;
        int[] highValue = getMax();
        String runTimeMessage = "";
        String runTimeMessageAttackerDiceValue = ""+highValue[0];
        String runTimeMessageAttackedDiceValue = ""+highValue[1];;
        if (highValue[0]>highValue[1]){
            System.out.println("Attacker won a dice roll");
            Game.getInstance().getAttackedObj().removeArmies(1);
            runTimeMessage = "Attacker Dice Value: "+highValue[0]+"&nbsp; Defender Dice Value: "+highValue[1]+"<br/>Attacker won a dice roll, so, Defender lost an army<br/>" +
                    "Remaining armies for defender: "+ Game.getInstance().getAttackedObj().getArmies()+"<br/>";
            //runTimeMessage += "Attacker won a dice roll, so, Defender lost an army<br/>" +
            //		"Remaining armies for defender: "+ Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>";
            diceThrowResults.add(runTimeMessage);

            System.out.println("Defender lost a army");
            System.out.println("Defender armies left:"+Game.getInstance().getAttackedObj().getArmies());

            /*DiceRollView.getInstance().getDiceInfoLabel().setText("<html>Attacker:"+ Game.getInstance().getAttacker()+"<br/>Attacker Armies left: "+
                    Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>Defender:"+Game.getInstance().getAttacked()
                    +"<br/> Defender armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>Dice Results<br/>"
                    +"Attacker: "+runTimeMessageAttackerDiceValue+"     Defender: "+runTimeMessageAttackedDiceValue
                    +"<br/><br/>"+runTimeMessage+"</html>");*/

            if (Game.getInstance().getAttackedObj().getArmies()==0){
                attackFinished = true;
                //System.out.println("Defender rolled all his Dices");
                //diceThrowResults.add("Defender rolled all his Dices");
                Game.getInstance().getAttackedObj().setOwner(Game.getInstance().getCurrPlayer());
                //Card obj = new Card();
                Game.getInstance().getAttackerObj().getOwner().getCardList().add(new Card());

                System.out.println("Defender Territory conquered by attacker");
                diceThrowResults.add("Defender Territory conquered by attacker<br/>Attacker received a card ");
                DiceRollView.getInstance().displayContent(diceThrowResults);
                diceThrowResults.clear();
                for (JButton btn:PhaseView.getInstance().getBtnTerritories().values()){
                    if(btn.getText()==Game.getInstance().getAttackedObj().getName()){
                        btn.setBackground(Game.getInstance().getCurrPlayer().getColor());
                    }
                }
                if (isThereAWinner()){
                    JOptionPane.showMessageDialog(null,Game.getInstance().getAttacker()+" has won the Game");

                }
                JOptionPane.showConfirmDialog(null,Game.getInstance().getCurrPlayerName()+" continue attacking other territories?");
                if (JOptionPane.YES_OPTION==0) {
                    //DiceRollView.getInstance().getDiceRollFrame().dispatchEvent(new WindowEvent(DiceRollView.getInstance().getDiceRollFrame().WIN));
                    //DiceRollView.getInstance().getDiceRollFrame().dispose();
                    //PhaseView.getInstance().loadMap(Game.getInstance().getGameMap());

                    //PhaseView.getInstance().getBtnTerritories().containsKey()

                    DiceRollView.getInstance().getDiceRollBtn().setVisible(true);
                    DiceRollView.getInstance().getDiceInfoLabel().setText("");
                    DiceRollView.getInstance().getDiceRollFrame().setVisible(false);
                    //this.defenderConqueredFlag = true;
                    Game.getInstance().setAttacker(null);
                    Game.getInstance().setAttacked(null);
                    Game.getInstance().setAttackerObj(null);
                    Game.getInstance().setAttackedObj(null);
                    Game.getInstance().setNumOfDiceAttacker(-1);
                    Game.getInstance().setNumOfDiceAttacked(-1);

                }

            }

        }
        else {
            System.out.println("Defender Won a dice roll");
            Game.getInstance().getAttackerObj().removeArmies(1);
            runTimeMessage = "Attacker Dice Value: "+highValue[0]+"&nbsp; Defender Dice Value: "+highValue[1]+"<br/>Defender won a dice roll, so, Attacker lost an army<br/>" +
                    "Remaining armies for defender: "+ Game.getInstance().getAttackedObj().getArmies()+"<br/>";
            //runTimeMessage += "Defender won a dice roll, so, Attacker lost an army<br/>" +
            //		"Remaining armies for attacker: "+ Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>";

            diceThrowResults.add(runTimeMessage);

            System.out.println("Attacker lost a army");
            System.out.println("Attacker armies left:"+Game.getInstance().getAttackerObj().getArmies());
            if (Game.getInstance().getAttackerObj().getArmies()==0){
                System.out.println("Attacker lost all his armies, nothing conquered");
                JOptionPane.showMessageDialog(null,"Attacker lost all his armies, attacker can now fortify:");
                //runTimeMessage = "Attacker lost all his armies, attacker can now fortify..";
                DiceRollView.getInstance().getContinueAttackBtn().setVisible(false);
                attackFinished = true;
            }
            if (attackerDiceValues.size()==0){
                attackFinished = true;
                System.out.println("Attacker rolled all his Dices");

                if(Game.getInstance().getAttackerObj().getArmies()==0){
                    System.out.println("Attacker Territory lost all armies");
                }
            }
        }
        setChanged();
        notifyObservers(this);
        return attackFinished;

    }

    /**
     * Getting the current highest dice values among the attacker and defender
     * during a fresh attack and also if attack is continued
     * @return
     */
    public int[] getMax(){
        int attackerHighest = 0;
        int attackedHighest = 0;
        int index=-1;
        for (int i=0; i<attackerDiceValues.size(); i++){
            if (attackerHighest<attackerDiceValues.get(i)) {
                attackerHighest = attackerDiceValues.get(i);
                index = i;
            }

        }
        // Iterate through the DiceValues of attacker
        // Remove the previous maximum value from the current attackerDiceValues array
        // Also continue with the only "remaining dice values" of attackerDiceValues array
        // if the attacker chooses to continue attacking
        if (index!=-1)
            attackerDiceValues.remove(index);

        index = -1;

        for (int i=0; i<attackedDiceValues.size(); i++){
            if (attackedHighest<attackedDiceValues.get(i)) {
                attackedHighest = attackedDiceValues.get(i);
                index = i;

            }
        }
        // Iterate through the DiceValues of defender
        // Remove the previous maximum value from the current attackedDiceValues array
        // Also continue with the only "remaining dice values" of attackedDiceValues array
        // if the defender is attacked again
        if (index!=-1)
            attackedDiceValues.remove(index);

        return new int[]{attackerHighest,attackedHighest};
    }

    public boolean isThereAWinner(){
        for (Territory ter:Game.getInstance().getGameMap().getTerritories()){
            if (ter.getOwner().getName() != Game.getInstance().getAttacker())
                return false;
        }
        return true;
    }
}
