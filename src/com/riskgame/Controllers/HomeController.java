package com.riskgame.Controllers;

import com.riskgame.RiskGame.MapEditor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import com.riskgame.Checker.ControllerChecker;
import com.riskgame.Models.HomeModel;

import java.net.URL;
import java.util.ResourceBundle;
//import riskgame.MapOptions;
    ////import game.main.PlayerSelect;
    ////import game.main.Tournament;

    //import utils.LogHelper;



/**
 * This controller class maps the user's actions to both RiskView and RiskModel
 * to handle data
 */
public class HomeController implements Initializable, ControllerChecker {


        private HomeModel homeModel;
        /*@FXML
        private Button newGameButton;*/
    /*@FXML
    private Button tournamentButton;*/
    /*@FXML
    private Button loadGameButton;*/
        @FXML
        private Button mapEditorButton;
        @FXML
        private Button quitGameButton;

        /**
         * Constructor of class RiskGameController
         *
         * @param homeModel
         */
        public HomeController(HomeModel homeModel) {

            this.homeModel = homeModel;
        }

        public HomeController() {

        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonActions();
    }

    /**
     * Function to initialize the actions of the buttons.
     */
    private void initializeButtonActions() {

        mapEditorButton.setOnAction(openMapEditor());
        quitGameButton.setOnAction(event -> quitGame());

    }


    /**
     * Method to open map editor options dialog
     * @return
     */
    private MapEditor openMapEditor() {
        MapEditor mapEditor = new MapEditor();
        return mapEditor;
    }

    /**
     * Method used to quit game
     */
    public void quitGame() {
        LogHelper.printMessage("Quitting Game");
        riskModel.quitGame();
    }

    @Override
    public void onPlayerSelected(int numberOfPlayer) {

    }
}
