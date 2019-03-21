package View;

import Controller.GameController;
import Controller.MapController;
import Model.Continent;
import Model.Game;
import Model.Player;
import Model.Territory;
import View.PhaseView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class WorldDominationView implements Observer {

	private static WorldDominationView instance;
	JLabel worldDominationViewLabel = new JLabel();
	ArrayList<Territory> territoriesList;
	ArrayList<Territory> continentTerritory;
	String[] continentList;

	public void initWorldDominationView() {

		worldDominationViewLabel.setBounds(1024, 0, 500, 768);
		JFrame frameFromPhase = PhaseView.getInstance().getGameFrame();
		JPanel worldDominationViewPanel = new JPanel();
		worldDominationViewPanel.setBounds(1024, 0, 500, 768);
		worldDominationViewPanel.add(worldDominationViewLabel);
		frameFromPhase.add(worldDominationViewPanel);

		Game.getInstance().addObserver(this);

	}

	@Override
	public void update(Observable observable, Object o) {
		if (o instanceof Game) {
			String label = "<html><head><h1>Map Domination</h1></head><br/><body><center> ";

			territoriesList = Game.getInstance().getGameMap().getTerritories();

			for (int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
				int tempTerritoryCount = 0;
				float percentage = 0;

				for (Territory territory : territoriesList) {
					if (territory.getOwner() == Game.getInstance().getPlayerById(i)) {
						tempTerritoryCount += 1;
					}
				}
				percentage = (float) 100 * tempTerritoryCount / Game.getInstance().getGameMap().getTerritories().size();

				label = label + Game.getInstance().getPlayerById(i).getName() + " owns " + percentage
						+ " % territories and owns " + PhaseView.getInstance().curPArmies + "<br/>";
				worldDominationViewLabel.setText(label);
			}
			worldDominationViewLabel.setText(label + "<br/><br/><center><body/></html>");

			// Continent domination starts here...

			label = label + "<html><head><h1>CONTINENT DOMINATION</h1></head><body><center><br/>";
			continentList = Game.getInstance().getGameMap().getContinentsArray();
			// System.out.println(continentList);
			int count = 0;
			for (int i = 0; i < continentList.length; i++) {
				int numofterrys = ((int) Game.getInstance().getGameMap().getTerritoriesArray(continentList[i]).length);
				continentTerritory = Game.getInstance().getGameMap().getTerritoriesAsObjects(continentList[i]);
				for (Player player : Game.getInstance().getPlayers()) {

					int tempTerritoryCount = 0;
					float percentage = 0;

					for (Territory terry : continentTerritory) {
						if (terry.getOwner().getId() == player.getId()) {
							tempTerritoryCount += 1;
						}
					}

					percentage = (float) 100 * tempTerritoryCount / numofterrys;
					label = label + player.getName() + " owns " + percentage + " % of " + continentList[i] + "<br/>";
					worldDominationViewLabel.setText(label);
				}

			}
			worldDominationViewLabel.setText(label + "<center></body></html>");

		}

		else if (o instanceof GameController) {

		}
	}

	public static WorldDominationView getInstance() {
		if (instance == null) {
			instance = new WorldDominationView();
		}
		return instance;
	}
}
