package com.riskgame.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MapEditController {

    @FXML
    private Button createMapButton;
    @FXML
    private Button loadEditMapButton;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonActions();
    }

    /**
     * Method to initialize button actions
     */
    private void initializeButtonActions() {
        createMapButton.setOnAction(createMapFileDialog());
        //loadEditMapButton.setOnAction(loadAndEditMapFileDialog());
        backButton.setOnAction(event -> closeMapEditorOptionsWindow());
    }


    /**
     * Method to open map editor dialog with empty data
     */
    private CreateNewMap createMapFileDialog() {
        CreateNewMap createNewMap = new CreateNewMap();
        return createNewMap;
    }


    /**
     * Method to open map editor dialog with loaded map data
     */
    /*private MapEditor loadAndEditMapFileDialog() {
        MapEditor mapEditor = new MapEditor();
        return mapEditor;
    }*/


    /**
     * Method to close Map Editor Options window
     */
    @FXML
    private void closeMapEditorOptionsWindow() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

}
