package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import Model.Continent;
import Model.Map;
import Model.Territory;

public class MapController {
	private Map map;
	private static MapController instance;
	
	private MapController() {}
	
	public static MapController getInstance() {
		if(instance == null) {
			instance = new MapController();
		}
		return instance;
	}
	
	public void createMapFile(String info) {
		String[] infoA = info.split(",");
		String author = infoA[0];
		String path = infoA[1];
		this.map = new Map(path);
		this.map.setAuthor(author);
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public boolean readMapFile(File mapFile) {
		try {
			this.map = new Map(mapFile.getAbsolutePath());
			this.map.readMapFile();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception Handled. Map Load Failed");
			return false;
		}
		return true;
	}
	
	public void loadMap() {
		
	}
	
	public ArrayList<String> getContinentsList() {
		ArrayList<String> rt = new ArrayList<String>();
		String[] continents = this.getContinentsArray();
		
		for(int i = 0; i < continents.length; i++) {
			rt.add(continents[i]);
		}
		
		return rt;
	}
	
	public ArrayList<String> getTerritoriesList(String continent) {
		ArrayList<String> rt = new ArrayList<String>();
		String[] territories = this.getTerritoriesArray(continent);
		
		for(int i = 0; i < territories.length; i++) {
			rt.add(territories[i]);
		}
		
		return rt;
	}
	
	
	public String[] getContinentsArray() {
		if(this.map == null) {
			return null;
		}
		return this.map.getContinentsArray();
	}
	
	public String[] getTerritoriesArray(String selectedContinent) {
		return this.map.getTerritoriesArray(selectedContinent);
	}
	
	public void addContinent(String name, int reward) {
		String continents[] = map.getContinentsArray();
		for(int i = 0; i < continents.length; i++) {
			if(continents[i].equals(name) == true) {
				System.out.println("Continent already exists");
				return;
			}
		}
		map.addContinent(new Continent(name, reward));
	}
	
	public void changeContinentReward(String name, int reward) {
		map.changeContinentReward(name, reward);
	}
	
	public void deleteContinent(String name) {
		map.deleteContinent(name);
	}
	
	public String[] getAdjacents(String territory) {
		return map.getAdjacents(territory);
	}
	
	public void addTerritory(String info) {
		this.map.addTerritory(info);
	}
	
	public void deleteTerritory(String info) {
		this.map.delTerritory(info);
	}
	
	public void addAdjacent(String info) {
		this.map.addAdjacent(info);
	}
	
	public void deleteAdjacent(String info) {
		this.map.deleteAdjacent(info);
	}
	
	public void saveMap() {
		this.map.saveMap();
	}
}
