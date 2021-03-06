package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.HomeController;
import Event.GameEvents;
import Event.MapEditorEvents;
import javafx.stage.FileChooser;

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
			
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse clicked");
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("MapEditor");
				objHomeController.eventTriggered(objEvent);
				return;
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		
		homeMenuBar.add(mapEditorMenu);
		homeMenus.put("MapEditor", mapEditorMenu);
	}
	
	private void initFileMenu(JMenuBar homeMenuBar) {
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("New Game");
				JPanel configPanel = new JPanel();
				configPanel.setSize(600, 600);
				Integer[] values = {2,3,4,5,6,7,8};
				JComboBox<Integer> numPlayers = new JComboBox<Integer>(values);
				
				JButton loadMap = new JButton("Choose Map");
				loadMap.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						int returnValue = fileChooser.showOpenDialog(null);
					    if (returnValue == JFileChooser.APPROVE_OPTION) 
					    {
						    File selectedFile = fileChooser.getSelectedFile();
						    System.out.println("File Selected: " + selectedFile.getAbsolutePath());
					    }
					}
				});
				
				configPanel.add(numPlayers);
				configPanel.add(loadMap);
				int result = JOptionPane.showConfirmDialog(null, configPanel, "Please Configure", 
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					System.out.println("Starting new game");
				}
			}
		});
		fileMenu.add(newGame);
		
		homeMenuBar.add(fileMenu);
		homeMenus.put("File", fileMenu);
	}
	
	public void initMenuBar() {
		homeMenuBar = new JMenuBar();
		initFileMenu(homeMenuBar);
		initMapEditorMenu(homeMenuBar);
	}
}
