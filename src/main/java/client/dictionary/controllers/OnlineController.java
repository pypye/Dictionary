package client.dictionary.controllers;

import api.GoogleAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class OnlineController {
    @FXML
    private Button offlineButton, onlineButton, translateButton;
    @FXML
    private TextArea inputTextArea, outputTextArea;

    @FXML
    protected void onOfflineButtonClick() throws IOException {
        SceneController.switchToOffline();
    }
    @FXML
    protected void onOnlineButtonClick() throws IOException {
        SceneController.switchToOnline();
    }
    @FXML
    protected void onTranslateButtonClick() throws IOException {
        String input = inputTextArea.getText();
        String output = GoogleAPI.translate("", "vi", input);
        outputTextArea.setText(output);
    }
}
