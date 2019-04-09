package View;

import Controller.GameController;
import Controller.MapController;
import Model.Continent;
import Model.Game;
import Model.Player;
import Model.Territory;
import View.PhaseView;
import org.jfree.chart.*;
import org.jfree.chart.editor.ChartEditor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.xy.XYDataset;
//import org.jfree.util.Rotation;



import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class WorldDominationView implements Observer, Serializable {

	public static WorldDominationView instance;
	JLabel worldDominationViewLabel = new JLabel();
	ArrayList<Territory> territoriesList;
	ArrayList<Territory> continentTerritory;
	String[] continentList;

	public JPanel getChartPanel() {
		return chartPanel;
	}

	JPanel chartPanel;
	JFrame frameFromPhase;

	public PieDataset getDt() {
		return dt;
	}

	PieDataset dt;


	/**
	 * Show the World domination panel on the right side of the game screen
	 */
    public void initWorldDominationView() {

		worldDominationViewLabel.setBounds(1024, 0, 310, 300);
		frameFromPhase = PhaseView.getInstance().getGameFrame();
        JPanel worldDominationViewPanel = new JPanel();
		worldDominationViewPanel.setBounds(1024, 310, 310, 600);

		worldDominationViewPanel.add(worldDominationViewLabel);
		frameFromPhase.add(worldDominationViewPanel);

        Game.getInstance().addObserver(this);

    }

	/**
	 * The update method corresponding to the observer pattern
	 * @param observable object of Observable class
	 * @param o object of Object class
	 */
	@Override
	public void update(Observable observable, Object o) {
		if (o instanceof Game || o instanceof GameController || o instanceof Player) {
			String label = "<html><head><h1>World Domination View</h1></head><body> ";

			territoriesList = Game.getInstance().getGameMap().getTerritories();
			String[] name = new String[Game.getInstance().getNumPlayers()] ;
			Float[] value = new Float[Game.getInstance().getNumPlayers()];
			for (int i = 0; i < Game.getInstance().getNumPlayers(); i++) {
				int tempTerritoryCount = 0;
				float percentage = 0;

				for (Territory territory : territoriesList) {
					if (territory.getOwner() == Game.getInstance().getPlayerById(i)) {
						tempTerritoryCount += 1;
					}
				}
				percentage = (float) 100.0 * tempTerritoryCount / Game.getInstance().getGameMap().getTerritories().size();
				label = label + Game.getInstance().getPlayerById(i).getName() + " owns " + percentage
						+ " % territories and owns " + PhaseView.getInstance().curPArmies + "<br/>";
				worldDominationViewLabel.setText(label);
				//dt = createDataset(Game.getInstance().getPlayerById(i).getName(),percentage);

				//createDataset(Game.getInstance().getPlayerById(i).getName(),percentage);
				//for(int j=0; j<Game.getInstance().getNumPlayers();j++){
					name[i] = Game.getInstance().getPlayerById(i).getName();
				//}
				if(name[i] != null)
			{
				value[i] = percentage;
			}
			//dt = createDataset(name,value);
			//chartPanel = new ChartPanel(createChart(createDataset(name,value)));

		}
		dt = createDataset(name,value);
		chartPanel = new ChartPanel(createChart(dt));
		//chartPanel.repaint();
		chartPanel.setBounds(1024,0,310,300);//390
		chartPanel.setVisible(true);
		/*chartPanel.add(new JButton(new AbstractAction("Refresh Chart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createDataset(name,value);
                createChart(dt);
                chartPanel.repaint();

            }
        }),BorderLayout.SOUTH);
*/
		frameFromPhase.add(chartPanel);

		worldDominationViewLabel.setText(label + "</body></html>");

			// Continent domination starts here...

			label = label + "<html><head><h1>Continent Domination</h1></head><body><br/>";
			continentList = Game.getInstance().getGameMap().getContinentsArray();
			// System.out.println(continentList);
			int count = 0;
			for (int i = 0; i < continentList.length; i++) {
				int numofterrys = ((int) Game.getInstance().getGameMap().getTerritoriesArray(continentList[i]).length);
				continentTerritory = Game.getInstance().getGameMap().getTerritoriesAsObjects(continentList[i]);
				for (Player player : Game.getInstance().getPlayers()) {

					int tempTerritoryCount = 0;
					float percentage = 0;
					//
					for (Territory terry : continentTerritory) {
						if (terry.getOwner().getId() == player.getId()&& continentTerritory.contains(terry)) {
							tempTerritoryCount += 1;
						}
					}

					percentage = (float) 100.0 * tempTerritoryCount / numofterrys;
					label = label + player.getName() + " owns " + percentage + " % of " + continentList[i] + "<br/>";
					worldDominationViewLabel.setText(label);
				}

			}
			worldDominationViewLabel.setText(label + "</left></body></html>");

		}


	}
	/**
	 * Creating a dataset for the pie chart
	 * @param plname string array of names of the player
	 * @param value	float array for the percentage value
	 * @return
	 */
	public static PieDataset createDataset(String[] plname,Float[] value){

		DefaultPieDataset dataset = new DefaultPieDataset();
		for(int i =0; i< plname.length;i++){
			//dataset.addChangeListener(Object::notify);
			dataset.setValue(plname[i],value[i]);

		}
		return dataset;
	}
	/*public static PieDataset createDataset(String plname,float value) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue(plname,value);
		return dataset;
	}*/

	/**
	 * The method to create a pie chart using JFreechart
	 * @param dataset object of PieDataset
	 * @return
	 */
	public static JFreeChart createChart(PieDataset dataset ) {
		JFreeChart chart = ChartFactory.createPieChart(
				"Territories Domination",   // chart title
				dataset,          // data
				true,             // include legend
				false,
				false);
		/*PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(0);

		plot.setDirection(org.jfree.chart.util.Rotation.CLOCKWISE);
		plot.setBackgroundPaint(null);
		plot.setOutlinePaint(null);
		plot.setForegroundAlpha(0.5f);
		plot.setLabelGenerator(null);*/

		return chart;
	}
	public static WorldDominationView getInstance() {
		if (instance == null) {
			instance = new WorldDominationView();
		}
		return instance;
	}
}
