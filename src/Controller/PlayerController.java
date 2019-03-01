package Controller;

import Model.Territory;

import java.util.List;

public class PlayerController {
    private String playerName;
    private List<Territory> territoriesOwned;
    
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Territory> getTerritoriesOwned() {
        return territoriesOwned;
    }

    public void setTerritoriesOwned(List<Territory> territoriesOwned) {
        this.territoriesOwned = territoriesOwned;
    }


    public PlayerController(){}


}
