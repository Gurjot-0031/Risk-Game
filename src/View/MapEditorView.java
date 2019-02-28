package View;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Controller.HomeController;
import Controller.MapController;
import Controller.MapEditorController;
import Event.MapEditorEvents;

public class MapEditorView {
	private static MapEditorController objController;
	private JFrame mapEditorFrame;
	private static MapEditorView instance;
	
	public static MapEditorView getInstance(MapEditorController objController) {
		if(instance == null) {
			instance = new MapEditorView(objController);
		}
		return instance;
	}
	
	public void loadFrame() {
		if(mapEditorFrame == null) {
			initFrame();
		}
		mapEditorFrame.setVisible(true);
	}
	
	private MapEditorView(MapEditorController objController) {
		MapEditorView.objController = objController;
	}
	
	public void initFrame() {
		mapEditorFrame = new JFrame("Map Editor");
		mapEditorFrame.setSize(1024, 768);
		mapEditorFrame.setResizable(false);
		mapEditorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapEditorFrame.getContentPane().setBackground(Color.WHITE);
		mapEditorFrame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Load Map: ");
		mapEditorFrame.getContentPane().add(label);
		label.setBounds(10, 10, 100, 20);
		
		JButton btnBrowse = new JButton("Browse");
		mapEditorFrame.getContentPane().add(btnBrowse);
		btnBrowse.setBounds(120, 10, 100, 20);
		btnBrowse.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
			    if (returnValue == JFileChooser.APPROVE_OPTION) 
			    {
			    	final JComboBox<String> listOfTerritories = new JComboBox<String>();
			    	
				    File selectedFile = fileChooser.getSelectedFile();
				    System.out.println("File Selected: " + selectedFile.getAbsolutePath());
				    MapEditorEvents objEvent = new MapEditorEvents();
				    objEvent.setEventFile(selectedFile);
				    objEvent.setEventInfo("MapEditorBrowse");
				    objController.eventTriggered(objEvent);
				    
				    JLabel label2 = new JLabel("List of continents");
					mapEditorFrame.getContentPane().add(label2);
					label2.setBounds(10, 40, 250, 20);
					
					String[] continents = MapController.getInstance().getContinentsArray();
					JComboBox<String> listOfContinents = new JComboBox<String>(continents);
					mapEditorFrame.getContentPane().add(listOfContinents);
					listOfContinents.setBounds(10, 70, 250, 20);
					listOfContinents.setSelectedIndex(0);
					listOfContinents.addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent e) {
							if (e.getStateChange() == ItemEvent.SELECTED) {
								Object selectedContinent = e.getItem();
								String[] territories = MapController.getInstance().getTerritoriesArray(selectedContinent.toString());
								final DefaultComboBoxModel<String> territoriesModel = 
										new DefaultComboBoxModel<String>(territories);
								listOfTerritories.setModel(territoriesModel);
							}
						}
					});
					
					JLabel label3 = new JLabel("List of territories");
					mapEditorFrame.getContentPane().add(label3);
					label3.setBounds(300, 40, 250, 20);
					
					String selectedContinent = listOfContinents.getSelectedItem().toString();
					String[] territories = MapController.getInstance().getTerritoriesArray(selectedContinent);
					mapEditorFrame.getContentPane().add(listOfTerritories);
					DefaultComboBoxModel<String> territoriesModel = new DefaultComboBoxModel<String>(territories);
					listOfTerritories.setModel(territoriesModel);
					listOfTerritories.setBounds(300, 70, 250, 20);
					
					//JLabel label4 = new JLabel("Add Continent");
					JTextField inputContinent = new JTextField();
					mapEditorFrame.getContentPane().add(inputContinent);
					inputContinent.setBounds(10, 110, 250, 20);
					
					JTextField inputTerritory = new JTextField();
					mapEditorFrame.getContentPane().add(inputTerritory);
					inputTerritory.setBounds(300, 110, 250, 20);
			    }
				return;
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		
		
		
	}
	
	
}
