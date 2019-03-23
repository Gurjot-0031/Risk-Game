package Model;

import java.util.ArrayList;

/**
 * This class acts as territory model
 * 
 * @author Team38
 *
 */
public class Territory {
	String name;
	int x;
	int y;
	ArrayList<String> adjacents;
	Player owner;
	int armies;
	Continent parentContinent;

	public Continent getParentContinent() {
		Continent temp=null;
		for (Continent cont : Game.getInstance().getGameMap().getContinentsAsObjects()) {
			for (Territory terr : cont.getTerritories().values()) {
				if (terr.getName() == this.getName())
					temp = cont;
			}
		}

		return temp;


	}

	public void setParentContinent(Continent parentContinent) {
		this.parentContinent = parentContinent;
	}



	public boolean ContinueAttacking() {
		return ContinueAttacking;
	}

	public void setContinueAttacking(boolean continueAttacking) {
		ContinueAttacking = continueAttacking;
	}

	boolean ContinueAttacking = true;

	/**
	 * The constructor
	 * 
	 * @param name
	 *            Territory name
	 * @param x
	 *            Territory x
	 * @param y
	 *            Territory y
	 * @param adjacents
	 *            Territory's adjacent territories
	 */
	public Territory(String name, int x, int y, ArrayList<String> adjacents) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.adjacents = adjacents;
		this.armies = 0;
	}

	/**
	 * Gets the armies on territory
	 * 
	 * @return Number of armies
	 */
	public int getArmies() {
		return this.armies;
	}

	/**
	 * Adds army to territory
	 * 
	 * @param num
	 *            Input number
	 */
	public void addArmy(int num) {
		this.armies += num;
	}

	/**
	 * Gets the territory name
	 * 
	 * @return The territory name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the territory x
	 * 
	 * @return Territory X
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the territory y
	 * 
	 * @return Territory y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the adjacent of this territory
	 * 
	 * @return List of adjacents
	 */
	public ArrayList<String> getAdjacents() {
		return this.adjacents;
	}

	/**
	 * Checks whether input territory is adjacent to this or not
	 * 
	 * @param adjacent
	 *            Input territory to check
	 * @return Success or failure
	 */
	public boolean checkAdjacent(String adjacent) {
		if (this.adjacents.contains(adjacent)) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the owner of territory
	 * 
	 * @param owner
	 *            Input owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * Gets the owner of territory
	 * 
	 * @return The owner player
	 */
	public Player getOwner() {
		return this.owner;
	}

	/**
	 * Remove armies from territory
	 * 
	 * @param num
	 *            Input count
	 */
	public void removeArmies(int num) {
		this.armies -= num;
	}

	/**
	 * Remove adjacent from territory
	 * 
	 * @param adjacent
	 *            Input adjacent
	 */
	public void removeAdjacent(String adjacent) {
		if (this.adjacents.contains(adjacent)) {
			this.adjacents.remove(adjacent);
		}
	}

	/**
	 * Adds adjacent to territory
	 * 
	 * @param adjacent
	 *            Input adjacent
	 * @return Success or failure
	 */
	public boolean addAdjacent(String adjacent) {
		if (this.adjacents.contains(adjacent)) {
			return false;
		}
		this.adjacents.add(adjacent);
		return true;
	}
}
