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

public class mapValidation {

	String validMap;
	String invalidEmptyContinentMap;
	String invalidWrongAdjacencyMap;
	String invalidNotConnectedMap;
	
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidMap() {
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

}
