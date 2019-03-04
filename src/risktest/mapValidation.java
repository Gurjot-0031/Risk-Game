package risktest;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Controller.MapController;
import Model.Game;
import Model.Player;
import Model.Territory;

public class mapValidation {

	String validMap;
	String invalidEmptyContinentMap;
	String invalidWrongAdjacencyMap;
	String invalidNotConnectedMap;
	String invalidMapFile;
	String reinforcementMapOne;
	String reinforcementMapTwo;
	
	@Before
	public void setUp() throws Exception {
		validMap = "[map]\n"
				+ "author=test\n"
				+ "[continents]\n"
				+ "a=2\n"
				+ "b=3\n"
				+ "[territories]\n"
				+ "a1,10,10,a,a2,b1\n"
				+ "a2,10,10,a,a1\n"
				+ "b1,10,10,b,b2,a1\n"
				+ "b2,10,10,b,b1\n";
		
		invalidEmptyContinentMap = "[map]\n"
				+ "author=test\n"
				+ "[continents]\n"
				+ "a=2\n"
				+ "b=3\n"
				+ "c=2\n"
				+ "[territories]\n"
				+ "a1,10,10,a,a2\n"
				+ "a2,10,10,a,a1\n"
				+ "b1,10,10,b,b2,a1\n"
				+ "b2,10,10,b,b1\n";
		
		invalidWrongAdjacencyMap = "[map]\n"
				+ "author=test\n"
				+ "[continents]\n"
				+ "a=2\n"
				+ "b=3\n"
				+ "[territories]\n"
				+ "a1,10,10,a,a2\n"
				+ "a2,10,10,a,a1\n"
				+ "b1,10,10,b,b2,a1\n"
				+ "b2,10,10,b,b1\n";
		
		invalidNotConnectedMap = "[map]\n"
				+ "author=test\n"
				+ "[continents]\n"
				+ "a=2\n"
				+ "b=3\n"
				+ "[territories]\n"
				+ "a1,10,10,a,a2\n"
				+ "a2,10,10,a,a1\n"
				+ "b1,10,10,b,b2\n"
				+ "b2,10,10,b,b1\n";
		
		invalidMapFile = "[map]\n"
				+ "author=test\n"
				+ "a=2\n"
				+ "b=3\n"
				+ "a1,10,10,a,a2\n"
				+ "a2,10,10,a,a1\n"
				+ "b1,10,10,b,b2\n"
				+ "b2,10,10,b,b1\n";
		
		reinforcementMapOne = "[map]\n"
				+ "author=test\n"
				+ "[continents]\n"
				+ "a=2\n"
				+ "[territories]\n"
				+ "a1,10,10,a,a2\n"
				+ "a2,10,10,a,a1\n";
		
		reinforcementMapTwo = "[map]\n"
				+ "author=test\n"
				+ "[continents]\n"
				+ "a=2\n"
				+ "b=2\n"
				+ "[territories]\n"
				+ "a1,10,10,a,a2,b1\n"
				+ "a2,10,10,a,a1,a3\n"
				+ "a3,10,10,a,a2,a4\n"
				+ "a4,10,10,a,a3\n"
				+ "b1,10,10,b,b2,a1\n"
				+ "b2,10,10,b,b1\n";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidMap() {
		System.out.println("Test Case testValidMap");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(validMap);
            writer.close();
            Model.Map map = new Model.Map(file.getAbsolutePath());
            assertTrue(map.readMapFile());
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void testInvalidEmptyContinentMap() {
		System.out.println("Test Case testInvalidEmptyContinentMap");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(invalidEmptyContinentMap);
            writer.close();
            Model.Map map = new Model.Map(file.getAbsolutePath());
            assertFalse(map.readMapFile());
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void testInvalidWrongAdjacencyMap() {
		System.out.println("Test Case testInvalidWrongAdjacencyMap");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(invalidWrongAdjacencyMap);
            writer.close();
            Model.Map map = new Model.Map(file.getAbsolutePath());
            assertFalse(map.readMapFile());
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void testInvalidNotConnectedMap() {
		System.out.println("Test Case testInvalidNotConnectedMap");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(invalidNotConnectedMap);
            writer.close();
            Model.Map map = new Model.Map(file.getAbsolutePath());
            assertFalse(map.readMapFile());
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}

	@Test
	public void testReadInvalidMapFile() {
		System.out.println("Test Case testReadInvalidMapFile");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(invalidMapFile);
            writer.close();
            Model.Map map = new Model.Map(file.getAbsolutePath());
            assertFalse(map.readMapFile());
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void testReinforcement() {
		System.out.println("Test Case testReinforcement (without continent reward)");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(reinforcementMapOne);
            writer.close();
            Model.Map map = new Model.Map(file.getAbsolutePath());
            map.readMapFile();
            Game.getInstance().setMap(map);
            Game.getInstance().setNumPlayers(2);
            Game.getInstance().setTurn(0);
            
            for(int i = 0; i < 2; i++) {
				Game.getInstance().addPlayer(new Player(i, "", null, 0));
			}
            
            Game.getInstance().assignTerritoryToPlayers();
            int reinforce = Game.getInstance().calcReinforcementArmies(0);
            
            assertTrue(reinforce == 3);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void testReinforcementTwo() {
		System.out.println("Test Case testReinforcementTwo (with continent reward)");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(reinforcementMapTwo);
            writer.close();
            Model.Map map = new Model.Map(file.getAbsolutePath());
            map.readMapFile();
            Game.getInstance().setMap(map);
            Game.getInstance().setNumPlayers(2);
            Game.getInstance().setTurn(0);
            int ctr = 0;
            for(Territory t : Game.getInstance().getGameMap().getTerritories()) {
            	if(ctr == 0) {
            		t.setOwner(new Player(0, "", null, 0));
            		ctr++;
            	}
            	else {
            		t.setOwner(new Player(1, "", null, 0));
            	}
            }
            int reinforce = Game.getInstance().calcReinforcementArmies(1);
            
            assertTrue(reinforce == 5);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void testMapEditorOne() {
		System.out.println("Test Case testMapEditorOne - Add Continent");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(validMap);
            writer.close();
            
            MapController.getInstance().readMapFile(file);
            MapController.getInstance().addContinent("testc", 2);
            assertTrue(MapController.getInstance().getContinentsList().contains("testc"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void testMapEditorTwo() {
		System.out.println("Test Case testMapEditorOne - Add Territory");
		File file = new File("tmpMap.txt");
        try {
        	if(file.exists()) {
        		file.delete();
        	}
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(validMap);
            writer.close();
            
            MapController.getInstance().readMapFile(file);
            MapController.getInstance().addTerritory("a,testt,10,10,a1");
            assertTrue(MapController.getInstance().getTerritoriesList("a").contains("testt"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
}
