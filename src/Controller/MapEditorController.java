package Controller;

import Event.IEvent;
import Event.MapEditorEvents;
import View.MapEditorView;

public class MapEditorController {
	
	public void eventTriggered(IEvent event) {
		String eventInfo = event.getEventInfo();
		System.out.println(eventInfo + "triggered at Map Editor Controller");
		
		switch(eventInfo) {
			case "MapEditorBrowse":
				MapController.getInstance().readMapFile(((MapEditorEvents)event).getEventFile());
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
			default:
				System.out.println("Unknown event received");
		}
	}
}
