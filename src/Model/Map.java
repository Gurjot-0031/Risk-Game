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
		    String continentName = entry.getKey();
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
}
