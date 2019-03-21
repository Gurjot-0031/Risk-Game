package Model;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.JOptionPane;

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

    public String attack(String attacker, String attacked){
    	

    	
		return "Attack Phase";
    }
}
