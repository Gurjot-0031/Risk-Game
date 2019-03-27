package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is the map model
 *
 * @author Team38
 *
 */
public class Map {
    String author;
    String wrap;
    String scroll;
    String warn;
    String path;

    HashMap<String, Continent> continents;

    /**
     * The constructor
     *
     * @param path
     *            The input path of map
     */
    public Map(String path) {
        this.path = path;
        this.continents = new HashMap<String, Continent>();
    }

    /**
     * Sets the author name
     *
     * @param author
     *            The input author name
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the wrap
     *
     * @param wrap
     *            Input wrap
     */
    public void setWrap(String wrap) {
        this.wrap = wrap;
    }

    /**
     * Sets the scroll
     *
     * @param scroll
     *            Input scroll
     */
    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    /**
     * Sets the warn
     *
     * @param warn
     *            Input warn
     */
    public void setWarn(String warn) {
        this.warn = warn;
    }

    /**
     * Adds the continent to map
     *
     * @param continent
     *            The input continent
     */
    public void addContinent(Continent continent) {
        this.continents.put(continent.getName(), continent);
    }

    /**
     * Adds the territory to map
     *
     * @param info
     *            Information received from view.
     */
    public void addTerritory(String[] info) {
        ArrayList<String> adjacents = new ArrayList<String>();
        for (int ctr = 4; ctr < info.length; ctr++) {
            adjacents.add(info[ctr]);
        }
        String continent = info[3];
        int x = Integer.parseInt(info[1]);
        int y = Integer.parseInt(info[2]);
        this.continents.get(continent).addTerritory(new Territory(info[0], x, y, adjacents));
    }

    /**
     * Gets the continent array
     *
     * @return The continent array
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


    public ArrayList<Continent> getContinentsAsObjects() {
        ArrayList<Continent> continentsArray = new ArrayList<>();
        int ctr = 0;
        for (java.util.Map.Entry<String, Continent> entry : this.continents.entrySet()) {
            Continent continentName = entry.getValue();
            continentsArray.add(continentName) ;
            ctr++;
        }
        return continentsArray;
    }
    /**
     * Gets the territory array
     *
     * @param selectedContinent
     *            The input selected continent
     * @return The territories array
     */
    public String[] getTerritoriesArray(String selectedContinent) {
        int ctr = 0;
        Continent tmpContinent = this.continents.get(selectedContinent);
        if (tmpContinent == null) {
            System.out.println("Continent " + selectedContinent + " not found");
            return null;
        }
        String[] territoriesArray = new String[tmpContinent.territories.size()];

        for (java.util.Map.Entry<String, Territory> entry2 : tmpContinent.territories.entrySet()) {
            territoriesArray[ctr] = entry2.getKey();
            ctr++;
        }

        return territoriesArray;
    }

    /**
     * Gets the territory array
     *
     * @param selectedContinent
     *            The input selected continent
     * @return The territories array as objects
     */
    public ArrayList<Territory> getTerritoriesAsObjects(String selectedContinent) {
        int ctr = 0;
        Continent tmpContinent = this.continents.get(selectedContinent);
        if (tmpContinent == null) {
            System.out.println("Continent " + selectedContinent + " not found");
            return null;
        }
        ArrayList<Territory> territoriesObjects = new ArrayList<>();

        for (java.util.Map.Entry<String, Territory> entry2 : tmpContinent.territories.entrySet()) {
            territoriesObjects.add(entry2.getValue());
            ctr++;
        }

        return territoriesObjects;
    }

