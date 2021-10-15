package client.dictionary.controllers;

import base.advanced.Dictionary;
import client.dictionary.configs.CssConfig;
import client.dictionary.configs.DatabaseConfig;
import client.dictionary.configs.PlayAudioConfig;
import data.MongoDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

public class SettingsController extends MenuController {
    private Alert alert;
    @FXML
    private ChoiceBox<String> switchThemeChoiceBox, dataChoiceBox;

    @FXML
    public void initialize() {
        switchThemeChoiceBox.setItems(FXCollections.observableArrayList("Light", "Dark"));
        switchThemeChoiceBox.setValue(CssConfig.getConfig() ? "Dark" : "Light");
        dataChoiceBox.setItems(FXCollections.observableArrayList("Local", "MongoDB"));
        dataChoiceBox.setValue(DatabaseConfig.getConfig() ? "MongoDB" : "Local");
        alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Import data");
        alert.setContentText("Đang import dữ liệu...");
        alert.setResizable(false);
        alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Button cancel = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setVisible(false);
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
    public void onSwitchDataChoiceBox() {
        if(dataChoiceBox.getValue().equals("Local")){
            if(DatabaseConfig.getConfig()){
                Thread thread = new Thread(() -> {
                    MongoDB.close();
                    Dictionary.initialize();
                    Platform.runLater(() -> alert.close());
                });
                alert.show();
                thread.setDaemon(true);
                thread.start();
                DatabaseConfig.setConfig(false);
            }
        }
        else {
            if(!DatabaseConfig.getConfig()){
                Thread thread = new Thread(() -> {
                    Dictionary.save();
                    MongoDB.initialize();
                    Platform.runLater(() -> alert.close());
                });
                alert.show();
                thread.setDaemon(true);
                thread.start();
                DatabaseConfig.setConfig(true);
            }
        }
    }
}
