package client.dictionary.controllers;

import client.dictionary.configs.CssConfig;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SettingsController extends MenuController {
    @FXML
    private ChoiceBox<String> switchThemeChoiceBox;

    @FXML
    public void initialize() {
        switchThemeChoiceBox.setItems(FXCollections.observableArrayList("Light", "Dark"));
        if (CssConfig.getConfig()) {
            switchThemeChoiceBox.setValue("Dark");
        } else {
            switchThemeChoiceBox.setValue("Light");
        }
    }

    @FXML
    public void onSwitchThemeChoiceBox() {
        if (switchThemeChoiceBox.getValue().equals("Dark")) {
            SceneController.getScene().getStylesheets().remove(SceneController.LIGHT_CSS);
            if (!SceneController.getScene().getStylesheets().contains(SceneController.DARK_CSS))
                SceneController.getScene().getStylesheets().add(SceneController.DARK_CSS);
            CssConfig.setConfig(true);
        } else {
            SceneController.getScene().getStylesheets().remove(SceneController.DARK_CSS);
            if (!SceneController.getScene().getStylesheets().contains(SceneController.LIGHT_CSS))
                SceneController.getScene().getStylesheets().add(SceneController.LIGHT_CSS);
            CssConfig.setConfig(false);
        }
    }
}