    /**
     * Changes the continent reward
     *
     * @param name
     *            continent name
     * @param reward
     *            continent reward
     */
    public void changeContinentReward(String name, int reward) {
        boolean found = false;
        for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
            if (entry2.getKey().equals(name)) {
                entry2.getValue().setReward(reward);
                found = true;
            }
        }
        if (found == false) {
            System.out.println("Could not find the continent. Cannot change reward");
        } else {
            System.out.println("Continent reward changed");
        }
    }

    /**
     * Deletes the continent
     *
     * @param name
     *            Input continent name
     */
    public void deleteContinent(String name) {
        Continent continent = null;
        for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
            if (entry2.getKey().equals(name)) {
                continent = entry2.getValue();
            }
        }
        if (continent == null) {
            System.out.println("Could not find the continent. Cannot delete continent");
        } else {
            if (continent.getNumTerritories() != 0) {
                System.out.println(
                        "Continent still has territories in it. Delete all territories from this continent first");
            } else {
                this.continents.remove(name);
                System.out.println("Continent " + name + " deleted successfully");
            }
        }
    }

    /**
     * Gets the adjacent countries
     *
     * @param territory
     *            The input territory
     * @return Territory array
     */
    public String[] getAdjacents(String territory) {
        for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
            if (entry2.getValue().getTerritory(territory) != null) {
                Territory tmpTerritory = entry2.getValue().getTerritory(territory);
                return tmpTerritory.adjacents.toArray(new String[0]);
            }
        }
        return null;
    }

    /**
     * Adds a territory
     *
     * @param info
     *            The Information received from view.
     * @return Success or failure
     */
    public boolean addTerritory(String info) {
        try {
            String[] infoA = info.split(",");
            String continent = infoA[0];
            String tName = infoA[1];
            Integer x = Integer.parseInt(infoA[2]);
            Integer y = Integer.parseInt(infoA[3]);
            ArrayList<String> adjacents = new ArrayList<String>();

            if (infoA.length < 5) {
                return false;
            }

            for (int i = 4; i < infoA.length; i++) {
                adjacents.add(infoA[i]);
            }

            Territory tmpTerritory = new Territory(tName, x, y, adjacents);
            this.continents.get(continent).addTerritory(tmpTerritory);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Deletes the territory
     *
     * @param info
     *            Information received from view.
     * @return Success or Failure
     */
    public boolean delTerritory(String info) {
        try {
            String[] infoA = info.split(",");
            String continent = infoA[0];
            String tName = infoA[1];
            Continent tmpContinent = this.continents.get(continent);
            boolean rt = tmpContinent.deleteTerritory(tName);
            if (rt == false) {
                System.out.println("Could not find the territory to be deleted");
                return rt;
            }
            for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
                entry2.getValue().handleTerritoryDeletion(tName);
            }
        } catch (Exception e) {
            System.out.println("Could not find the territory to be deleted");
            return false;
        }
        return true;
    }

    /**
     * Adds the adjacent
     *
     * @param info
     *            Information received from view.
     * @return Success or failure
     */
    public boolean addAdjacent(String info) {
        try {
            String[] infoA = info.split(",");
            if (infoA.length < 3) {
                System.out.println("Invalid information while adding adjacent");
                return false;
            }
            String continent = infoA[0];
            String tName = infoA[1];
            Continent tmpContinent = this.continents.get(continent);
            Territory tmpTerritory = tmpContinent.getTerritory(tName);
            tmpTerritory.addAdjacent(infoA[2]);
        } catch (Exception e) {
            System.out.println("Could not add adjacent");
            return false;
        }
        return true;
    }

    /**
     * Deletes the adjacent countries
     *
     * @param info
     *            Information received from view.
     * @return Success or failure
     */
    public boolean deleteAdjacent(String info) {
        try {
            String[] infoA = info.split(",");
            if (infoA.length < 3) {
                System.out.println("Invalid information while deleting adjacent");
                return false;
            }
            String continent = infoA[0];
            String tName = infoA[1];
            Continent tmpContinent = this.continents.get(continent);
            Territory tmpTerritory = tmpContinent.getTerritory(tName);
            tmpTerritory.removeAdjacent(infoA[2]);
        } catch (Exception e) {
            System.out.println("Could not add adjacent");
            return false;
        }
        return true;
    }

    /**
     * Checks for empty continents
     *
     * @return Success or failure
     */
    public boolean checkEmptyContinents() {
        for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
            if (entry2.getValue().getNumTerritories() < 1) {
                System.out.println("Empty Continent " + entry2.getKey());
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the territory from name
     *
     * @param name
     *            Input territory name
     * @return The territory
     */
    public Territory getTerritory(String name) {
        for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
            if (entry2.getValue().getTerritory(name) != null) {
                return entry2.getValue().getTerritory(name);
            }
        }
        return null;
    }

    /**
     * Checks adjacency between territories
     *
     * @return Success or failure
     */
    public boolean checkAdjacency() {
        for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
            Continent continent = entry2.getValue();

            for (java.util.Map.Entry<String, Territory> entry : continent.territories.entrySet()) {
                Territory territory = entry.getValue();
                if (territory.getAdjacents().size() < 1) {
                    System.out.println("A territory with no adjacents found: " + territory.getName());
                    return false;
                }

                ArrayList<String> adjacents = territory.getAdjacents();
                for (String adjacent : adjacents) {
                    if (this.getTerritory(adjacent) == null) {
                        System.out.println("The adjacent territory " + adjacent + " does not yet exist itself");
                        return false;
                    } else {
                        if (this.getTerritory(adjacent).checkAdjacent(territory.getName()) == false) {
                            System.out.println("Adjacency error between " + territory.getName() + " and " + adjacent);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Visits each and every territory recursively
     *
     * @param territory
     *            Input starting territory
     * @param visited
     *            The hashmap of visited territories
     * @param continent
     *            The continent or entire map if null
     * @return Success of failure
     */
    public boolean markVisited(String territory, HashMap<String, Boolean> visited, Continent continent) {
        if (visited.containsKey(territory)) {
            return true;
        }
        if (continent != null) {
            if (continent.territories.get(territory) != null) {
                visited.put(territory, true);
            }
        } else {
            visited.put(territory, true);
        }

        for (String adjacent : this.getTerritory(territory).getAdjacents()) {
            if (continent != null) {
                if (continent.territories.get(adjacent) != null) {
                    markVisited(adjacent, visited, continent);
                }
            } else {
                markVisited(adjacent, visited, continent);
            }
        }
        return true;
    }

    /**
     * Checks connectivity between territories
     *
     * @return Success or failure
     */
    public boolean checkConnectivity() {
        String anyTerritory = this.continents.entrySet().iterator().next().getValue().territories.entrySet().iterator()
                .next().getKey();

        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        markVisited(anyTerritory, visited, null);

        for (java.util.Map.Entry<String, Continent> entry2 : this.continents.entrySet()) {
            Continent continent = entry2.getValue();

            HashMap<String, Boolean> visitedC = new HashMap<String, Boolean>();
            markVisited(continent.territories.entrySet().iterator().next().getKey(), visitedC, continent);

            for (java.util.Map.Entry<String, Territory> entry : continent.territories.entrySet()) {
                if (visitedC.get(entry.getKey()) == null) {
                    System.out.println("Continent " + continent.name + " inside map is not connected graph");
                    return false;
                }
                if (visited.get(entry.getKey()) == null) {
                    System.out.println("The map is not a connected graph");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Saves map to file
     *
     * @return Success or failure
     */
    public boolean saveMapToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path));
            writer.write("[map]\n");
            if (this.author == null) {
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
                    for (String adjacent : tmp.getAdjacents()) {
                        line += "," + adjacent;
                    }
                    writer.write(line + "\n");
                }
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error while writing map to file. Map could not be saved.");
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * Saves map after doing verification
     *
     * @return Success or failure
     */
    public boolean saveMap() {
        if (checkEmptyContinents() != true) {
            return false;
        } else if (checkAdjacency() != true) {
            return false;
        } else if (checkConnectivity() != true) {
            return false;
        }

        if (saveMapToFile() != true) {
            return false;
        }
        System.out.println("Map Saved");
        return true;
    }

    /**
     * Reads the map from file
     *
     * @return Success or failure
     */
    public boolean readMapFile() {
        BufferedReader reader;
        int flag = 0;
        try {
            reader = new BufferedReader(new FileReader(this.path));
            String line = reader.readLine();
            while (line != null) {
                line = line.replaceAll("\\s", "").toLowerCase();
                if (line.length() < 2) {
                    line = reader.readLine();
                    continue;
                }

                if (flag == 0) {
                    if (line.equals("[map]")) {
                        line = reader.readLine();
                        continue;
                    } else if (line.equals("[continents]")) {
                        flag = 1;
                    }
                    processMapLine(line);
                } else if (flag == 1) {
                    if (line.equals("[territories]")) {
                        flag = 2;
                    }
                    processContinentLine(line);
                } else if (flag == 2) {
                    processTerritoryLine(line);
                }

                line = reader.readLine();
            }

            if (flag != 2) {
                System.out.println("Map not read correctly.");
                return false;
            } else {
                System.out.println("Map has been read.");
            }
        } catch (Exception e) {
            System.out.println("Map not read correctly.");
            System.out.println(e);
            e.printStackTrace();
        }

        if (checkEmptyContinents() != true) {
            return false;
        } else if (checkAdjacency() != true) {
            return false;
        } else if (checkConnectivity() != true) {
            return false;
        }

        return true;
    }

    /**
     * Process line for map configuration
     *
     * @param line
     *            Input line
     * @return Success or failure
     */
    public boolean processMapLine(String line) {
        String[] info = line.split("=");
        if (info[0].equals("author")) {
            this.setAuthor(info[1]);
        } else if (info[0].equals("wrap")) {
            this.setWrap(info[1]);
        } else if (info[0].equals("scroll")) {
            this.setScroll(info[1]);
        } else if (info[0].equals("warn")) {
            this.setWarn(info[1]);
        }

        return true;
    }

    /**
     * Processes a continent configuration line
     *
     * @param line
     *            Input line
     * @return Success or failure
     */
    public boolean processContinentLine(String line) {
        String[] info = line.split("=");
        if (info.length < 2) {
            return false;
        }
        Integer reward = Integer.parseInt(info[1]);
        this.addContinent(new Continent(info[0], reward));
        return true;
    }

    /**
     * Processes a territory configuration line
     *
     * @param line
     *            Input line
     * @return Success or failure
     */
    public boolean processTerritoryLine(String line) {
        String[] info = line.split(",");
        if (info.length < 5) {
            return false;
        }
        this.addTerritory(info);
        return true;
    }

    /**
     * Gets the territories in map
     *
     * @return The territory list
     */
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
