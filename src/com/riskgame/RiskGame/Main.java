package com.riskgame.RiskGame;

import com.riskgame.Controllers.HomeController;

import javax.swing.*;

/**
 * Main class to run the Risk Game.
 *
 * @author Ashmeet
 * @version 1.2
 */

public class Main {
    /**
     * To start the intialize and start the risk game using the main function.
     * @param args Arguments used for the Main class.
     */
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HomeController obj = new HomeController();
                obj.gameInitializer();
            }
        });
    }

}
