package Controller;

import javax.swing.*;

import Event.IEvent;
import Model.Game;
import Model.Map;
import Model.Player;
import Model.Territory;
import View.GameView;

public class GameController {
	private static GameController instance;
	
	private GameController() {}
	
	public static GameController getInstance() {
		if(instance == null) {
			instance = new GameController();
		}
		return instance;
	}
	
	public void gameLoop(String[] info) {
		switch(Game.getInstance().getGamePhase()) {
		case 0:
			this.startNewGame(info);
			Game.getInstance().nextPhase();
			break;
		case 1:
			String clickedTerritory = info[0];
			this.handleClick(clickedTerritory, Game.getInstance().getGamePhase());
			break;
		default:
			System.out.println("Invalid Game Phase. Critical Error");
			return;
		}
	}
	
	public void setupArmyPlacement() {
		int currPlayer = Game.getInstance().getGameTurn();
		
	}
	
	public void startNewGame(String[] info) {
		try {
			if(info.length < 2) {
				System.out.println("Invalid data received at HomeController:New Game");
				System.out.println("New Game cannot be started");
			}
			Game.getInstance().setNumPlayers(Integer.parseInt(info[0]));
			Map map = new Map(info[1]);
			Game.getInstance().setMap(map);
			for(int i = 0; i < Integer.parseInt(info[0]); i++) {
				Game.getInstance().addPlayer(new Player(i, "No Name", null));
			}
			Game.getInstance().assignTerritoryToPlayers();
			GameView.getInstance().loadFrame();
			GameView.getInstance().loadMap(map);
		}
		catch(NumberFormatException e) {
			System.out.println("Number of players invalid");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception Handled. New Game not started");
			return;
		}
		
		System.out.println("Starting New Game");
	}
	
	public String handleClick(String info, int gamePhase) {
		if(Game.getInstance().getGameMap().getTerritory(info) == null || 
				Game.getInstance().getGameMap().getTerritory(info).getOwner() == null) {
			return "Invalid Click";
		}
		switch (gamePhase) {
			case 1:
				if(Game.getInstance().getGameTurn() == 
						Game.getInstance().getGameMap().getTerritory(info).getOwner().getId()) {
					Game.getInstance().getGameMap().getTerritory(info).addArmy(1);
					Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).removeArmy(1);
					
					boolean nextPhase = true;
					for(int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
						if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() != 0) {
							nextPhase = false;
						}
					}
					
					if(nextPhase == true) {
						Game.getInstance().setTurn(0);
						Game.getInstance().nextPhase();
						return "Next Phase";
					}
					
					while(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() != 0) {
						Game.getInstance().nextTurn();
					}
					return "Event Processed";
				}
				break;
			default:
				System.out.println("Invalid Phase");
				return "Invalid Phase";
		}
		return "Should not reach here";
	}
	
	public String eventTriggered(IEvent event) {
		switch(event.getEventInfo()) {
		case "New Game":
			this.gameLoop(event.getEventData().split(","));
			break;
		case "Territory Clicked":
			this.gameLoop(event.getEventData().split(","));
			break;
		default:
			return "Invalid Event";	
		}
		return "event processed";
	}
}
