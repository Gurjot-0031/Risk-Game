package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import Controller.HomeController;

public class HomeMenu {
	private HomeController objHomeController;
	
	private JMenuBar homeMenuBar;
	private HashMap<String, JMenu> homeMenus;
	
	public HomeMenu(HomeController objHomeController) {
		this.objHomeController = objHomeController;
		this.homeMenus = new HashMap<String, JMenu>();
	}
	
	public JMenuBar getMenuBar() {
		return this.homeMenuBar;
	}
	
	private void initMapEditorMenu(JMenuBar homeMenuBar) {
		JMenu mapEditorMenu = new JMenu("MapEditor");
		mapEditorMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Mouse released");
				objHomeController.eventTriggered("MapEditor");
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				return;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse clicked");
				objHomeController.eventTriggered("MapEditor");
				return;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		homeMenuBar.add(mapEditorMenu);
		homeMenus.put("MapEditor", mapEditorMenu);
	}
	
	private void initFileMenu(JMenuBar homeMenuBar) {
		JMenu fileMenu = new JMenu("File");
		// TBD file options
		homeMenuBar.add(fileMenu);
		homeMenus.put("File", fileMenu);
	}
	
	public void initMenuBar() {
		homeMenuBar = new JMenuBar();
		initFileMenu(homeMenuBar);
		initMapEditorMenu(homeMenuBar);
	}
}
