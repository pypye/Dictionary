package client.dictionary.controllers;

import api.GoogleAPI;
import api.TextToSpeechAPI;
import client.dictionary.configs.CssConfig;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class OnlineController extends MenuController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextArea inputTextArea, outputTextArea;
    @FXML
    private ChoiceBox<String> inputLangChoiceBox;
    @FXML
    private ChoiceBox<String> outputLangChoiceBox;
    @FXML
    private SplitPane translatePane;

    @FXML
    private void initialize() {
        inputLangChoiceBox.setItems(FXCollections.observableArrayList("English", "Vietnam"));
        outputLangChoiceBox.setItems(FXCollections.observableArrayList("English", "Vietnam"));
        inputLangChoiceBox.setValue("English");
        outputLangChoiceBox.setValue("Vietnam");
        //disable divider in split pane
        translatePane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> translatePane.getDividers().get(0).setPosition(0.5));
    }

    @FXML
    public void onTranslateButtonClick() throws IOException {
        String input = inputTextArea.getText();
        new Thread(() -> {
            String output = null;
            try {
                if (inputLangChoiceBox.getValue().equals("English")) {
                    output = GoogleAPI.translate("en", "vi", input);
                } else {
                    output = GoogleAPI.translate("vi", "en", input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalOutput = output;
            Platform.runLater(() -> outputTextArea.setText(finalOutput));
        }).start();
    }

    @FXML
    public void onSwitchInputLanguage() {
        if (inputLangChoiceBox.getValue().equals("English")) {
            outputLangChoiceBox.setValue("Vietnam");
        } else {
            outputLangChoiceBox.setValue("English");
        }
    }

    @FXML
    public void onSwitchOutputLanguage() {
        if (outputLangChoiceBox.getValue().equals("English")) {
            inputLangChoiceBox.setValue("Vietnam");
        } else {
            inputLangChoiceBox.setValue("English");
        }
    }

    @FXML
    public void onInputCopyToClipboard() {
        copyToClipBoard(inputTextArea);
    }

    @FXML
    public void onOutputCopyToClipboard() {
        copyToClipBoard(outputTextArea);
    }

    private void copyToClipBoard(TextArea text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text.getText());
        clipboard.setContent(content);
        Notifications notifications = Notifications.create().title("Notification").text("Copied To Clipboard").owner(rootPane).hideAfter(Duration.seconds(3));
        if (CssConfig.getConfig()) {
            notifications.darkStyle();
        }
        notifications.show();
    }

    @FXML
    public void onPlayAudioInputBtn() {
        new Thread(() -> TextToSpeechAPI.getTextToSpeech(inputTextArea.getText())).start();
    }

    @FXML
    public void onPlayAudioOutputBtn() {
        new Thread(() -> TextToSpeechAPI.getTextToSpeech(outputTextArea.getText())).start();
    }
}
