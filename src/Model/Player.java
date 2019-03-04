package Model;

import java.awt.Color;

/**
 * This class is the model player class
 * @author Team38
 *
 */
public class Player {
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
}
