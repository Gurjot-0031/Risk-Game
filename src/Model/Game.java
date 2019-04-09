package Model;

import View.PhaseView;

import java.util.*;

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

	/**
	 * Get the Counter for number of games in tournament
	 * @return the game counter value
	 */
	public int getGameCycleCounter() {
		return gameCycleCounter;
	}

	/**
	 * Set the game counter
	 * @param gameCycleCounter integer value
	 */
	public void setGameCycleCounter(int gameCycleCounter) {
		this.gameCycleCounter = gameCycleCounter;
	}

	public int gameCycleCounter = 1;

	/**
	 * Check if the current mode is all out
	 * @return true or false
	 */
	public boolean isAlloutMode() {
		return alloutMode;
	}

	/**
	 * Set the game to allout mode
	 * @param alloutMode
	 */
	public void setAlloutMode(boolean alloutMode) {
		this.alloutMode = alloutMode;
	}

	private boolean alloutMode = false;

	/**
	 * Get the previous phase
	 * @return integer value
	 */
	public int getPrevPhase() {
		return prevPhase;
	}

	/**
	 * Set the previous phase
	 * @param prevPhase
	 */
	public void setPrevPhase(int prevPhase) {
		this.prevPhase = prevPhase;
	}

	private int prevPhase = 1;

	/**
	 * This method stores the players in form of Player object
	 * @return arraylist
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	ArrayList<Player> players;

	/**
	 * Set the game phase
	 * @param gamePhase
	 */
	public void setGamePhase(int gamePhase) {
		this.gamePhase = gamePhase;
	}

	// 0 = Init, 1 = Setup, 2 = Reinforcement, 3 = Attack, 4 = Fortification
	private int gamePhase;
	private int gameTurn;
	private String attacker = null;
	private String attacked;
	private Territory attackerObj;

	/**
	 * Set the attacker territory object
	 * @param attackerObj
	 */
	public void setAttackerObj(Territory attackerObj) {
		this.attackerObj = attackerObj;
	}

	/**
	 * Set the defender territory object
	 * @param attackedObj
	 */
	public void setAttackedObj(Territory attackedObj) {
		this.attackedObj = attackedObj;
	}

	/**
	 * Get the attacker territory object
	 * @return object of type territory
	 */
	public Territory getAttackerObj() {
		return attackerObj;
	}

	/**
	 * Get the defender territory object
	 * @return object of type territory
	 */
	public Territory getAttackedObj() {
		return attackedObj;
	}

	private Territory attackedObj = null;

	/**
	 * Get the number of dice used by attacker
	 * @return integer value
	 */
	public int getNumOfDiceAttacker() {
		return numOfDiceAttacker;
	}

	/**
	 * Get the number of dice used by defender
	 * @return integer value
	 */
	public int getNumOfDiceAttacked() {
		return numOfDiceAttacked;
	}


	/**
	 * Set the number of dice used by attacker
	 * @return
	 */
	public void setNumOfDiceAttacker(int numOfDiceAttacker) {
		this.numOfDiceAttacker = numOfDiceAttacker;
	}

	/**
	 * Set the number of dice used by defender
	 * @return
	 */
	public void setNumOfDiceAttacked(int numOfDiceAttacked) {
		this.numOfDiceAttacked = numOfDiceAttacked;
	}

	private int numOfDiceAttacker =-1;
	private int numOfDiceAttacked =-1;
	public String fortification_source;
	public String fortification_destination;

	/**
	 * The constructor
	 */
	private Game() {
		setChanged();
		notifyObservers();
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
	 * Gets the attacker territory
	 * @return The attacker territory
	 */
	public String getAttacker() {
		return this.attacker;
	}

	/**
	 * Gets the defender territory
	 * @return The defender territory
	 */
	public String getAttacked() {
		return this.attacked;
	}

	/**
	 * Set the current attacker territory
	 * @param attacker name of the attacking territory
	 */
	public void setAttacker(String attacker) {
		this.attacker = attacker;
	}

	/**
	 * Set the current attacked/defending territory
	 * @param attacked name of the defending territory
	 */
	public void setAttacked(String attacked) {
		this.attacked = attacked;
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

	/**
	 * Gets the entire object for current player
	 * @return Player type object in every gameturn
	 */
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
			this.setPrevPhase(4);
			if(Game.getInstance().getCurrPlayer().getId()==0) {
				this.gameTurn = 0;
				this.gamePhase = 2;
				//this.setGameCycleCounter(Game.getInstance().getGameCycleCounter() + 1);
			}
			else
				this.gamePhase = 3;

			this.setAttackerObj(null);
			this.setAttackedObj(null);
			this.setAttacker(null);
			this.setAttacked(null);
			this.fortification_destination = null;
			this.fortification_source = null;



		}

		if(this.gamePhase == 2) {		//Reinforcement phase
			if(this.prevPhase != 4)
				System.out.println("Setup Phase ends..");
			System.out.println("Reinforcement Phase starts..");
			for(int i = 0; i < this.numPlayers; i++) {
				int armies = this.calcReinforcementArmies(i);
				this.players.get(i).setArmies(armies);

			}
		}
		else if(this.gamePhase == 3) {
			PhaseView.getInstance().getResetAttackerBtn().setVisible(true);
			this.setPrevPhase(2);
			if(Game.getInstance().getCurrPlayer().getPlayerType().equalsIgnoreCase("HUMAN"))
                System.out.println("***********");
			else
				System.out.println("STRATEGY APPLIED");

		}
        else if(this.gamePhase == 4) {
            this.setPrevPhase(3);
            System.out.println("Fortification Phase");

        }

		setChanged();
		notifyObservers(this);
	}

	/**
	 * Changes the game turn
	 */
	public void nextTurn() {
		//System.out.println("Previous Player"+ Game.getInstance().getCurrPlayer().getName());
		if(this.gameTurn == (this.numPlayers-1) && Game.getInstance().getGamePhase()==4)
			this.nextPhase();
		else{
			this.gameTurn += 1;
			if(this.gameTurn == this.numPlayers) {
				this.gameTurn = 0;
				//	System.out.println("Current Player"+ Game.getInstance().getCurrPlayer().getName());
				if(this.gamePhase == 4) {
					this.nextPhase();
				}
			}
		}

		//if(this.getGameTurn()!=this.numPlayers)
		//	System.out.println("Current Player"+ Game.getInstance().getCurrPlayer().getName());
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

	public static Game getNewInstance() {
		instance = null;
		return getInstance();
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
	 *
	 * This method is used to assign armies to players in setup phase during tournament mode
	 */
	public void assignArmyToPlayersAutomatically() {
		ArrayList<Territory> territories = this.gameMap.getTerritories();
		Collections.shuffle(territories);
		Iterator<Player> iterator = Game.getInstance().players.iterator();

		while(iterator.hasNext()){
			Player tempPlayer = iterator.next();
			Random random = new Random();

			if(!tempPlayer.getPlayerType().equalsIgnoreCase("HUMAN")){
				//int avg = Game.getInstance().getCurrPlayerArmies()/Game.getInstance().getCurrPlayer().getTerritoriesOwned().size();
				if(Game.getInstance().getCurrPlayer().armies!=0){

					Territory tempTerr = Game.getInstance().getCurrPlayer().getTerritoriesOwned().get(random.nextInt(Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()));
					tempTerr.addArmy(1);
					Game.getInstance().getCurrPlayer().removeArmy(1);
				}
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

	public boolean isThereAGameWinner(){
		Player player = Game.getInstance().getGameMap().getTerritories().iterator().next().owner;
		for(Territory territory:Game.getInstance().getGameMap().getTerritories()){
			if(!territory.getOwner().getName().equalsIgnoreCase(player.getName()))
				return false;
		}
		return true;
	}


	public String getGameWinner() {
		int[] playerTerritoriesCount = new int[this.players.size()];
		for(Player player:this.players){
			playerTerritoriesCount[player.getId()] = player.getTerritoriesOwned().size();
		}

		int totalTerr = this.gameMap.getTerritories().size();
		for(int i=0;i<playerTerritoriesCount.length;i++){
			if(playerTerritoriesCount[i] >= totalTerr*0.75)
				return this.players.get(i).getPlayerType();
		}
		return "DRAW";
	}
}
