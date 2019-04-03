package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class PlayerStrategy extends Observable implements Serializable {


    public static int playerCounter = 0;
    public String classType = "nonhuman";

    abstract public void reinforcement(Map gameMap);
    abstract public void placeArmyOnCountry(Map gameMap);
    abstract public void attack(Map gameMap, ArrayList<PlayerStrategy> players);
    abstract public void attackAllOut(Map gameMap, ArrayList<PlayerStrategy> players);
    abstract public int rollingDice(Territory attackingTerritory, Territory defendingTerritory);
    abstract public int rollingDiceAllOut(Territory attackingTerritory, Territory defendingTerritory);
    abstract public void initFortification(Map gameMap);
    abstract public void fortification(String countrySourceName, String countryDestinationName, int playerID);

    abstract public int getReinforcementArmyNum();
    abstract public void setReinforcementArmyNum(int reinforcementArmyNum);
    abstract public void updateReinforcementArmyNum(int reinforcementArmyNum);
    abstract public void increaseCountryNum();
    abstract public int getPlayerID();
    abstract public void addCountry(Territory country);
    abstract public void removeCountry(Territory country);
    abstract public ArrayList<Territory> getCountry();
    abstract public int getCountryNum();
    abstract public int getArmyNum();
    abstract public void updateArmyNum(int armyNumber);
    abstract public Card getCard();
    abstract public void setGameMap(Map gameMap);
    abstract public List<Continent> getListOfContinentsOwned();


    abstract public void phaseChanged(String message);
    abstract public String getMessage();
    abstract public float getPercentageOfCountriesOwned();
    abstract public ArrayList<String> getActions();
    abstract public void setPercentageOfCountriesOwned(float percentageOfCountriesOwned);
    abstract public void invokePlayerObserver();
    abstract public Map getGameMap();
}
