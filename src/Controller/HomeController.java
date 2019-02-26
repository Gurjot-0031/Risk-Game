package Controller;

import javax.swing.JFrame;

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
	
	public void eventTriggered(String event) {
		System.out.println(event + "triggered at home controller");
		
		switch(event) {
			case "MapEditor":
				MapEditorView.getInstance(new MapEditorController()).loadFrame();
		}
	}
}
