package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Controller.GameController;
import Event.GameEvents;
import Model.Game;
import Model.Map;
import Model.Player;
import Model.Territory;
import com.sun.org.apache.bcel.internal.generic.INEG;

import static java.lang.Thread.sleep;

/**
 * View Class for Phase View
 * @author Team38
 *
 */
public class PhaseView extends MouseAdapter implements Observer {
	private static PhaseView instance;

	String[] source;

	private JFrame gameFrame;

	private PhaseView() {

	}

	/**
	 * get the updated button property of territories on the game screen
	 * @return
	 */
	public HashMap<String, JButton> getBtnTerritories() {
		return btnTerritories;
	}

	/**
	 * set the button property of territories on the game screen
	 * @param btnTerritories territory button
	 */
	public void setBtnTerritories(HashMap<String, JButton> btnTerritories) {
		this.btnTerritories = btnTerritories;
	}

	private HashMap<String,JButton> btnTerritories = new HashMap<>();
	private JLabel infoLog;
	//public String playerBeforeClick = Game.getInstance().getCurrPlayerName();;
	public JButton getResetAttackerBtn() {
		return resetAttackerBtn;
	}

	public void setResetAttackerBtn(JButton resetAttackerBtn) {
		this.resetAttackerBtn = resetAttackerBtn;
	}

	private JButton resetAttackerBtn = new JButton("Reset Attacker");

    public JLabel getInfoLog() {
        return infoLog;
    }

    public JLabel getInfoLog2() {
        return infoLog2;
    }

    private JLabel infoLog2;
	String gamePhase =Game.getInstance().getGamePhaseDesc();
	String curPlayer = Game.getInstance().getCurrPlayerName();
	int curPArmies = Game.getInstance().getCurrPlayerArmies();

	public boolean isArmiesChanged() {
		return armiesChanged;
	}

	public void setArmiesChanged(boolean armiesChanged) {
		this.armiesChanged = armiesChanged;
	}

	//int curPArmies2 ;
	boolean armiesChanged =false;
	boolean phaseChanged =false;

	/**
	 * Get the frame in Game screen
	 * @return
	 */
    public JFrame getGameFrame() {

		return gameFrame;
	}

