package Controller;

import javax.swing.JFrame;

import Event.IEvent;
import Model.Game;
import View.HomeMenu;
import View.HomeView;
import View.MapEditorView;

public class HomeController {
	
	HomeView objHomeView;
	HomeMenu objHomeMenu;
	
	JFrame currLoadedFrame;
	
	public void initHomeWindow() {
		objHomeView = new HomeView(this);
		objHomeView.initFrame();
		initHomeMenu();
		objHomeView.addMenuBar(this.objHomeMenu.getMenuBar());
	}
	
	private void initHomeMenu() {
		objHomeMenu = new HomeMenu(this);
		objHomeMenu.initMenuBar();
	}
	
	public void eventTriggered(IEvent event) {
		String eventInfo = event.getEventInfo();
		String[] eventData = event.getEventData().split(",");
		System.out.println(eventInfo + "triggered at home controller");
		
		switch(eventInfo) {
			case "MapEditor":
				MapEditorView.getInstance(new MapEditorController()).loadFrame();
				break;
			case "New Game":
				GameController.getInstance().startNewGame(eventData);
				break;
			default:
				System.out.println("Unknown event at Home Controller");
		}
	}
}
