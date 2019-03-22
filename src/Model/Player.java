package Model;

import View.DiceRollView;
import View.PhaseView;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

/**
 * This class is the model player class
 * @author Team38
 *d
 */
public class Player extends Observable{
	private int id;
	private String name;
	private Color color;
	int armies;

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
		if(num > this.armies) {
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

		
		if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).removeArmy(1) == true) {
			Game.getInstance().getGameMap().getTerritory(info).addArmy(1);
			setChanged();
			notifyObservers(this);
		}
		
		
		if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() == 0) {
			Game.getInstance().nextTurn();
			//If a player has no army to deploy, the next player's turn comes
		}
		
		if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() > 0) {
			return "Event Processed";
		}
		
		boolean nextPhase = true;
		for(int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
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
		for(Territory t : Game.getInstance().getGameMap().getTerritories()) {
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
		
		if(fortificationPossible == false) {
			System.out.println("Fortification move not possible for Player " + Game.getInstance().getCurrPlayerName());
			Game.getInstance().nextTurn();
			return "Processed";
		}
		
		Territory tmpTerritory = Game.getInstance().getGameMap().getTerritory(info);
		if(tmpTerritory == null || tmpTerritory.getOwner().getId() != Game.getInstance().getGameTurn()) {
			return "Territory does not belong to current player";
		}
		
		if(Game.getInstance().fortification_source == null) {
			for(String adjacent : tmpTerritory.getAdjacents()) {
				Territory adjT = Game.getInstance().getGameMap().getTerritory(adjacent);
				if(adjT != null && adjT.getOwner() == tmpTerritory.getOwner() && tmpTerritory.getArmies() > 1) {
					Game.getInstance().fortification_source = info;
					System.out.println("Please select target territory to fortify");
					return "Fortification Source Event Processed";
				}
			}
			return "No adjacent territory can be fortified from " + info + ". Please select other territory";
		}
		else {
			if(tmpTerritory.getAdjacents().contains(Game.getInstance().fortification_source)) {
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
    public String attack(Territory attacker, Territory attacked,int numOfDiceAttacker,int numOfDiceAttacked){
		if(attacker!=null && attacked!=null && numOfDiceAttacker!=-1 && numOfDiceAttacked!=-1) {
			//int remainingDiceAttacker = numOfDiceAttacker;
			//int remainingDiceAttacked = numOfDiceAttacker;
			DiceRollView.getInstance().loadFrame();
			System.out.println("Attacker dice # "+Game.getInstance().getNumOfDiceAttacker());
			System.out.println("Attacked dice # "+Game.getInstance().getNumOfDiceAttacked());

            String runTimeMessageAttack = "";
			attackerDiceValues = rollDice(numOfDiceAttacker);
			attackedDiceValues = rollDice(numOfDiceAttacked);

			//String continueAttacking = "Y";


            boolean isAttackFinished = compareDiceResults();
            //While Attack not finished(no of dices available>0)
			while(!isAttackFinished){
				//giving option to the user..

                int continueAttack;

                //continueAttack =  JOptionPane.showInputDialog("Continue Attacking?: (Y/N)");
				continueAttack = JOptionPane.showConfirmDialog(null,"Continue Attacking?: (Y/N)");
                if(continueAttack == JOptionPane.YES_OPTION)
				//if(continueAttack.equalsIgnoreCase("Y"))
                    Game.getInstance().getAttackerObj().setContinueAttacking(true);
                else
                    Game.getInstance().getAttackerObj().setContinueAttacking(false);

                //continueAttacking= JOptionPane.showInputDialog("Continue Attacking?: (Y/N)");
				if(Game.getInstance().getAttackerObj().ContinueAttacking()) {
					if(attackerDiceValues.size()>0) {
                        if (attackedDiceValues.size() > 0) {
                            //attackedDiceValues = rollDice(attackedDiceValues.size());
                            System.out.println("Next Dice roll");
                            runTimeMessageAttack = "Next Dice roll";
                            isAttackFinished = compareDiceResults();
                            DiceRollView.getInstance().getDiceInfoLabel().setText("<html>Attacker:"+ Game.getInstance().getAttacker()+"<br/>Attacker Armies left: "+
                                    Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>Defender:"+Game.getInstance().getAttacked()
                                    +"<br/> Defender armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>" +
                                    runTimeMessageAttack+"</html>");
                        }
                        else {
                            System.out.println("Defender has rolled all the dices..Defender Lost");
                            //attackerDiceValues = rollDice(attackerDiceValues.size());
                            runTimeMessageAttack = "Defender has rolled all the dices..Defender Lost";
                            DiceRollView.getInstance().getDiceInfoLabel().setText("<html>Attacker:"+ Game.getInstance().getAttacker()+"<br/>Attacker Armies left: "+
                                    Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>Defender:"+Game.getInstance().getAttacked()
                                    +"<br/> Defender armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>" +
                                    runTimeMessageAttack+"</html>");
                        }
                    }
					else {
                        System.out.println("Attacker has rolled all the dices..Attacker Lost");
                        runTimeMessageAttack = "Attacker has rolled all the dices..Attacker Lost";
                        DiceRollView.getInstance().getDiceInfoLabel().setText("<html>Attacker:"+ Game.getInstance().getAttacker()+"<br/>Attacker Armies left: "+
                                Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>Defender:"+Game.getInstance().getAttacked()
                                +"<br/> Defender armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>" +
                                runTimeMessageAttack+"</html>");
                    }
				}
				else {
                    System.out.println("Attacker discontinued the attack..");
                    runTimeMessageAttack = "Attacker discontinued the attack..";
                    isAttackFinished = true;
                    DiceRollView.getInstance().getDiceInfoLabel().setText("<html>Attacker:"+ Game.getInstance().getAttacker()+"<br/>Attacker Armies left: "+
                            Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>Defender:"+Game.getInstance().getAttacked()
                            +"<br/> Defender armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>" +
                            runTimeMessageAttack+"</html>");
                }
			}
		}
		return "Attack Phase";
    }

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
		for(int i=0;i<noOfDices;i++)
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
		if(highValue[0]>highValue[1]){
			System.out.println("Attacker won a dice roll");
			Game.getInstance().getAttackedObj().removeArmies(1);
			runTimeMessage = "Attacker won a dice roll<br/>Defender lost an army<br/>";

            System.out.println("Defender lost a army");
            System.out.println("Defender armies left:"+Game.getInstance().getAttackedObj().getArmies());

            //DiceRollView.getInstance().getDiceRollFrame();
            DiceRollView.getInstance().getDiceInfoLabel().setText("<html>Attacker:"+ Game.getInstance().getAttacker()+"<br/>Attacker Armies left: "+
                    Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>Defender:"+Game.getInstance().getAttacked()
                    +"<br/> Defender armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>Dice Results<br/>"
                    +"Attacker: "+runTimeMessageAttackerDiceValue+"     Defender: "+runTimeMessageAttackedDiceValue
                    +"<br/><br/>"+runTimeMessage+"</html>");

            //JOptionPane.showMessageDialog(DiceRollView.getInstance().getDiceRollFrame(),
              //      "<html>Defender lost a army<br/>Defender armies left: "
                //            +Game.getInstance().getAttackedObj().getArmies()+"</html>");
			if(Game.getInstance().getAttackedObj().getArmies()==0){
				System.out.println("Defender Territory conquered by attacker");
				attackFinished = true;
			}
			//Game.getInstance().getAttackerO
		}
		else{
            runTimeMessage = "Defender won a dice roll<br/>Attacker lost an army<br/>";

            DiceRollView.getInstance().getDiceInfoLabel().setText("<html>Attacker:"+ Game.getInstance().getAttacker()+"<br/>Attacker Armies left: "+
                    Game.getInstance().getAttackerObj().getArmies()+"<br/><br/>Defender:"+Game.getInstance().getAttacked()
                    +"<br/> Defender armies left: "+Game.getInstance().getAttackedObj().getArmies()+"<br/><br/>Dice Results<br/>" +
                    "Attacker: "+runTimeMessageAttackerDiceValue+"     Defender: "+runTimeMessageAttackedDiceValue+
                    "<br/><br/>" +runTimeMessage+"</html>");

			System.out.println("Defender Won a dice roll");
			Game.getInstance().getAttackerObj().removeArmies(1);
            System.out.println("Attacker lost a army");
            System.out.println("Attacker armies left:"+Game.getInstance().getAttackerObj().getArmies());
            //JOptionPane.showMessageDialog(DiceRollView.getInstance().getDiceRollFrame(),
              //      "<html>Attacker lost a army<br/>Attacker armies left: "
                //            +Game.getInstance().getAttackerObj().getArmies()+"</html>");
			if(Game.getInstance().getAttackerObj().getArmies()==0){
				System.out.println("Attacker lost all his armies, nothing conquered");
				attackFinished = true;
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
		for(int i=0;i<attackerDiceValues.size();i++){
			if(attackerHighest<attackerDiceValues.get(i)) {
				attackerHighest = attackerDiceValues.get(i);
				//attackerDiceValues.remove(i);
			}
		}
		// Iterate through the DiceValues of attacker
		// Remove the previous maximum value from the current attackerDiceValues array
		// Also continue with the only "remaining dice values" of attackerDiceValues array
		// if the attacker chooses to continue attacking
		Iterator attckItr = attackerDiceValues.iterator();
		while (attckItr.hasNext()) {
			int x = (Integer)attckItr.next();
			if (x == attackerHighest)
				attckItr.remove();
		}
		//attackerHighest = Collections.max(attackerDiceValues);
		//int index = Collections.lastIndexOfSubList(attackerDiceValues);
		//attackedDiceValues.remove(attackedHighest);

		for(int i=0;i<attackedDiceValues.size();i++){
			if(attackedHighest<attackedDiceValues.get(i)) {
				attackedHighest = attackedDiceValues.get(i);
				//attackedDiceValues.remove(i);
			}
		}
		// Iterate through the DiceValues of defender
		// Remove the previous maximum value from the current attackedDiceValues array
		// Also continue with the only "remaining dice values" of attackedDiceValues array
		// if the defender is attacked again
		Iterator defendItr = attackedDiceValues.iterator();
		while (defendItr.hasNext()) {
			int x = (Integer)defendItr.next();
			if (x == attackedHighest)
				defendItr.remove();
		}

		return new int[]{attackerHighest,attackedHighest};
	}
}
