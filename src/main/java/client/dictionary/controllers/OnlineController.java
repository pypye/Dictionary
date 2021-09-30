package client.dictionary.controllers;

import api.GoogleAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class OnlineController {
    @FXML
    private Button offlineButton, onlineButton, translateButton;
    @FXML
    private TextArea inputTextArea, outputTextArea;
    @FXML
    private ChoiceBox inputLangChoiceBox, outputLangChoiceBox;

    @FXML
    private void initialize() {
        inputLangChoiceBox.setItems(FXCollections.observableArrayList("Vietnam", "English"));
        outputLangChoiceBox.setItems(FXCollections.observableArrayList("Vietnam", "English"));
        inputLangChoiceBox.setValue("English");
        outputLangChoiceBox.setValue("Vietnam");
    }

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
