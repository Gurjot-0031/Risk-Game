package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 * This class is game model
 * It is a singleton class, as at a game run, there should be only one Game model object created.
 * @author Team38
 *
 */
public class Game extends Observable {
	private static Game instance;
	private int numPlayers;
	Map gameMap;

	public ArrayList<Player> getPlayers() {
		return players;
	}

	ArrayList<Player> players;
	
	// 0 = Init, 1 = Setup, 2 = Reinforcement, 3 = Attack, 4 = Fortification
	private int gamePhase;
	private int gameTurn;
	
	public String fortification_source;
	
	/**
	 * The constructor
	 */
	private Game() {
		players = new ArrayList<Player>();
		gamePhase = 0;
		gameTurn = 0;
	}


	/**
	 * Gets the game map
	 * @return The game map
	 */
	public Map getGameMap() {
		return this.gameMap;
	}
	
	/**
	 * Sets the current turn
	 * @param turn The input turn
	 */
	public void setTurn(int turn) {
		this.gameTurn = turn;
	}
	
	/**
	 * Gets number of players
	 * @return number of players
	 */
	public int getNumPlayers() {
		return this.numPlayers;
	}
	
	/**
	 * Gets the player by id
	 * @param id The input player id
	 * @return returns the Player
	 */
	public Player getPlayerById(int id) {
		return this.players.get(id);
	}
	
	/**
	 * Gets the currrent player name
	 * @return The current player name
	 */
	public String getCurrPlayerName() {
		return this.players.get(this.gameTurn).getName();
	}
	public Player getCurrPlayer() {
		return this.players.get(this.gameTurn);
	}
	
	/**
	 * Gets the current player armies
	 * @return The current player armies
	 */
	public int getCurrPlayerArmies() {
		return this.players.get(this.gameTurn).getArmies();
	}
	
	/**
	 * Gets the current game phase
	 * @return Gamephase
	 */
	public int getGamePhase() {
		return this.gamePhase;
	}
	
	/**
	 * Gets the game phase description
	 * @return The game phase description
	 */
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
	
	/**
	 * Gets game turn
	 * @return The game turn
	 */
	public int getGameTurn() {
		return this.gameTurn;
	}


	//public void addObserver(Observer o){
	//	this.addObserver(o);
	//}


	/**
	 * Changes the game phase
	 */
	public void nextPhase() {
		this.gamePhase += 1;
		if(this.gamePhase == 5) {
			this.gamePhase = 2;
		}
		
		if(this.gamePhase == 2) {		//Reinforcement phase
			for(int i = 0; i < this.numPlayers; i++) {
				int armies = this.calcReinforcementArmies(i);
				this.players.get(i).setArmies(armies);
				System.out.println("Setup Phase ends..");
				System.out.println("Reinforcement Phase starts..");
			}
		}
		else if(this.gamePhase == 3) {
			System.out.println("Please select the attacker territory..");
			this.nextPhase();
		}
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Changes the game turn
	 */
	public void nextTurn() {
		this.gameTurn += 1;
		if(this.gameTurn == this.numPlayers) {
			this.gameTurn = 0;
			if(this.gamePhase == 4) {
				this.nextPhase();
			}
		}
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Gets the singleton game instance
	 * @return The singleton instance
	 */
	public static Game getInstance() {
		if(instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	/**
	 * Assigns territory to players in a round robin fashion.
	 */
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
	
	/**
	 * Adds player to game
	 * @param player The input player
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	/**
	 * Deletes the player from game
	 * @param player Input player
	 */
	public void deletePlayer(String player) {
		for(Player tmpPlayer : this.players) {
			if(tmpPlayer.getName().equals(player)) {
				this.players.remove(tmpPlayer);
			}
		}
	}
	
	/**
	 * Checks the game configuration
	 * @return Success or failure
	 */
	public boolean checkGameConfig() {
		if(this.numPlayers < 2) {
			return false;
		}
		if(this.gameMap == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sets number of players
	 * @param numPlayers Input number of players
	 */
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	/**
	 * Sets the game map
	 * @param gameMap The game map
	 */
	public void setMap(Map gameMap) {
		this.gameMap = gameMap;
	}
	
	/**
	 * Calculates the reinforcement armies, it is equal to 3 if number of territories owned by the player
	 * is less than 9, otherwise, it is equal to (number of territories owned by player)/3.
	 * Additionaly, it checks whether a player owns all the territories in a particular continent or not
	 * If Yes, it raises the reinforcement armies for that player by the factor of "Control value" of that
	 * particular continent.
	 * @param id The input player id
	 * @return The number of reinforcement armies.
	 */
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
			//Iterates through the list of continents, to check whether a continent has an owner or not.
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
