package client.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class OfflineController {
    @FXML
    private Button offlineButton, onlineButton;

    @FXML
    protected void onOfflineButtonClick() throws IOException {
        SceneController.switchToOffline();
    }
    @FXML
    protected void onOnlineButtonClick() throws IOException {
        SceneController.switchToOnline();

    }
}