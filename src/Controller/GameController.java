package Controller;

import javax.swing.*;

import Model.Game;
import Model.Map;

public class GameController {
	private static GameController instance;
	
	private GameController() {}
	public static GameController getInstance() {
		if(instance == null) {
			instance = new GameController();
		}
		return instance;
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
}
