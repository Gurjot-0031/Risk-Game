package Controller;

public class MainController {
	
	public static void main(String[] args) {
		HomeController objHomeController = new HomeController();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	objHomeController.initHomeWindow();
            }
        });
	}

}
