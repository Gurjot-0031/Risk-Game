package Model;

public class Game {
	private static Game instance;
	private int numPlayers;
	Map gameMap;
	
	private Game() {}
	
	public static Game getInstance() {
		if(instance == null) {
			instance = new Game();
		}
		return instance;
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
