package Model;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
	private static Game instance;
	private int numPlayers;
	Map gameMap;
	
	ArrayList<Player> players;
	
	// 0 = Init, 1 = Setup, 2 = Reinforcement, 3 = Attack, 4 = Fortification
	private int gamePhase;
	private int gameTurn;
	
	public String fortification_source;
	
	private Game() {
		players = new ArrayList<Player>();
		gamePhase = 0;
		gameTurn = 0;
	}
	
	public Map getGameMap() {
		return this.gameMap;
	}
	
	public void setTurn(int turn) {
		this.gameTurn = turn;
	}
	
	public int getNumPlayers() {
		return this.numPlayers;
	}
	
	public Player getPlayerById(int id) {
		return this.players.get(id);
	}
	
	public String getCurrPlayerName() {
		return this.players.get(this.gameTurn).getName();
	}
	
	public int getCurrPlayerArmies() {
		return this.players.get(this.gameTurn).getArmies();
	}
	
	public int getGamePhase() {
		return this.gamePhase;
	}
	
	public String getGamePhaseDesc() {
		switch (this.gamePhase) {
		case 0:
			return "Game Phase: Initialisation";
		case 1:
			return "Game Phase: Setup";
		case 2:
			return "Game Phase: Reinforcement";
		case 3:
			return "Game Phase: Attack";
		case 4:
			return "Game Phase: Fortification";
		default:
			return "Invalid game Phase";
		}
	}
	
	public int getGameTurn() {
		return this.gameTurn;
	}
	
	public void nextPhase() {
		this.gamePhase += 1;
		if(this.gamePhase == 5) {
			this.gamePhase = 2;
		}
		
		if(this.gamePhase == 2) {
			for(int i = 0; i < this.numPlayers; i++) {
				int armies = this.calcReinforcementArmies(i);
				this.players.get(i).setArmies(armies);
			}
		}
		else if(this.gamePhase == 3) {
			System.out.println("Skipping Attack Phase");
			this.nextPhase();
		}
	}
	
	public void nextTurn() {
		this.gameTurn += 1;
		if(this.gameTurn == this.numPlayers) {
			this.gameTurn = 0;
			if(this.gamePhase == 4) {
				this.nextPhase();
			}
		}
	}
	
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
			territory.addArmy(1);
			this.players.get(ctr).removeArmy(1);
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
	
	public int calcReinforcementArmies(int id) {
		int reinforcment = 0;
		int numTerr = 0;

		for(Territory territory : this.getGameMap().getTerritories()) {
			if(territory.getOwner().getId() == id) {
				numTerr++;
			}
		}
		
		if((numTerr / 3) < 3) {
			reinforcment += 3;
		}
		else {
			reinforcment += numTerr / 3;
		}
		
		for(java.util.Map.Entry<String, Continent> entry : this.gameMap.continents.entrySet()) {
			if(entry.getValue().checkOwner(id) == true) {
				try {
					System.out.println("Player " + this.players.get(id).getName() + " owns the " + entry.getKey() + " and gains " +
						entry.getValue().getReward() + " extra armies");
				}
				catch(Exception e) {
					
				}
				reinforcment += entry.getValue().getReward();
			}
		}
		try {
			System.out.println("Player " + this.players.get(id).getName() + " receives total of " + reinforcment + " reinforcements.");
		}
		catch(Exception e) {
			
		}
		return reinforcment;
	}
}
