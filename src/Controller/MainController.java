package Controller;

/**
 * This is the main starting point
 *
 * @author Team38
 *
 */
public class MainController
{

    /**
     * The main function called by operating system
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        HomeController objHomeController = new HomeController();


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                objHomeController.initHomeWindow();
            }
        });
    }

}
