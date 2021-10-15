package client.dictionary.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {
    @FXML
    public void onOfflineButtonClick() throws IOException {
        SceneController.switchToOffline();
    }

    @FXML
    public void onOnlineButtonClick() throws IOException {
        SceneController.switchToOnline();
    }

    @FXML
    public void onSynonymButtonClick() throws IOException {
        SceneController.switchToSynonym();
    }

    @FXML
    public void onSettingsButtonClick() throws IOException {
        SceneController.switchToSettings();
    }
}
