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
	
	public int getReward() {
		return this.reward;
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
		    else if(rt != territoryObj.getOwner()) {
		    	return null;
		    }
		}
		return rt;
	}
}
