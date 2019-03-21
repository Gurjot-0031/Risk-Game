package Controller;

import Event.IEvent;
import Event.MapEditorEvents;
import View.MapEditorView;

/**
 * This class acts as controller for map editor events
 * 
 * @author Team38
 *
 */
public class MapEditorController {

	/**
	 * Handles the events triggered from view
	 * 
	 * @param event
	 *            Information received from view.
	 */
	public void eventTriggered(IEvent event) {
		String eventInfo = event.getEventInfo();
		System.out.println(eventInfo + "triggered at Map Editor Controller");

		switch (eventInfo) {
		case "MapEditorBrowse":
			if (MapController.getInstance().readMapFile(((MapEditorEvents) event).getEventFile()) == false) {
				System.out.println("Map Read Error");
			}
			break;
		case "Add Continent":
			String[] info = event.getEventData().split(",");
			String name = info[0];
			int reward = Integer.parseInt(info[1]);
			MapController.getInstance().addContinent(name, reward);
			break;
		case "Change Reward":
			String[] infoC = event.getEventData().split(",");
			String nameC = infoC[0];
			int rewardC = Integer.parseInt(infoC[1]);
			MapController.getInstance().changeContinentReward(nameC, rewardC);
			break;
		case "Delete Continent":
			String nameD = event.getEventData();
			MapController.getInstance().deleteContinent(nameD);
			break;
		case "Add Territory":
			String infoAT = event.getEventData();
			MapController.getInstance().addTerritory(infoAT);
			break;
		case "Delete Territory":
			String infoDT = event.getEventData();
			MapController.getInstance().deleteTerritory(infoDT);
			break;
		case "Add Adjacent":
			String infoAA = event.getEventData();
			MapController.getInstance().addAdjacent(infoAA);
			break;
		case "Delete Adjacent":
			String infoDA = event.getEventData();
			MapController.getInstance().deleteAdjacent(infoDA);
			break;
		case "MapEditorCreate":
			String infoNM = event.getEventData();
			MapController.getInstance().createMapFile(infoNM);
			break;
		case "Save Map":
			MapController.getInstance().saveMap();
			break;
		default:
			System.out.println("Unknown event received: " + eventInfo);
		}
	}
}
