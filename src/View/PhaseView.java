package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Controller.GameController;
import Event.GameEvents;
import Model.Game;
import Model.Map;
import Model.Player;
import Model.Territory;

import static java.lang.Thread.sleep;

/**
 * View Class for Phase View
 * @author Team38
 *
 */
public class PhaseView extends MouseAdapter implements Observer {
	private static PhaseView instance;

    public JFrame getGameFrame() {
        return gameFrame;
    }

    private JFrame gameFrame;
	private PhaseView() {}
	
	private JLabel infoLog;
	private JLabel infoLog2;
	String gamePhase ;
	String curPlayer = Game.getInstance().getCurrPlayerName();
	int curPArmies = Game.getInstance().getCurrPlayerArmies();
	//int curPArmies2 ;
	boolean armiesChanged =false;
	boolean phaseChanged =false;
	String source[];

	/**
	 * Update the Observer list
	 * @param observable Observable Object
	 * @param o Instance of Game Class
	 */
	@Override
	public void update(Observable observable, Object o) {
		 if(o instanceof Game){
		 	Game obj = (Game) o;

		 	if(gamePhase!= obj.getGamePhaseDesc()){
		 		phaseChanged = true;
		 		gamePhase = obj.getGamePhaseDesc();
			}
		 	curPlayer = obj.getCurrPlayerName();
		 	//curPArmies = obj.getCurrPlayerArmies();

		 }

		 else if(o instanceof GameController || o instanceof Player){
			if(curPArmies != Game.getInstance().getCurrPlayerArmies())
		 		armiesChanged =true;
			 curPArmies = Game.getInstance().getCurrPlayerArmies();
		 	//curPlayer = Game.getInstance().getCurrPlayerName();
		 	//gamePhase = Game.getInstance().getGamePhaseDesc();

		 }
		 else if(o instanceof Object){
		 	source= o.toString().split(",");

		 }


	}

	/**
	 * Get the instance of Phase View
	 * @return PhaseView
	 */
	public static PhaseView getInstance() {
		if(instance == null) {
			instance = new PhaseView();
		}
		return instance;
	}


	/**
	 * Load the Frame
	 */
	public void loadFrame() {
		if(gameFrame == null) {
			initFrame();
		}
		gameFrame.setVisible(true);
	}

	/**
	 * Initialize the Frame
	 */
	public void initFrame() {
		gameFrame = new JFrame("Game");
		gameFrame.setSize(1566, 768);
		gameFrame.setSize(1566, 768);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.getContentPane().setBackground(Color.WHITE);
		gameFrame.getContentPane().setLayout(null);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(0, 620, 1024, 118);
		this.infoLog = new JLabel();
		this.infoLog.setBounds(10, 660, 1000, 108);

		this.infoLog2 = new JLabel();
		this.infoLog2.setBounds(10, 660, 1000, 108);

		//JTabbedPane tp = new JTabbedPane();
		//JToolTip tp = new JToolTip();
		//infoPanel.add(tp);
		//tp.setToolTipText("PHASE VIEW");
		//tp.setVisible(true);
		infoPanel.add(this.infoLog);
		infoPanel.add(this.infoLog2);
		gameFrame.add(infoPanel);

		/*JPanel gameDetailsPanel = new JPanel();
		gameDetailsPanel.setBounds(924, 0, 300, 768);
		gameFrame.add(gameDetailsPanel);*/
	}

