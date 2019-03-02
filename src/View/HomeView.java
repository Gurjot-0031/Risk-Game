package View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import Controller.HomeController;

public class HomeView {
	
	private HomeController objHomeController;
	private JFrame homeFrame;
	
	public void addMenuBar(JMenuBar homeMenuBar) {
		this.homeFrame.setJMenuBar(homeMenuBar);
	}
	
	public HomeView(HomeController objHomeController) {	this.objHomeController = objHomeController;
	}
	
	public void initFrame() {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        homeFrame = new JFrame("RISK");
        homeFrame.setSize(1024, 768);
        homeFrame.setResizable(false);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.getContentPane().setBackground(Color.WHITE);//.setLayout(null);
        
        // JLabel label = new JLabel("Hello World");
        // homeFrame.getContentPane().add(label);
 
         //homeFrame.pack();
        homeFrame.setVisible(true);
    }
	
}
