package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
	String author;
	String wrap;
	String scroll;
	String warn;
	
	HashMap<String, Continent> continents;
	
	public Map() {
		this.continents = new HashMap<String, Continent>();
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setWrap(String wrap) {
		this.wrap = wrap;
	}
	
	public void setScroll(String scroll) {
		this.scroll = scroll;
	}
	
	public void setWarn(String warn) {
		this.warn = warn;
	}
	
	public void addContinent(Continent continent) {
		this.continents.put(continent.getName(), continent);
	}
	
	public void addTerritory(String[] info) {
		ArrayList<String> adjacents = new ArrayList<String>();
		for(int ctr = 4; ctr < info.length; ctr++) {
			adjacents.add(info[ctr]);
		}
		String continent = info[3];
		int x = Integer.parseInt(info[1]);
		int y = Integer.parseInt(info[2]);
		this.continents.get(continent).addTerritory(new Territory(info[0], x, y, adjacents));
	}
	
	public String checkOwner() {
		String rt = null;
		for (java.util.Map.Entry<String, Continent> entry : this.continents.entrySet()) {
		    Continent continentObj = entry.getValue();
		    if(rt == null) {
		    	continentObj.checkOwner();
		    }
		    else if(continentObj.checkOwner() == null && rt != continentObj.checkOwner()) {
		    	return null;
		    }
		}
		return rt;
	}
	
	public String[] getContinentsArray() {
		String[] continentsArray = new String[this.continents.size()];
		int ctr = 0;
		for (java.util.Map.Entry<String, Continent> entry : this.continents.entrySet()) {
		    String continentName = entry.getKey();
		    continentsArray[ctr] = continentName;
		    ctr++;
		}
		return continentsArray;
	}
	
	public String[] getTerritoriesArray(String selectedContinent) {
		int ctr = 0;
		Continent tmpContinent = this.continents.get(selectedContinent);
		String[] territoriesArray = new String[tmpContinent.territories.size()];

	    for (java.util.Map.Entry<String, Territory> entry2 : tmpContinent.territories.entrySet()) {
	    	territoriesArray[ctr] = entry2.getKey();
	    	ctr++;
	    }
	    
		return territoriesArray;
	}
	
	public void changeContinentReward(String name, int reward) {
		boolean found = false;
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
	    	if(entry2.getKey().equals(name)) {
	    		entry2.getValue().setReward(reward);
	    		found = true;
	    	}
	    }
		if(found == false) {
			System.out.println("Could not find the continent. Cannot change reward");
		}
		else {
			System.out.println("Continent reward changed");
		}
	}
	
	public void deleteContinent(String name) {
		Continent continent = null;
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
	    	if(entry2.getKey().equals(name)) {
	    		continent = entry2.getValue();
	    	}
	    }
		if(continent == null) {
			System.out.println("Could not find the continent. Cannot delete continent");
		}
		else {
			if(continent.getNumTerritories() != 0) {
				System.out.println("Continent still has territories in it. Delete all territories from this continent first");
			}
			else {
				this.continents.remove(name);
				System.out.println("Continent " + name + " deleted successfully");
			}
		}
	}
	
	public String[] getAdjacents(String territory) {
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
	    	if(entry2.getValue().getTerritory(territory) != null) {
	    		Territory tmpTerritory = entry2.getValue().getTerritory(territory);
	    		return tmpTerritory.adjacents.toArray(new String[0]);
	    	}
	    }
		return null;
	}
}
