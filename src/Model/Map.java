package Model;

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
}
