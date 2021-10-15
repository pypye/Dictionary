package client.dictionary.controllers;

import api.GoogleAPI;
import api.TextToSpeechAPIOffline;
import api.TextToSpeechAPIOnline;
import client.dictionary.configs.CssConfig;
import client.dictionary.configs.PlayAudioConfig;
import client.dictionary.stages.Notification;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OnlineController extends MenuController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ChoiceBox<String> inputLangChoiceBox;
    @FXML
    private ChoiceBox<String> outputLangChoiceBox;
    @FXML
    private SplitPane translatePane;
    @FXML
    private TextArea inputTextArea, outputTextArea;

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
        Thread thread = new Thread(() -> {
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
        });
        thread.setDaemon(true);
        thread.start();
        outputTextArea.setPromptText("Đang dịch...");
        outputTextArea.setText(null);
    }

    @FXML
    public void onSwitchInputLanguage() {
        if (inputLangChoiceBox.getValue().equals("English")) outputLangChoiceBox.setValue("Vietnam");
        else outputLangChoiceBox.setValue("English");

    }

    @FXML
    public void onSwitchOutputLanguage() {
        if (outputLangChoiceBox.getValue().equals("English")) inputLangChoiceBox.setValue("Vietnam");
        else inputLangChoiceBox.setValue("English");

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
        Notification.show("Copied To Clipboard", rootPane, CssConfig.getConfig());
    }

    @FXML
    public void onPlayAudioInputBtn() {
        if (PlayAudioConfig.getConfig()) {
            Thread thread = new Thread(() -> TextToSpeechAPIOnline.getTextToSpeech(inputTextArea.getText()));
            thread.setDaemon(true);
            thread.start();
        } else {
            Thread thread = new Thread(() -> TextToSpeechAPIOffline.getTextToSpeech(inputTextArea.getText()));
            thread.setDaemon(true);
            thread.start();
        }
    }

    @FXML
    public void onPlayAudioOutputBtn() {
        if (PlayAudioConfig.getConfig()) {
            Thread thread = new Thread(() -> TextToSpeechAPIOnline.getTextToSpeech(outputTextArea.getText()));
            thread.setDaemon(true);
            thread.start();
        } else {
            Thread thread = new Thread(() -> TextToSpeechAPIOffline.getTextToSpeech(outputTextArea.getText()));
            thread.setDaemon(true);
            thread.start();
        }
    }
}
