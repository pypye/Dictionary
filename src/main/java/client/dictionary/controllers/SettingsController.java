package client.dictionary.controllers;

import client.dictionary.configs.CssConfig;
import client.dictionary.configs.DatabaseConfig;
import client.dictionary.configs.PlayAudioConfig;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SettingsController extends MenuController {
    @FXML
    private ChoiceBox<String> switchThemeChoiceBox, playSoundChoiceBox, dataChoiceBox;

    @FXML
    public void initialize() {
        switchThemeChoiceBox.setItems(FXCollections.observableArrayList("Light", "Dark"));
        switchThemeChoiceBox.setValue(CssConfig.getConfig() ? "Dark" : "Light");
        playSoundChoiceBox.setItems(FXCollections.observableArrayList("Local", "API"));
        playSoundChoiceBox.setValue(PlayAudioConfig.getConfig() ? "API" : "Local");
        dataChoiceBox.setItems(FXCollections.observableArrayList("Local", "MongoDB"));
        dataChoiceBox.setValue(DatabaseConfig.getConfig() ? "MongoDB" : "Local");
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

    @FXML
    public void onSwitchPlayAudioChoiceBox() {
        PlayAudioConfig.setConfig(!playSoundChoiceBox.getValue().equals("Local"));
    }

    @FXML
    public void onSwitchDataChoiceBox() {
        DatabaseConfig.setConfig(!dataChoiceBox.getValue().equals("Local"));
    }
}
