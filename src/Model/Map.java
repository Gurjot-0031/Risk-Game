package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
	String author;
	String wrap;
	String scroll;
	String warn;
	String path;
	
	HashMap<String, Continent> continents;
	
	public Map(String path) {
		this.path = path;
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
	
	
	/*
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
	*/
	
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
	
	public boolean addTerritory(String info) {
		try {
			String[] infoA = info.split(",");
			String continent = infoA[0];
			String tName = infoA[1];
			Integer x = Integer.parseInt(infoA[2]);
			Integer y = Integer.parseInt(infoA[3]);
			ArrayList<String> adjacents = new ArrayList<String>();
			
			if(infoA.length < 5) {
				return false;
			}
			
			for(int i = 4; i < infoA.length; i++) {
				adjacents.add(infoA[i]);
			}
			
			Territory tmpTerritory = new Territory(tName, x, y, adjacents);
			this.continents.get(continent).addTerritory(tmpTerritory);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean delTerritory(String info) {
		try {
			String[] infoA = info.split(",");
			String continent = infoA[0];
			String tName = infoA[1];
			Continent tmpContinent = this.continents.get(continent);
			boolean rt = tmpContinent.deleteTerritory(tName);
			if(rt == false) {
				System.out.println("Could not find the territory to be deleted");
				return rt;
			}
			for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
				entry2.getValue().handleTerritoryDeletion(tName);
		    }
		}
		catch(Exception e) {
			System.out.println("Could not find the territory to be deleted");
			return false;
		}
		return true;
	}
	
	public boolean addAdjacent(String info) {
		try {
			String[] infoA = info.split(",");
			if(infoA.length < 3) {
				System.out.println("Invalid information while adding adjacent");
				return false;
			}
			String continent = infoA[0];
			String tName = infoA[1];
			Continent tmpContinent = this.continents.get(continent);
			Territory tmpTerritory = tmpContinent.getTerritory(tName);
			tmpTerritory.addAdjacent(infoA[2]);
		}
		catch(Exception e) {
			System.out.println("Could not add adjacent");
			return false;
		}
		return true;
	}
	
	public boolean deleteAdjacent(String info) {
		try {
			String[] infoA = info.split(",");
			if(infoA.length < 3) {
				System.out.println("Invalid information while deleting adjacent");
				return false;
			}
			String continent = infoA[0];
			String tName = infoA[1];
			Continent tmpContinent = this.continents.get(continent);
			Territory tmpTerritory = tmpContinent.getTerritory(tName);
			tmpTerritory.removeAdjacent(infoA[2]);
		}
		catch(Exception e) {
			System.out.println("Could not add adjacent");
			return false;
		}
		return true;
	}
	
	
	public boolean check_empty_continents() {
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
	    	if(entry2.getValue().getNumTerritories() < 1) {
	    		System.out.println("Empty Continent " + entry2.getKey());
	    		return false;
	    	}
	    }
		return true;
	}
	
	public Territory getTerritory(String name) {
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
			if(entry2.getValue().getTerritory(name) != null) {
				return entry2.getValue().getTerritory(name);
			}
		}
		return null;
	}
	
	public boolean check_adjacency() {
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
	    	Continent continent = entry2.getValue();
	    	
	    	for(java.util.Map.Entry<String, Territory> entry : continent.territories.entrySet()) {
	    		Territory territory = entry.getValue();
	    		if(territory.getAdjacents().size() < 1) {
	    			System.out.println("A territory with no adjacents found: " + territory.getName());
	    			return false;
	    		}
	    		
	    		ArrayList<String> adjacents = territory.getAdjacents();
	    		for(String adjacent : adjacents) {
	    			if(this.getTerritory(adjacent) == null) {
	    				System.out.println("The adjacent territory " + adjacent + " does not yet exist itself");
	    				return false;
	    			}
	    			else {
	    				if(this.getTerritory(adjacent).checkAdjacent(territory.getName()) == false) {
	    					System.out.println("Adjacency error between " + territory.getName() + " and " + adjacent);
	    					return false;
	    				}
	    			}
	    		}
	    	}
	    }
		return true;
	}
	
	public boolean mark_visited(String territory, HashMap<String, Boolean> visited, Continent continent) {
		if(visited.containsKey(territory)) {
			return true;
		}
		if(continent != null) {
			if(continent.territories.get(territory) != null) {
				visited.put(territory, true);
			}
		}
		else {
			visited.put(territory, true);
		}
		
		for(String adjacent : this.getTerritory(territory).getAdjacents()) {
			if(continent != null) {
				if(continent.territories.get(adjacent) != null) {
					mark_visited(adjacent, visited, continent);
				}
			}
			else {
				mark_visited(adjacent, visited, continent);
			}
		}
		return true;
	}
	
	public boolean check_connectivity() {
		String anyTerritory = this.continents.entrySet().iterator().next().getValue().territories.entrySet().iterator().next().getKey();
		
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		mark_visited(anyTerritory, visited, null);
		
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
			Continent continent = entry2.getValue();
			
			HashMap<String, Boolean> visitedC = new HashMap<String, Boolean>();
			mark_visited(continent.territories.entrySet().iterator().next().getKey(), visitedC, continent);
			
			for (java.util.Map.Entry<String, Territory> entry : continent.territories.entrySet()) {
				if(visitedC.get(entry.getKey()) == null) {
					System.out.println("Continent " + continent.name + " inside map is not connected graph");
					return false;
				}
				if(visited.get(entry.getKey()) == null) {
					System.out.println("The map is not a connected graph");
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean saveMapToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.path));
			writer.write("[map]\n");
			if(this.author == null) {
				System.out.println("Map has no author.");
				writer.close();
				return false;
			}
			writer.write("author=" + this.author + "\n");
			writer.write("[continents]\n");
			
			for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
				writer.write(entry2.getValue().getName() + "=" + entry2.getValue().getReward() + "\n");
			}
			writer.write("[territories]\n");
			for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
				for (java.util.Map.Entry<String, Territory> entry3 : entry2.getValue().territories.entrySet()) {
					Territory tmp = entry3.getValue();
					String line = tmp.getName() + "," + tmp.getX() + "," + tmp.getY() + "," + entry2.getKey();
					for(String adjacent : tmp.getAdjacents()) {
						line += "," + adjacent;
					}
					writer.write(line + "\n");
				}
			}
		    writer.close();
		}
		catch(Exception e) {
			System.out.println("Error while writing map to file. Map could not be saved.");
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public boolean saveMap() {
		if(check_empty_continents() != true) {
			return false;
		}
		else if(check_adjacency() != true) {
			return false;
		}
		else if(check_connectivity() != true) {
			return false;
		}
		
		if(saveMapToFile() != true) {
			return false;
		}
		System.out.println("Map Saved");
		return true;
	}
	
	
	public boolean readMapFile() {
		BufferedReader reader;
		int flag = 0;
		try {
			reader = new BufferedReader(new FileReader(this.path));
			String line = reader.readLine();
			while(line != null) {
				line = line.replaceAll("\\s", "").toLowerCase();
				if(line.length() < 2) {
					line = reader.readLine();
					continue;
				}
				
				if(flag == 0) {
					if(line.equals("[map]")) {
						line = reader.readLine();
						continue;
					}
					else if(line.equals("[continents]")) {
						flag = 1;
					}
					processMapLine(line);
				}
				else if(flag == 1) {
					if(line.equals("[territories]")) {
						flag = 2;
					}
					processContinentLine(line);
				}
				else if(flag == 2) {
					processTerritoryLine(line);
				}
				
				line = reader.readLine();
			}
			
			if(flag != 2) {
				System.out.println("Map not read correctly.");
				return false;
			}
			else {
				System.out.println("Map has been read.");
			}
		}
		catch (Exception e) {
			System.out.println("Map not read correctly.");
			System.out.println(e);
			e.printStackTrace();
		}
		
		if(check_empty_continents() != true) {
			return false;
		}
		else if(check_adjacency() != true) {
			return false;
		}
		else if(check_connectivity() != true) {
			return false;
		}
		
		return true;
	}
	
	public boolean processMapLine(String line) {
		String[] info = line.split("=");
		if(info[0].equals("author")) {
			this.setAuthor(info[1]);
		}
		else if(info[0].equals("wrap")) {
			this.setWrap(info[1]);
		}
		else if(info[0].equals("scroll")) {
			this.setScroll(info[1]);
		}
		else if(info[0].equals("warn")) {
			this.setWarn(info[1]);
		}
		
		return true;
	}
	
	public boolean processContinentLine(String line) {
		String[] info = line.split("=");
		if(info.length < 2) {
			return false;
		}
		Integer reward = Integer.parseInt(info[1]);
		this.addContinent(new Continent(info[0], reward));
		return true;
	}

	public boolean processTerritoryLine(String line) {
		String[] info = line.split(",");
		if(info.length < 5) {
			return false;
		}
		this.addTerritory(info);
		return true;
	}
	
	public ArrayList<Territory> getTerritories() {
		ArrayList<Territory> territoryList = new ArrayList<Territory>();
		
		for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
			for (java.util.Map.Entry<String, Territory> entry3 : entry2.getValue().territories.entrySet()) {
				Territory tmp = entry3.getValue();
				territoryList.add(tmp);
			}
		}
		
		return territoryList;
	}
}
