package Model;

import java.util.HashMap;

public class Continent {

	String name;
	int reward;
	
	HashMap<String, Territory> territories;
	
	public Continent(String name, int reward) {
		this.name = name;
		this.reward = reward;
		this.territories = new HashMap<String, Territory>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setReward(int reward) {
		this.reward = reward;
	}
	
	public int getReward() {
		return this.reward;
	}
	
	public int getNumTerritories() {
		return territories.size();
	}
	
	public void addTerritory(Territory territory) {
		this.territories.put(territory.getName(), territory);
	}
	
	public String checkOwner() {
		String rt = null;
		for (java.util.Map.Entry<String, Territory> entry : this.territories.entrySet()) {
		    String territoryName = entry.getKey();
		    Territory territoryObj = entry.getValue();
		    if(rt == null) {
		    	territoryObj.getOwner();
		    }
		    else if(rt != territoryObj.getOwner().getName()) {
		    	return null;
		    }
		}
		return rt;
	}
	
	public Territory getTerritory(String territory) {
		if(this.territories.containsKey(territory)) {
			return this.territories.get(territory);
		}
		return null;
	}
	
	public void handleTerritoryDeletion(String territory) {
		for (java.util.Map.Entry<String, Territory> entry : this.territories.entrySet()) {
		    Territory territoryObj = entry.getValue();
		    territoryObj.removeAdjacent(territory);
		}
	}
	
	public boolean deleteTerritory(String territory) {
		if(this.territories.containsKey(territory)) {
			this.territories.remove(territory);
		}
		else {
			return false;
		}
		return true;
	}
}
