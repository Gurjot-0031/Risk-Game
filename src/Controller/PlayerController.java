package Controller;

import Model.Territory;

import java.util.List;

/**
 * This class acts as player controller
 * @author Team38
 *
 */
public class PlayerController {
    private String playerName;
    private List<Territory> territoriesOwned;

    /**
     * Gets the player name
     * @return player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player name
     * @param playerName Input player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the territories owned by this player
     * @return The territory list
     */
    public List<Territory> getTerritoriesOwned() {
        return territoriesOwned;
    }

    /**
     * Sets the territories owned to this player
     * @param territoriesOwned Input territories owned
     */
    public void setTerritoriesOwned(List<Territory> territoriesOwned) {
        this.territoriesOwned = territoriesOwned;
    }

    
    /**
     * This is the constructor
     */
    public PlayerController(){}


}