	/**
	 * Load Map
	 * @param map Map
	 */
	public void loadMap(Map map) {
		if(map == null) {
			System.out.println("Map not loaded correctly. Cannot be rendered");
			return;
		}
		Game.getInstance().addObserver(PhaseView.getInstance());   //.........*****************************
		
		
		GameController.getInstance().addObserver(PhaseView.getInstance());
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

	/**
	 * View Class for Territory Mouse Hover
	 * @author Team38
	 *
	 */
	class territoryMouseHover extends MouseAdapter {
		private final Territory territory;

		territoryMouseHover(final Territory territory) {
			this.territory = territory;
		}

		public void mouseEntered(java.awt.event.MouseEvent evt) {
			String text = "<html><center><head><h1>PHASE VIEW</h1></head><center>Territory: " + territory.getName() + "<br/>Owned By: " +
					territory.getOwner().getName() + "<br/>Armies: " + territory.getArmies() + "<br/></html>";
	        infoLog.setText(text);
	    }

	    public void mouseExited(java.awt.event.MouseEvent evt) {
	        if(gamePhase!="Game Phase: Attack")
				infoLog.setText("<html><center><head><h1>PHASE VIEW</h1></head><center>Waiting for user action<br/>" + gamePhase + "<br/>Current Player: " +
						curPlayer + "<br/>Remaining Armies: " + curPArmies + "</html>");
	        else
				infoLog.setText("<html><center><head><h1>PHASE VIEW</h1></head><center>Waiting for user action<br/>" + gamePhase + "<br/>Current Player: " +
						curPlayer + "<br/></html>");
	    }
	}

	/**
	 * View Class for Territory Action Listener
	 * @author Team38
	 *
	 */
	class territoryActionListener implements ActionListener {
		private final Territory territory;


		/**
		 * Constructor
		 *
		 * @param territory Territory
		 */
		territoryActionListener(final Territory territory) {
			super();
			this.territory = territory;
		}

		/**
		 * Function to notice Clicks on Territory
		 *
		 * @param e Action Event
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			GameEvents objEvent = new GameEvents();
			objEvent.setEventInfo("");
			objEvent.setEventData("");
			//if(Game.getInstance().getGamePhase()==3) {
			//	Game.getInstance().setAttacker(territory.getName());
			//	objEvent.setEventInfo("Territory Clicked");
			//	objEvent.setEventData(territory.getName()+","+territory.getArmies());
			//} 

			infoLog2.setText("");
			if (Game.getInstance().getGamePhase() == 3) {
				if (Game.getInstance().getAttacker() == null) {

					if (Game.getInstance().getGameMap().getTerritory(territory.getName()).getArmies() >= 2) {

						if (Game.getInstance().getCurrPlayer().getId() == Game.getInstance().getGameMap().getTerritory(territory.getName()).getOwner().getId()) {
							Game.getInstance().setAttacker(territory.getName());
							System.out.println("Please select a target territory to attack..");

							if(Game.getInstance().getAttacked()==null)
								infoLog2.setText("Please select a target territory to attack..<br/>");
							objEvent.setEventInfo("Attacker Set");
							objEvent.setEventData(territory.getName() + "," + territory.getArmies());
						}
						else {
							System.out.println("Territory does not belong to current player..");
							infoLog2.setText("<html><body>Territory does not belong to current player..<br/></body></html>");
						}
					}
					else {
						System.out.println("Territory selected have less than 2 armies..");
						infoLog2.setText("<html><body>Territory selected have less than 2 armies..<br/></body></html>");
					}
				}
				else if (Game.getInstance().getAttacker() != null && Game.getInstance().getAttacked() == null) {

					if (territory.getOwner().getName() == Game.getInstance().getCurrPlayerName()) {
						System.out.println("Attack cannot be done to a player's own territory");
						System.out.println("Please select a valid territory to attack..");
						infoLog2.setText("<html><body>Attack cannot be done to a player's own territory<br/>Please select a valid territory to attack..<br/></body></html>");
						objEvent.setEventInfo("Attack Phase:Invalid attacked selected");
						objEvent.setEventData(territory.getName() + "," + territory.getArmies());
					}
					else {
						for (String adj : Game.getInstance().getGameMap().getAdjacents(Game.getInstance().getAttacker())) {
							if (territory.getName().equalsIgnoreCase(adj)) {
								Game.getInstance().setAttacked(territory.getName());
							}


						}
						if (Game.getInstance().getAttacked() == null) {
							System.out.println("Select an adjacent territory...");
							infoLog2.setText("<html><body>Select an adjacent territory...<br/></body></html>");
						}

						objEvent.setEventInfo("Attack Phase:attacked territory selected");
						objEvent.setEventData(territory.getName() + "," + territory.getArmies());
					}

				}
				//infoLog2.setText("</body></html>");
			}
			else {
				objEvent.setEventInfo("Territory Clicked");
				objEvent.setEventData(territory.getName() + "," + territory.getArmies());

			}
			GameController.getInstance().eventTriggered(objEvent);

			//if(armiesChanged==true){
			switch (gamePhase) {
				case "Game Phase: Setup":
					if (armiesChanged == true) {
						infoLog.setText("<html><center><b>PHASE VIEW<b><center><br/><br/>Game Phase : Setup<br/>"
								+ "1 army got deployed on " + this.territory.getName() + "</html>");
					} else
						infoLog.setText("<html><center><b>PHASE VIEW<b><center><br/><br/>No armies gets deployed as this territory does not belong to " + curPlayer + "</html>");
					break;
				case "Game Phase: Reinforcement":

					if (armiesChanged == true) {
						infoLog.setText("<html><center><b>PHASE VIEW<b><center><br/><br/>Game Phase : Reinforcement<br/>"
								+ "1 reinforcement deployed on " + this.territory.getName() + "</html>");
					} else
						infoLog.setText("<html><center><b>PHASE VIEW<b><center><br/><br/>No armies gets deployed as this territory does not belong to " + curPlayer + "</html>");
					break;
				case "Game Phase: Attack":
					//throws null pointer exception, unable to get the source values from the game controller.
					String temp;
					if (Game.getInstance().getAttacker() != null && Game.getInstance().getAttacked() != null) {
						temp = "<html><center><b>PHASE VIEW<b><center><br/><br/>Game Phase : Attack<br/>Attacker territory: " + Game.getInstance().getAttacker()
								+ "<br/>Attacked territory: " + Game.getInstance().getAttacked() + "</html>";
					} else
						temp = "<html><center><b>PHASE VIEW<b><center><br/><br/>Game Phase : Attack<br/></html>";
					infoLog.setText(temp);
					//source[2]+" fortifications sent from "+source[0]+" to "+source[1]);
					break;
				case "Game Phase: Fortification":
					//throws null pointer exception, unable to get the source values from the game controller.

					infoLog.setText("<html><center><b>PHASE VIEW<b><center><br/><br/>Game Phase : Fortification<br/></html>");
					//source[2]+" fortifications sent from "+source[0]+" to "+source[1]);
					break;
				default:
					infoLog.setText("Invalid");

					armiesChanged = false;


					if (phaseChanged == true && curPArmies == 0) {
						infoLog.setText("<html><head><h1><center><b>PHASE VIEW<b><center></h1><head><br/><br/></html>");
						phaseChanged = false;
					}

			}
		}
	}
}
