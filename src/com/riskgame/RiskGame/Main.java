package com.riskgame.RiskGame;

import com.riskgame.Controllers.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.riskgame.Models.HomeModel;

/**
 * Main class to run the Risk Game.
 *
 *
 * @author Ashmeet
 * @version 1.2
 */

public class Main extends Application {
    /**
     * To start the intialize and start the risk game using the main function.
     *
     * @param args Arguments used for the Main class.
     */
    public static void main(String[] args) {

        launch();

        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            HomeModel homeModel = new HomeModel();
            HomeController homeController = new HomeController(homeModel);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("javafx/RiskGame.fxml"));
            fxmlLoader.setController(homeController);
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("Risk Game");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();

        }
    }
