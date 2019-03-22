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
			//System.out.println("Select attacker territory");
			clickedTerritory = info[0];
			
			//System.out.println("Please select a territory to attack..");
			this.handleClick(clickedTerritory, Game.getInstance().getGamePhase());
			break;
		case 4:
			System.out.println("Game in Fortification Phase");
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
				initArmies = 8;
				break;
			case 3:
				initArmies = 10;
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

			//Here, player objects are created and passed onto add player method.
			//Game.getInstance().addPlayer() method adds it to the players aray list
			for(int i = 0; i < Integer.parseInt(info[0]); i++) {
				Game.getInstance().addPlayer(new Player(i, info[i + 2], pColors.get(i), initArmies));
			}
			Game.getInstance().assignTerritoryToPlayers();
			PhaseView.getInstance().loadFrame();
			PhaseView.getInstance().loadMap(map);
			WorldDominationView.getInstance().initWorldDominationView();
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
			case 1:		//Setup Phase
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
					return "Event Processed";
				}
				break;
				}
				
			case 2:			//Reinforcement phase
				if(Game.getInstance().getGameTurn() ==
						Game.getInstance().getGameMap().getTerritory(info).getOwner().getId()) {
							return Game.getInstance().getCurrPlayer().reinforce(info);
				}
				break;
				
			case 3: //Attack Phase...


					System.out.println("Attacker:"+Game.getInstance().getAttacker());
					System.out.println("Attacked:"+Game.getInstance().getAttacked());

					//while(Game.getInstance().getAttackerObj().ContinueAttacking())
						return Game.getInstance().getCurrPlayer().
                                attack(Game.getInstance().getAttackerObj(),Game.getInstance().getAttackedObj(),
                                        Game.getInstance().getNumOfDiceAttacker(),Game.getInstance().getNumOfDiceAttacked());

					//if(!Game.getInstance().getAttackerObj().ContinueAttacking())
					//	return "Attack Discontinued by the attacker";

				//break;
			case 4:
				return Game.getInstance().getCurrPlayer().fortify(info);
				//break;
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
		case "Attack Phase:attacked territory selected":
			this.gameLoop(event.getEventData().split(","));
			break;
			
		default:
			return "Invalid Event";	
		}
		return "event processed";
	}
}
