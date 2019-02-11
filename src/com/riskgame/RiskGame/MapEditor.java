package com.riskgame.RiskGame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapEditor implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        try {
            createMapEditorOptionsLayout();
        } catch (Exception exception) {
            //LogHelper.printMessage(MapOptions.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }

    /**
     * Method used to create map editor layout
     * @throws Exception
     */
    private void createMapEditorOptionsLayout() throws Exception {
        Stage mapOptionStage = new Stage();
        mapOptionStage.setTitle("Map Editor");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("javafx/MapOptions.fxml"));
        MapEditController mapEditController = new MapEditController();
        fxmlLoader.setController(mapEditController);
        Parent root = fxmlLoader.load();
        mapOptionStage.setScene(new Scene(root, 300, 275));
        mapOptionStage.show();
    }
}

