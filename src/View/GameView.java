package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import Model.Map;
import Model.Territory;

public class GameView {
	private static GameView instance;
	private JFrame gameFrame;
	private GameView() {}
	
	public static GameView getInstance() {
		if(instance == null) {
			instance = new GameView();
		}
		return instance;
	}
	
	public void loadFrame() {
		if(gameFrame == null) {
			initFrame();
		}
		gameFrame.setVisible(true);
	}
	
	public void initFrame() {
		gameFrame = new JFrame("Game");
		gameFrame.setSize(1024, 768);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.getContentPane().setBackground(Color.WHITE);
		gameFrame.getContentPane().setLayout(null);
	}
	
	public void loadMap(Map map) {
		if(map != null) {
			System.out.println("Map not loaded correctly. Cannot be rendered");
		}
		ArrayList<Territory> territoryList = map.getTerritories();
		for(Territory territory : territoryList) {
			JButton btnTerritory = new JButton(territory.getName());
			btnTerritory.addActionListener(new territoryActionListener(territory));
			gameFrame.add(btnTerritory);
			btnTerritory.setBounds(territory.getX(), territory.getY(), 100, 15);
		}
	}
	
	class territoryActionListener implements ActionListener{
	    private final Territory territory;
	    
	    territoryActionListener (final Territory territory) {
	        super();
	        this.territory = territory;
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(this.territory.getName() + " clicked");
		}
	}
}
