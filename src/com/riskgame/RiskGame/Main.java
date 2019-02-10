package com.riskgame.RiskGame;

import com.riskgame.controllers.RiskGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Main class to run the Risk Game.
 *
 * @author Ashmeet
 * @version 1.2
 */

public class Main extends Application {
    /**
     * To start the intialize and start the risk game using the main function.
     * @param args Arguments used for the Main class.
     */
    public static void main(String[] args) {


        public static void main(String[] args) {

            launch();

        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            RiskModel riskModel = new RiskModel();
            RiskGameController riskGameController = new RiskGameController(riskModel);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("javafx/RiskGame.fxml"));
            fxmlLoader.setController(riskGameController);
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("Risk Game");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();

        }
    }
