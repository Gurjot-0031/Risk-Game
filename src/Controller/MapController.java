package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Model.Map;

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
					continue;
				}
				
				if(flag == 0) {
					if(line.equals("[map]")) {
						continue;
					}
					else if(line.equals("[continents]")) {
						flag = 1;
					}
					
				}
				else if(flag == 1) {
					if(line.equals("[territories]")) {
						flag = 2;
					}
				}
				else if(flag == 2) {
					
				}
				
				line = reader.readLine();
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void loadMap() {
		
	}
}
