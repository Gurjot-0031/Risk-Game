package Model;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
	private static Game instance;
	private int numPlayers;
	Map gameMap;
	
	ArrayList<Player> players;
	
	private Game() {}
	
	public static Game getInstance() {
		if(instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	public void assignTerritoryToPlayers() {
		ArrayList<Territory> territories = this.gameMap.getTerritories();
		Collections.shuffle(territories);
		int ctr = 0;
		
		for(Territory territory : territories) {
			territory.setOwner(this.players.get(ctr));
			ctr++;
			if(ctr == this.players.size()) {
				ctr = 0;
			}
		}
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void deletePlayer(String player) {
		for(Player tmpPlayer : this.players) {
			if(tmpPlayer.getName().equals(player)) {
				this.players.remove(tmpPlayer);
			}
		}
	}
	
	public boolean checkGameConfig() {
		if(this.numPlayers < 2) {
			return false;
		}
		if(this.gameMap == null) {
			return false;
		}
		
		return true;
	}
	
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public void setMap(Map gameMap) {
		this.gameMap = gameMap;
	}
}
