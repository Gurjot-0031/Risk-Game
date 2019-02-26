package View;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.HomeController;
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
				    File selectedFile = fileChooser.getSelectedFile();
				    System.out.println("File Selected: " + selectedFile.getAbsolutePath());
				    MapEditorEvents objEvent = new MapEditorEvents();
				    objEvent.setEventFile(selectedFile);
				    objEvent.setEventInfo("MapEditorBrowse");
				    objController.eventTriggered(objEvent);
			    }
				return;
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
	}
	
	
}
