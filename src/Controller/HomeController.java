package Controller;

import View.HomeMenu;
import View.HomeView;

public class HomeController {
	
	HomeView objHomeView;
	HomeMenu objHomeMenu;
	
	private void initHomeWindow() {
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
				
		}
	}

	public static void main(String[] args) {
		HomeController objHomeController = new HomeController();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	objHomeController.initHomeWindow();
            }
        });
	}
}
