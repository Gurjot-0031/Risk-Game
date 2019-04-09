package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.swing.*;

import Event.GameEvents;
import Event.IEvent;
import Model.*;
import View.CardExchangeView;
import View.DiceRollView;
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
	public int numOfGames;
	public int numOfMaps;
	public ArrayList<String> output = new ArrayList<>();
	/**
	 * This is the constructor
	 */
	private GameController() {}

	/**
	 * The method to get singleton instance
	 * @return The single instance
	 */
	public static GameController getInstance() {
		if (instance == null) {
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
			switch (Game.getInstance().getGamePhase()) {
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
					System.out.println(this.handleClick(clickedTerritory, Game.getInstance().getGamePhase()));
					break;
				case 3:
					System.out.println("Game in Attack Phase");
					Game.getInstance().setGameCycleCounter(Game.getInstance().getGameCycleCounter() - 1);
					System.out.println(Game.getInstance().getGameCycleCounter());
					//System.out.println("Select attacker territory");
					clickedTerritory = info[0];
					//String evntInfo = info[1];

					//System.out.println("Please select a territory to attack..");
					//if(clickedTerritory.equalsIgnoreCase("Continue Attack"))
					//    this.handleClick("Continue Attack",Game.getInstance().getGamePhase());
					//else
					System.out.println(this.handleClick(clickedTerritory, Game.getInstance().getGamePhase()));

					break;
				case 4:
					System.out.println("Game in Fortification Phase");
					clickedTerritory = info[0];
					System.out.println(this.handleClick(clickedTerritory, Game.getInstance().getGamePhase()));
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
	public void startNewGame(String[] info)
	{
		try {
			if(info.length < 2)
			{
				System.out.println("Invalid data received at HomeController:New Game");
				System.out.println("New Game cannot be started");
			}
			Game.getInstance().setNumPlayers(Integer.parseInt(info[0]));
			Map map = new Map(info[1]);
			if (map.readMapFile() == false)
			{
				System.out.println("Selected map is invalid.");
				return;
			}
			Game.getInstance().setMap(map);
			int initArmies = 0;
			switch (Game.getInstance().getNumPlayers())
			{
				case 2:
					initArmies = 100;
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
			pColors.add(new Color(0,0,255));
			pColors.add(new Color(0,255,0));
			pColors.add(new Color(255,255,0));
			pColors.add(new Color(255,0,255));
			pColors.add(new Color(0,255,255));

			//Here, player objects are created and passed onto add player method.
			//Game.getInstance().addPlayer() method adds it to the players aray list

            int j=0;
			//for (int i = 2; i < Integer.parseInt(info[0])+2; i+=2) {
            for (int i = 2; i < info.length; i+=2) {
				Game.getInstance().addPlayer(new Player(j, info[i], pColors.get(j), initArmies,info[i+1]));

				StrategyContextSetter strategy = new StrategyContextSetter();
                Game.getInstance().getPlayerById(j).setStrategy((strategy.getStrategyObject(Game.getInstance().getPlayerById(j).getPlayerType())));

				j++;
			}
			/*if(Game.getInstance().getCurrPlayer().getPlayerType().equalsIgnoreCase("HUMAN"))*/
			Game.getInstance().assignTerritoryToPlayers();

			/*else

				Game.getInstance().assignTerritoriesAndArmiesToPlayersAutomatically();*/
			//Game.getInstance().getpla
			PhaseView.getInstance().loadFrame();
			PhaseView.getInstance().loadMap(map);
			WorldDominationView.getInstance().initWorldDominationView();
		}
		catch (NumberFormatException e){
			System.out.println("Number of players invalid");
		}
		catch (Exception e) {
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
		Player currentPlayer = Game.getInstance().getCurrPlayer();
		if(info.equalsIgnoreCase("Continue_Button_Clicked")){
            if(Game.getInstance().getCurrPlayer().getPlayerType().equalsIgnoreCase("HUMAN"))
                return "Please click the territory owned by "+Game.getInstance().getCurrPlayer().getName();
        }
		/*else if (Game.getInstance().getGameMap().getTerritory(info) == null ||
				Game.getInstance().getGameMap().getTerritory(info).getOwner() == null) {
			return "Invalid Click";
		}*/

		switch (gamePhase)
		{
			case 1:		//Setup Phase
					if (Game.getInstance().getGameTurn() ==
							Game.getInstance().getGameMap().getTerritory(info).getOwner().getId())
					{


						if (Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).removeArmy(1) == true) {
							Game.getInstance().getGameMap().getTerritory(info).addArmy(1);
							setChanged();
							notifyObservers(this);
						}

						boolean nextPhase = true;
						for (int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
							if (Game.getInstance().getPlayerById(i).getArmies() != 0) {
								nextPhase = false;		//until all of the armies which belong to the players
								break;					//are not deployed, game cannot go to next phase.
							}
						}

						if (nextPhase == true)
						{
							Game.getInstance().setTurn(0);
							Game.getInstance().nextPhase();
							return "Next Phase";
						}
						Game.getInstance().nextTurn();
						while (Game.getInstance().getPlayerById(Game.getInstance().getGameTurn()).getArmies() == 0)
						{
							Game.getInstance().nextTurn();
							return "Event Processed";
						}
					}
                break;

			case 2:			//Reinforcement phase
				/*if(Game.getInstance().getPrevPhase()==4) {
					CardExchangeView.getInstance().loadCardExchangeView();

				}
				*//*else if(Game.getInstance().getGameTurn() ==
						Game.getInstance().getGameMap().getTerritory(info).getOwner().getId())*//*
				else {*/
                    switch (currentPlayer.getPlayerType()) {
                        case "HUMAN":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        default:
                            return Game.getInstance().getCurrPlayer().getStrategy().reinforce(info);
                        /*case "BENEVOLENT":
                            return Game.getInstance().getCurrPlayer().getStrategy().reinforce(info);*/
                        /*case "RANDOM":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        case "CHEATER":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        default:*/


                    /*}*/
                }
				//break;

			case 3: //Attack Phase...

				if(Game.getInstance().getAttackedObj() == null && Game.getInstance().getAttackerObj() == null)
					System.out.println("Proceed further.......");


                /*if(Game.getInstance().getAttackerObj()==null)
                    JOptionPane.showMessageDialog(null,"Please select attacker territory");
                else if(Game.getInstance().getAttackedObj()==null)
                    JOptionPane.showMessageDialog(null,"Please select the defender territory");*/

				/*if (info!="Continue Attack")
					return Game.getInstance().getCurrPlayer().
							attack(Game.getInstance().getAttackerObj(),Game.getInstance().getAttackedObj(),
									Game.getInstance().getNumOfDiceAttacker(),Game.getInstance().getNumOfDiceAttacked());
				else
					DiceRollView.getInstance().getDiceRollBtn().setVisible(true);*/



            switch (currentPlayer.getPlayerType()) {
                case "HUMAN":
                    return Game.getInstance().getCurrPlayer().attack(info);
                default:
                    return Game.getInstance().getCurrPlayer().getStrategy().attack(info);
                /*case "BENEVOLENT":
                    return Game.getInstance().getCurrPlayer().getStrategy().attack(info);*/
                        /*case "RANDOM":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        case "CHEATER":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        default:*/
            }

			case 4:
                switch (currentPlayer.getPlayerType()) {
                    case "HUMAN":
                        return Game.getInstance().getCurrPlayer().fortify(info);
                    default:
                        return Game.getInstance().getCurrPlayer().getStrategy().fortify(info);
                        /*case "BENEVOLENT":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        case "RANDOM":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        case "CHEATER":
                            return Game.getInstance().getCurrPlayer().reinforce(info);
                        default:*/
                }
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
	public String eventTriggered(IEvent event)
	{
		String[] input = event.getEventData().split(",");
		String temp="";
		for(int i=0; i<input.length-3;i++){
			temp = temp+input[i]+",";

		}
		GameEvents evnt = new GameEvents();
		evnt.setEventInfo(event.getEventInfo());
		evnt.setEventData(temp);



		System.out.println(input[input.length-3]);
		int turns = Integer.parseInt(input[input.length-3])*Integer.parseInt(input[0]);
		Game.getInstance().setGameCycleCounter(turns);

		this.numOfGames = Integer.parseInt(input[input.length-2]);
		this.numOfMaps = Integer.parseInt(input[input.length-1]);

		switch (evnt.getEventInfo())
		{
			case "New Game":
				this.gameLoop(evnt.getEventData().split(","));

				for(int countGame=0; countGame< 1; countGame++ )
				{

					WorldDominationView.getInstance().createChart(WorldDominationView.getInstance().getDt());
				    WorldDominationView.getInstance().getChartPanel().repaint();
					//reset resources
					//Game.getNewInstance();

					while(true){
						if(Game.getInstance().getCurrPlayer().getPlayerType().equalsIgnoreCase("HUMAN")){
							break;
						}
						else{
							//GameEvents evnt = new GameEvents();
							//evnt.setEventInfo("Territory Clicked");
							Random random = new Random();
							Territory tempTerritory;
							if(Game.getInstance().getGamePhase() == 1)
								tempTerritory = Game.getInstance().getCurrPlayer().getTerritoriesOwned().get(random.nextInt(Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()));
							else
								tempTerritory = Game.getInstance().getGameMap().getTerritories().get(0);

							String paraToPass = tempTerritory.getName() + "," + tempTerritory.getArmies();
							if(Game.getInstance().getGameCycleCounter() == 0) {
								System.out.println(Game.getInstance().getGameCycleCounter() + " turns completed..");

								if (Game.getInstance().isThereAGameWinner()) {
									output.add(Game.getInstance().getGameMap().getTerritories().get(0).getOwner().getPlayerType());
								}
									//output.add(Game.getInstance().getGameMap().getTerritories().get(0).getOwner().getName() + " WINNER");
								else {
									output.add(Game.getInstance().getGameWinner());

								}
								break;
							}
							else
								this.gameLoop(paraToPass.split(","));
						}
					}
				}

				//System.out.println("OP size:" +output.size());
				//System.out.println("G+M"+(numOfMaps+numOfGames));
				if(output.size() == (numOfGames*numOfMaps))
				{
					System.out.print("        ");
					for(int j=0; j<numOfGames;j++)
					{
						System.out.print("Game " + (j+1) +"    ");
					}
					System.out.println();
					int countLove=0;
					for(int i=0; i<numOfMaps; i++)
					{
						System.out.print("Map " + (i+1) +":  ");
						for(int j=0; j<numOfGames; j++) {
							System.out.print(output.get(countLove) + "  ");
							countLove++;
						}
						System.out.println();
					}

				}

				break;

			case "Territory Clicked":
				this.gameLoop(evnt.getEventData().split(","));
				while(true){
					if(Game.getInstance().getCurrPlayer().getPlayerType().equalsIgnoreCase("HUMAN")){
						break;
					}
					else{
						Random random = new Random();
						Territory tempTerritory = Game.getInstance().getCurrPlayer().getTerritoriesOwned().get(random.nextInt(Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()));

						String paraToPass = tempTerritory.getName() + "," + tempTerritory.getArmies();
						if(Game.getInstance().getGameCycleCounter()==0) {
                            System.out.println(Game.getInstance().getGameCycleCounter() + " turns completed..");
                            if (Game.getInstance().getCurrPlayer().isThereAWinner())
                                System.out.println(Game.getInstance().getGameMap().getTerritories().get(0).getOwner().getName() + " is the winner..");
                            else
                                System.out.println("Game ended in a Draw");
                            break;
                        }
						else
                            this.gameLoop(paraToPass.split(","));
					}
				}
				break;

			case "Continue Attack":
				//this.gameLoop(new String[]{event.getEventData().split(",")[0],event.getEventInfo()});
				setChanged();
				notifyObservers();
				//this.gameLoop(new String[]{"Continue Attack",""});
				this.gameLoop(evnt.getEventData().split(","));

				break;
			default:
				return "Invalid Event";
		}
		return "event processed";
	}
}
