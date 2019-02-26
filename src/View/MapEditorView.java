package View;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.HomeController;

public class MapEditorView {
	HomeController objHomeController;
	JFrame mapEditorFrame;
	
	public MapEditorView(HomeController objHomeController) {
		this.objHomeController = objHomeController;
		this.mapEditorFrame = new JFrame();
	}
	
	public void initFrame() {
		JLabel label = new JLabel("Hello World");
		mapEditorFrame.getContentPane().add(label);
	}
}