	/**
	 * Update the Observer list
	 * @param observable Observable Object
	 * @param o Instance of Game Class
	 */
	@Override
	public void update(Observable observable, Object o) {
		gamePhase = Game.getInstance().getGamePhaseDesc();
		if(o instanceof Game){
			gamePhase = Game.getInstance().getGamePhaseDesc();
			curPlayer = Game.getInstance().getCurrPlayerName();
			//curPArmies = obj.getCurrPlayerArmies();

		}

		else if(o instanceof GameController){
				armiesChanged =true;
				//armiesChanged = false;
			gamePhase = Game.getInstance().getGamePhaseDesc();
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
			JButton temp = new JButton(territory.getName());
			temp.addActionListener(new territoryActionListener(territory));
			temp.addMouseListener(new territoryMouseHover(territory));
			btnTerritories.put(territory.getName(),temp);

			//btnTerritories.addActionListener(new territoryActionListener(territory));
			//btnTerritory.addMouseListener(new territoryMouseHover(territory));
			gameFrame.add(temp);
			temp.setBackground(territory.getOwner().getColor());
			temp.setBounds(territory.getX(), territory.getY(), 100, 15);
		}
		gameFrame.add(resetAttackerBtn);
		resetAttackerBtn.setBounds(5,5,80,20);
		resetAttackerBtn.setVisible(false);
		resetAttackerBtn.addActionListener(new Action() {
			@Override
			public Object getValue(String s) {
				return null;
			}

			@Override
			public void putValue(String s, Object o) {

			}

			@Override
			public void setEnabled(boolean b) {

			}

			@Override
			public boolean isEnabled() {
				return false;
			}

			@Override
			public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

			}

			@Override
			public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

			}

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Game.getInstance().setAttackerObj(null);
				Game.getInstance().setAttacker(null);
				System.out.println("Please select the new attacker..");
				JOptionPane.showMessageDialog(null,"Please select new attacker territory..");
			}
		});
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
			String text = "<html><center><head><h2>PHASE VIEW</h2></head><center>Territory: &nbsp;&nbsp;&nbsp;" + territory.getName() + "<br/>" +
					"Owned By: &nbsp;&nbsp;&nbsp;" +territory.getOwner().getName()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Continent: "+ territory.getParentContinent().getName()+"<br/>"+
					"Adjacents: &nbsp;&nbsp;&nbsp;"+territory.getAdjacents()+"<br/>"+
					"Armies: &nbsp;&nbsp;&nbsp;" +territory.getArmies() + "<br/></html>";
			infoLog.setText(text);
		}

        public void mouseExited(java.awt.event.MouseEvent evt) {

			gamePhase = Game.getInstance().getGamePhaseDesc();
            if (gamePhase!="Game Phase: Attack")
                infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center>Waiting for user action<br/>" + gamePhase + "<br/>Current Player: " +
                        curPlayer + "<br/>Remaining Armies: " + curPArmies + "</html>");
            else if (gamePhase=="Game Phase: Attack") {
                if (Game.getInstance().getAttacker() == null)
                    infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center>Please select the attacker<br/>" + gamePhase + "<br/>Current Player: " +
                            curPlayer + "<br/>Remaining Armies: " + curPArmies + "</html>");
                else
                    infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center>Attacker: " + Game.getInstance().getAttacker() + "<br/>" + gamePhase + "<br/>Current Player: " +
                            curPlayer + "<br/>Remaining Armies: " + curPArmies + "</html>");
                if (Game.getInstance().getAttacker()!=null && Game.getInstance().getAttacked() == null)
                    infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center>Please select the Defender<br/>" + gamePhase + "<br/>Current Player: " +
                            curPlayer + "<br/>Remaining Armies: " + curPArmies + "</html>");
                else if(Game.getInstance().getAttacker()!=null && Game.getInstance().getAttacked() != null)
                    infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center>Attacker: " + Game.getInstance().getAttacked() + "<br/>" + gamePhase + "<br/>Current Player: " +
                            curPlayer + "<br/>Remaining Armies: " + curPArmies + "</html>");
            }

            else
                infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center>Waiting for user action<br/>" + gamePhase + "<br/>Current Player: " +
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

            infoLog2.setText("");
			objEvent.setEventInfo("Territory Clicked");
			objEvent.setEventData(territory.getName() + "," + territory.getArmies());


			GameController.getInstance().eventTriggered(objEvent);

			//if(armiesChanged==true){
			switch (gamePhase) {
				case "Game Phase: Setup":
					if (armiesChanged == true ) {
						infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center><br/>Game Phase : Setup<br/>"
								+ "1 army got deployed on " + this.territory.getName() + "</html>");

					} else
						infoLog.setText("<html><center><b>PHASE VIEW<b><center><br/><br/>No armies gets deployed as this territory does not belong to " + curPlayer + "</html>");
					break;
				case "Game Phase: Reinforcement":

					if (armiesChanged == true && curPlayer == Game.getInstance().getGameMap().getTerritory(territory.getName()).getOwner().getName() ) {
						infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center><br/>Game Phase : Reinforcement<br/>"
								+ "1 reinforcement deployed on " + this.territory.getName() + "</html>");
					} else
						infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center><br/>No armies gets deployed as this territory does not belong to " + curPlayer + "</html>");
					break;
				case "Game Phase: Attack":
					//throws null pointer exception, unable to get the source values from the game controller.
					String temp;
					if (Game.getInstance().getAttacker() != null && Game.getInstance().getAttacked() != null) {
						temp = "<html><center><head><h2>PHASE VIEW</h2></head><center><br/>Game Phase : Attack<br/>Attacker territory: " + Game.getInstance().getAttacker()
								+ "<br/>Attacked territory: " + Game.getInstance().getAttacked() + "</html>";
					} else
						temp = "<html><center><head><h2>PHASE VIEW</h2></head><center><br/>Game Phase : Attack<br/></html>";
					infoLog.setText(temp);
					//source[2]+" fortifications sent from "+source[0]+" to "+source[1]);
					break;
				case "Game Phase: Fortification":
					//throws null pointer exception, unable to get the source values from the game controller.

                    infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center><br/>Game Phase : Fortification<br/></html>");
                    //source[2]+" fortifications sent from "+source[0]+" to "+source[1]);
                    break;
                default:
                    infoLog.setText("Invalid");

                    armiesChanged = false;


                    if (phaseChanged == true && curPArmies == 0) {
                        infoLog.setText("<html><center><head><h2>PHASE VIEW</h2></head><center><br/><br/></html>");
                        phaseChanged = false;
                    }
					//playerBeforeClick = curPlayer;

			}
		}
	}
}
