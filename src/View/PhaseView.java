package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.GameController;
import Event.GameEvents;
import Model.Game;
import Model.Map;
import Model.Territory;

public class PhaseView extends MouseAdapter implements Observer {
	private static PhaseView instance;
	private JFrame gameFrame;
	private PhaseView() {}
	
	private JLabel infoLog;
	String gamePhase ;
	String curPlayer ;
	int curPArmies ;

	@Override
	public void update(Observable observable, Object o) {
		 if(o instanceof Game){
		 	Game obj = (Game) o;

		 	gamePhase = ((Game) o).getGamePhaseDesc();
		 	curPlayer = ((Game) o).getCurrPlayerName();
		 	curPArmies = ((Game) o).getCurrPlayerArmies();
		 }


	}


	public static PhaseView getInstance() {
		if(instance == null) {
			instance = new PhaseView();
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
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(0, 650, 1024, 118);
		this.infoLog = new JLabel();
		this.infoLog.setBounds(10, 660, 1000, 108);
		infoPanel.add(this.infoLog);
		gameFrame.add(infoPanel);
	}
	
	public void loadMap(Map map) {
		if(map == null) {
			System.out.println("Map not loaded correctly. Cannot be rendered");
			return;
		}
		Game.getInstance().addObserver(PhaseView.getInstance());   //.........*****************************
		ArrayList<Territory> territoryList = map.getTerritories();
		for(Territory territory : territoryList) {
			JButton btnTerritory = new JButton(territory.getName());
			btnTerritory.addActionListener(new territoryActionListener(territory));
			btnTerritory.addMouseListener(new territoryMouseHover(territory));
			gameFrame.add(btnTerritory);
			btnTerritory.setBackground(territory.getOwner().getColor());
			btnTerritory.setBounds(territory.getX(), territory.getY(), 100, 15);
		}
	}
	
	class territoryMouseHover extends MouseAdapter {
		private final Territory territory;

		territoryMouseHover(final Territory territory) {
			this.territory = territory;
		}

		public void mouseEntered(java.awt.event.MouseEvent evt) {
			String text = "<html>Territory: " + territory.getName() + "<br/>Owned By: " +
					territory.getOwner().getName() + "<br/>Armies: " + territory.getArmies() + "<br/></html>";
	        infoLog.setText(text);
	    }

	    public void mouseExited(java.awt.event.MouseEvent evt) {
	        infoLog.setText("<html>Waiting for user action<br/>" + gamePhase + "<br/>Current Player: " +
	        		curPlayer + "<br/>Remaining Armies: " + curPArmies + "</html>");
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
			GameEvents objEvent = new GameEvents();
			objEvent.setEventInfo("Territory Clicked");
			objEvent.setEventData(territory.getName()+","+territory.getArmies());
			GameController.getInstance().eventTriggered(objEvent);
			//if(this.territory.getOwner().getName()==Game.getInstance().getCurrPlayerName())
			//	infoLog.setText("1 army got deployed on"+territory.getName());
			if(this.territory.getOwner().getName()==
					Game.getInstance().getPlayerById(Game.getInstance().getCurrPlayer().getId()-1).getName()){
				infoLog.setText("1 army got deployed on"+territory.getName());
			}
			else
				infoLog.setText("");
		}
	}
}
