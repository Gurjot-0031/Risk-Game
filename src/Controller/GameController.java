package Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.*;

import Event.IEvent;
import Model.Game;
import Model.Map;
import Model.Player;
import Model.Territory;
import View.PhaseView;
import View.WorldDominationView;

/**
 * This class is the game controller and receives events from game view,
 * and sends them to the Game Model
 * @author Team38
 *
 */
public class GameController extends Observable {
	private static GameController instance;
	
	/**
	 * This is the constructor
	 */
	private GameController() {}
	
	/**
	 * The method to get singleton instance
	 * @return The single instance
	 */
	public static GameController getInstance() {
		if(instance == null) {
			instance = new GameController();
		}
		return instance;
	}



	/**
	 * The game loop, that calls necessary functions based on game phase.
	 * @param info The info to be processed
	 */
	public void gameLoop(String[] info) {
		String clickedTerritory = null;
		switch(Game.getInstance().getGamePhase()) {
		case 0:
			this.startNewGame(info);
			Game.getInstance().nextPhase();
			break;
		case 1:
			System.out.println("Game in Setup Game");
			clickedTerritory = info[0];
			this.handleClick(clickedTerritory, Game.getInstance().getGamePhase());
			break;
		case 2:
			System.out.println("Game in Reinforcement Game");
			clickedTerritory = info[0];
			this.handleClick(clickedTerritory, Game.getInstance().getGamePhase());
			break;
		case 3:
			System.out.println("Game in Attack Game");
		case 4:
			System.out.println("Game in Fortification Game");
			clickedTerritory = info[0];
			this.handleClick(clickedTerritory, Game.getInstance().getGamePhase());
			break;
		default:
			System.out.println("Invalid Game Game. Critical Error");
			return;
		}
	}
	
	/**
	 * This functions starts the new game
	 * @param info Information received from view
	 */
	public void startNewGame(String[] info) {
		try {
			if(info.length < 2) {
				System.out.println("Invalid data received at HomeController:New Game");
				System.out.println("New Game cannot be started");
			}
			Game.getInstance().setNumPlayers(Integer.parseInt(info[0]));
			Map map = new Map(info[1]);
			if(map.readMapFile() == false) {
				System.out.println("Selected map is invalid.");
				return;
			}
			Game.getInstance().setMap(map);
			int initArmies = 0;
			switch(Game.getInstance().getNumPlayers()) {
			case 2:
				initArmies = 40;
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
			PhaseView.getInstance().loadFrame();
			PhaseView.getInstance().loadMap(map);
			WorldDominationView.getInstance().initWorldDomnationView();
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
	
	/**
	 * This function handles the clicks made by user on territories
	 * @param info Information received from view
	 * @param gamePhase The current gamephase
	 * @return The processing information
	 */
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
						setChanged();
						notifyObservers(this);
					}
					
					boolean nextPhase = true;
					for(int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
						if(Game.getInstance().getPlayerById(i).getArmies() != 0) {
							nextPhase = false;		//until all of the armies which belong to the players
							break;					//are not deployed, game cannot go to next phase.
						}
					}
					
					if(nextPhase == true) {
						Game.getInstance().setTurn(0);
						Game.getInstance().nextPhase();
						return "Next Game";
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
						setChanged();
						notifyObservers(this);
					}
					
					
					if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() == 0) {
						Game.getInstance().nextTurn();
						//If a player has no army to deploy, the next player's turn comes
					}
					
					if(Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() > 0) {
						return "Event Processed";
					}
					
					boolean nextPhase = true;
					for(int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
						if(Game.getInstance().getPlayerById(i).getArmies() > 0) {
							Game.getInstance().setTurn(i);
							//Each Player deploys the reinforcement armies
							//one by one until all his reinforcement armies are deployed
							// then, the turn of next player comes.
							nextPhase = false;
							break;				//change
						}
					}
					if(nextPhase == true) {
						Game.getInstance().setTurn(0);
						Game.getInstance().nextPhase();
						return "Next Game";
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
					}
					System.out.println("No adjacent territory can be fortified from " + info + ". Please select other territory");
				}
				else {
					if(tmpTerritory.getAdjacents().contains(Game.getInstance().fortification_source)) {
						Territory sourceT = Game.getInstance().getGameMap().getTerritory(Game.getInstance().fortification_source);
						int toMove = 0;
						while(true) {
							try {
								String num = JOptionPane.showInputDialog("How many armies to move? Max possible is: " + (sourceT.getArmies()-1));
								toMove = Integer.parseInt(num);
								if(toMove > sourceT.getArmies() || toMove < 0) {
									System.out.println("Should be positive and Max possible move allowed is : " + (sourceT.getArmies()-1));
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
						Object obj = new Object();
						obj = sourceT.getName()+","+tmpTerritory.getName()+","+toMove;
						setChanged();
						notifyObservers(obj);
					}
					else {
						System.out.println("Selected target: " + tmpTerritory.getName() + 
								" is not adjacent to selected source: " + Game.getInstance().fortification_source +
								". Please try again.");
					}					
				}
				break;
			default:
				System.out.println("Invalid Game");
				return "Invalid Game";
		}
		return "Should not reach here";
	}
	
	/**
	 * This function handles events received from view
	 * @param event The event received
	 * @return The processed result
	 */
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
