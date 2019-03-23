package Model;

import java.util.HashMap;

/**
 * The model class for continent
 * 
 * @author Team38
 *
 */
public class Continent {

	String name;
	int reward;

	HashMap<String, Territory> territories;

	/**
	 * The constructor
	 * 
	 * @param name
	 *            Continent name
	 * @param reward
	 *            Continent reward
	 */
	public Continent(String name, int reward) {
		this.name = name;
		this.reward = reward;
		this.territories = new HashMap<String, Territory>();
	}

	/**
	 * Gets the name of continent
	 * 
	 * @return Continent name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the reward for continent
	 * 
	 * @param reward
	 *            The input reward
	 */
	public void setReward(int reward) {
		this.reward = reward;
	}

	/**
	 * Gets the continent reward
	 * 
	 * @return Continent reward
	 */
	public int getReward() {
		return this.reward;
	}

	/**
	 * Gets number of territories
	 * 
	 * @return Number of territories
	 */
	public int getNumTerritories() {
		return territories.size();
	}

	/**
	 * Adds the territory to continent
	 * 
	 * @param territory
	 *            The new territory
	 */
	public void addTerritory(Territory territory) {
		this.territories.put(territory.getName(), territory);
	}

	/**
	 * Checks the owner of continent
	 * 
	 * @param id
	 *            The input player id
	 * @return Success or failure
	 */
	public boolean checkOwner(int id) {
		boolean rt = true;
		for (java.util.Map.Entry<String, Territory> entry : this.territories.entrySet()) {
			Territory territoryObj = entry.getValue();
			if (territoryObj.getOwner().getId() != id) {
				return false;
			}
		}
		return rt;
	}

	/**
	 * Gets the territory
	 * 
	 * @param territory
	 *            Input name of territory
	 * @return The territory
	 */
	public Territory getTerritory(String territory) {
		if (this.territories.containsKey(territory)) {
			return this.territories.get(territory);
		}
		return null;
	}

	/**
	 * Handle the territory deletion
	 * 
	 * @param territory
	 *            Input territory name
	 */
	public void handleTerritoryDeletion(String territory) {
		for (java.util.Map.Entry<String, Territory> entry : this.territories.entrySet()) {
			Territory territoryObj = entry.getValue();
			territoryObj.removeAdjacent(territory);
		}
	}

	/**
	 * Deletes the territory
	 * 
	 * @param territory
	 *            Input territory name
	 * @return Success or failure
	 */
	public boolean deleteTerritory(String territory) {
		if (this.territories.containsKey(territory)) {
			this.territories.remove(territory);
		} else {
			return false;
		}
		return true;
	}
}
