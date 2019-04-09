package Controller;

import javax.swing.JFrame;

import Event.IEvent;
import View.HomeMenu;
import View.HomeView;
import View.MapEditorView;

import java.io.Serializable;

/**
 * This class acts as controller for Home Screen
 *
 * @author Team38
 *
 */
public class HomeController implements Serializable {

    HomeView objHomeView;
    HomeMenu objHomeMenu;

    JFrame currLoadedFrame;

    /**
     * This is the constructor
     */
    public void initHomeWindow() {
        objHomeView = new HomeView(this);
        objHomeView.initFrame();
        initHomeMenu();
        objHomeView.addMenuBar(this.objHomeMenu.getMenuBar());
    }

    /**
     * This function initializes the home window.
     */
    private void initHomeMenu() {
        objHomeMenu = new HomeMenu(this);
        objHomeMenu.initMenuBar();
    }

    /**
     * This function handles the events triggered
     *
     * @param event
     *            The event received from view
     */
    public void eventTriggered(IEvent event) {
        String eventInfo = event.getEventInfo();
        String[] eventData;
        if (eventInfo.equals("MapEditor") == false) {
            eventData = event.getEventData().split(",");
        }

        System.out.println(eventInfo + "triggered at home controller");

        switch (eventInfo) {
            case "MapEditor":
                MapEditorView.getInstance(new MapEditorController()).loadFrame();
                break;
            case "New Game":
                GameController.getInstance().eventTriggered(event);
                break;
            default:
                System.out.println("Unknown event at Home Controller");
        }
    }
}
