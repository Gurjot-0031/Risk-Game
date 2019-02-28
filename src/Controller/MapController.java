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
	
	public void readMapFile(File mapFile) {
		BufferedReader reader;
		int flag = 0;
		try {
			reader = new BufferedReader(new FileReader(mapFile.getAbsolutePath()));
			String line = reader.readLine();
			while(line != null) {
				line = line.replaceAll("\\s", "").toLowerCase();
				if(line.length() < 2) {
					line = reader.readLine();
					continue;
				}
				
				if(flag == 0) {
					if(line.equals("[map]")) {
						this.map = new Map();
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
			}
			else {
				System.out.println("Map has been read.");
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public boolean processMapLine(String line) {
		String[] info = line.split("=");
		if(info[0].equals("author")) {
			this.map.setAuthor(info[1]);
		}
		else if(info[0].equals("wrap")) {
			this.map.setWrap(info[1]);
		}
		else if(info[0].equals("scroll")) {
			this.map.setScroll(info[1]);
		}
		else if(info[0].equals("warn")) {
			this.map.setWarn(info[1]);
		}
		
		return true;
	}
	
	public boolean processContinentLine(String line) {
		String[] info = line.split("=");
		if(info.length < 2) {
			return false;
		}
		Integer reward = Integer.parseInt(info[1]);
		this.map.addContinent(new Continent(info[0], reward));
		return true;
	}

	public boolean processTerritoryLine(String line) {
		String[] info = line.split(",");
		if(info.length < 5) {
			return false;
		}
		this.map.addTerritory(info);
		return true;
	}
	
	public void loadMap() {
		
	}
	
	public String[] getContinentsArray() {
		return this.map.getContinentsArray();
	}
	
	public String[] getTerritoriesArray(String selectedContinent) {
		return this.map.getTerritoriesArray(selectedContinent);
	}
}
