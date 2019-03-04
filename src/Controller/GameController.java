package Controller;

import java.awt.Color;
import java.util.ArrayList;

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
		String clickedTerritory = null;
		switch(Game.getInstance().getGamePhase()) {
		case 0:
			this.startNewGame(info);
			Game.getInstance().nextPhase();
			break;
		case 1:
			System.out.println("Game in Setup Phase");
			clickedTerritory = info[0];
			this.handleClick(clickedTerritory, Game.getInstance().getGamePhase());
			break;
		case 2:
			System.out.println("Game in Reinforcement Phase");
			clickedTerritory = info[0];
			this.handleClick(clickedTerritory, Game.getInstance().getGamePhase());
			break;
		case 3:
			System.out.println("Game in Attack Phase");
		case 4:
			System.out.println("Game in Fortification Phase");
			clickedTerritory = info[0];
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
			int initArmies = 0;
			switch(Game.getInstance().getNumPlayers()) {
			case 2:
				//initArmies = 40;
				initArmies = 22;
				break;
			case 3:
				initArmies = 35;
				break;
			case 4:
				initArmies = 30;
				break;
			case 5:
				initArmies = 25;
				break;
			case 6:
				initArmies = 20;
				break;
			}
			
			ArrayList<Color> pColors = new ArrayList<Color>();
			pColors.add(new Color(255,0,0));
			pColors.add(new Color(0,255,0));
			pColors.add(new Color(0,0,255));
			pColors.add(new Color(255,255,0));
			pColors.add(new Color(255,0,255));
			pColors.add(new Color(0,255,255));
			
			for(int i = 0; i < Integer.parseInt(info[0]); i++) {
				Game.getInstance().addPlayer(new Player(i, info[i + 2], pColors.get(i), initArmies));
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
					
					if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).removeArmy(1) == true) {
						Game.getInstance().getGameMap().getTerritory(info).addArmy(1);
					}
					
					boolean nextPhase = true;
					for(int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
						if(Game.getInstance().getPlayerById(i).getArmies() != 0) {
							nextPhase = false;
							break;
						}
					}
					
					if(nextPhase == true) {
						Game.getInstance().setTurn(0);
						Game.getInstance().nextPhase();
						return "Next Phase";
					}
					Game.getInstance().nextTurn();
					while(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() == 0) {
						Game.getInstance().nextTurn();
					}
					return "Event Processed";
				}
				break;
			case 2:
				if(Game.getInstance().getGameTurn() == 
						Game.getInstance().getGameMap().getTerritory(info).getOwner().getId()) {
					
					if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).removeArmy(1) == true) {
						Game.getInstance().getGameMap().getTerritory(info).addArmy(1);
					}
					
					
					if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() == 0) {
						Game.getInstance().nextTurn();
					}
					
					if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() > 0) {
						return "Event Processed";
					}
					
					boolean nextPhase = true;
					for(int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
						if(Game.getInstance().getPlayerById(i).getArmies() > 0) {
							Game.getInstance().setTurn(i);
							nextPhase = false;
						}
					}
					if(nextPhase == true) {
						Game.getInstance().setTurn(0);
						Game.getInstance().nextPhase();
						return "Next Phase";
					}

					return "Event Processed";
				}
				break;
				
			case 3:
			case 4:
				boolean fortificationPossible = false;
				for(Territory t : Game.getInstance().getGameMap().getTerritories()) {
					if(t.getOwner().getId() == Game.getInstance().getGameTurn() &&
							t.getArmies() > 1) {
						for(String adjacent : t.getAdjacents()) {
							Territory adjT = Game.getInstance().getGameMap().getTerritory(adjacent);
							if(adjT.getOwner() == t.getOwner()) {
								fortificationPossible = true;
							}
						}
					}
				}
				
				if(fortificationPossible == false) {
					System.out.println("Fortification move not possible for Player " + Game.getInstance().getCurrPlayerName());
					Game.getInstance().nextTurn();
					return "Processed";
				}
				
				Territory tmpTerritory = Game.getInstance().getGameMap().getTerritory(info);
				if(tmpTerritory == null || tmpTerritory.getOwner().getId() != Game.getInstance().getGameTurn()) {
					return "Territory does not belong to current player";
				}
				
				if(Game.getInstance().fortification_source == null) {
					for(String adjacent : tmpTerritory.getAdjacents()) {
						Territory adjT = Game.getInstance().getGameMap().getTerritory(adjacent);
						if(adjT != null && adjT.getOwner() == tmpTerritory.getOwner() && tmpTerritory.getArmies() > 1) {
							Game.getInstance().fortification_source = info;
							System.out.println("Please select target territory to fortify");
							return "Fortification Source Event Processed";
						}
						else {
							System.out.println("Cannot fortify from territory: " + info);
							return "Fortification Source Event Processed";
						}
					}
					System.out.println("No adjacent territory can be fortified from " + info + ". Please select other territory");
				}
				else {
					if(tmpTerritory.getAdjacents().contains(Game.getInstance().fortification_source)) {
						Territory sourceT = Game.getInstance().getGameMap().getTerritory(Game.getInstance().fortification_source);
						int toMove = 0;
						while(true) {
							try {
								String num = JOptionPane.showInputDialog("How many armies to move? Max possible is: " + sourceT.getArmies());
								toMove = Integer.parseInt(num);
								if(toMove > sourceT.getArmies() || toMove < 0) {
									System.out.println("Should be positive and Max possible move allowed is : " + sourceT.getArmies());
									continue;
								}
								break;
							}
							catch(NumberFormatException e) {
								System.out.println("Exception Handled");
								System.out.println("Only numeric value >= 0 is allowed. Try again");
							}
						}
						sourceT.removeArmies(toMove);
						tmpTerritory.addArmy(toMove);
						System.out.println(toMove + " armies moved from " + sourceT.getName() + " to " + tmpTerritory.getName());
						Game.getInstance().fortification_source = null;
						Game.getInstance().nextTurn();
					}
					else {
						System.out.println("Selected target: " + tmpTerritory.getName() + 
								" is not adjacent to selected source: " + Game.getInstance().fortification_source + 
								". Please try again.");
					}					
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
