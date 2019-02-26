package Controller;

import Event.IEvent;
import View.MapEditorView;

public class MapEditorController {
	public void eventTriggered(IEvent event) {
		String eventInfo = event.getEventInfo();
		System.out.println(eventInfo + "triggered at Map Editor Controller");
		
		switch(eventInfo) {
			case "MapEditorBrowse":
				break;
		}
	}
}
