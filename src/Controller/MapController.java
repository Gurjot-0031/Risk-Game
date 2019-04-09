package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;

import Model.Continent;
import Model.Map;
import Model.Territory;

/**
 * This class is controller for map related events
 *
 * @author Team38
 *
 */
public class MapController implements Serializable {
    private Map map;
    private static MapController instance;

    /**
     * This is the private constructor
     */
    private MapController() {

    }

    /**
     * The get instance method for singleton class
     *
     * @return The singleton instance
     */
    public static MapController getInstance() {
        if (instance == null) {
            instance = new MapController();
        }
        return instance;
    }

    /**
     * This function creates a new map
     *
     * @param info
     *            Information received from view.
     */
    public void createMapFile(String info) {
        String[] infoA = info.split(",");
        String author = infoA[0];
        String path = infoA[1];
        this.map = new Map(path);
        this.map.setAuthor(author);
    }

    /**
     * Sets the map
     *
     * @param map
     *            the input map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Reads the map from file
     *
     * @param mapFile
     *            The input map file
     * @return Success or Failure
     */
    public boolean readMapFile(File mapFile) {
        try {
            this.map = new Map(mapFile.getAbsolutePath());
            this.map.readMapFile();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception Handled. Map Load Failed");
            return false;
        }
        return true;
    }

    /**
     * Gets the continent list
     *
     * @return Continent List
     */
    public ArrayList<String> getContinentsList() {
        ArrayList<String> rt = new ArrayList<String>();
        String[] continents = this.getContinentsArray();

        for (int i = 0; i < continents.length; i++) {
            rt.add(continents[i]);
        }

        return rt;
    }

    /**
     * Gets the territory list for a continent
     *
     * @param continent
     *            The input continent
     * @return The territory list
     */
    public ArrayList<String> getTerritoriesList(String continent) {
        ArrayList<String> rt = new ArrayList<String>();
        String[] territories = this.getTerritoriesArray(continent);

        for (int i = 0; i < territories.length; i++) {
            rt.add(territories[i]);
        }

        return rt;
    }

    /**
     * Gets the continent array
     *
     * @return The continent array
     */
    public String[] getContinentsArray() {
        if (this.map == null) {
            return null;
        }
        return this.map.getContinentsArray();
    }

    /**
     * Gets the territory array in specific continent
     *
     * @param selectedContinent
     *            The input continent
     * @return The territory array
     */
    public String[] getTerritoriesArray(String selectedContinent) {
        return this.map.getTerritoriesArray(selectedContinent);
    }

    /**
     * Adds the continent to map
     *
     * @param name
     *            Continent name
     * @param reward
     *            Continent reward
     */
    public void addContinent(String name, int reward) {
        String[] continents = map.getContinentsArray();
        for (int i = 0; i < continents.length; i++) {
            if (continents[i].equals(name) == true) {
                System.out.println("Continent already exists");
                return;
            }
        }
        map.addContinent(new Continent(name, reward));
    }

    /**
     * Changes the continent reward
     *
     * @param name
     *            Continent name
     * @param reward
     *            Continent's new reward
     */
    public void changeContinentReward(String name, int reward) {
        map.changeContinentReward(name, reward);
    }

    /**
     * Deletes the continent
     *
     * @param name
     *            Continent name
     */
    public void deleteContinent(String name) {
        map.deleteContinent(name);
    }

    /**
     * Gets the adjacent country names
     *
     * @param territory
     *            Input territory
     * @return The territory array
     */
    public String[] getAdjacents(String territory) {
        return map.getAdjacents(territory);
    }

    /**
     * Adds the territory
     *
     * @param info
     *            Information received from view.
     */
    public void addTerritory(String info) {
        this.map.addTerritory(info);
    }

    /**
     * Deletes the territory
     *
     * @param info
     *            Information received from view.
     */
    public void deleteTerritory(String info) {
        this.map.delTerritory(info);
    }

    /**
     * Adds the adjacent territory
     *
     * @param info
     *            Information received from view.
     */
    public void addAdjacent(String info) {
        this.map.addAdjacent(info);
    }

    /**
     * Deletes the adjacent territory
     *
     * @param info
     *            Information received from view.
     */
    public void deleteAdjacent(String info) {
        this.map.deleteAdjacent(info);
    }

    /**
     * Saves the map to file
     */
    public void saveMap() {
        this.map.saveMap();
    }
}
