package View;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.*;
import javax.tools.Tool;

import Controller.HomeController;

public class HomeView {

	private HomeController objHomeController;
	private JFrame homeFrame;

	public void addMenuBar(JMenuBar homeMenuBar) {
		this.homeFrame.setJMenuBar(homeMenuBar);
	}

	public HomeView(HomeController objHomeController) {
		this.objHomeController = objHomeController;
	}

	public void initFrame() {
		homeFrame = new JFrame("RISK");
		homeFrame.setSize(200, 200);
		homeFrame.setResizable(false);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.getContentPane().setBackground(Color.WHITE);
		homeFrame.setVisible(true);
		Image background = Toolkit.getDefaultToolkit().createImage("61PP3WD555L._AC_SX215_.jpg");
	}

}
