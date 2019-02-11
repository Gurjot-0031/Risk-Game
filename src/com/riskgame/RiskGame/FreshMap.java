package com.riskgame.RiskGame;

import com.riskgame.Controllers.FreshMapController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FreshMap {
    @Override
    public void handle(ActionEvent event) {
        LogHelper.printMessage("Opening create new map dialog");
        openMapCreatorLayout();
    }

    /**
     * Method to create map creator layout
     *
     * @throws Exception
     */
    private void createMapCreatorLayout() throws Exception {
        Stage mapEditorStage = new Stage();
        mapEditorStage.setTitle("Create New Map");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("javafx/ViewMap.fxml"));
        FreshMapController mapController = new FreshMapController(false);
        fxmlLoader.setController(mapController);
        Parent root = fxmlLoader.load();
        mapEditorStage.setScene(new Scene(root, 950, 500));
        mapEditorStage.show();
    }

    /**
     * Method to open map creator layout
     */
    private void openMapCreatorLayout() {
        try {
            createMapCreatorLayout();
        } catch (Exception exception) {
            LogHelper.printMessage(MapEditor.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }
}
