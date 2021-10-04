package client.dictionary.controllers;

import api.GoogleAPI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private Button translateButton, inputCopyButton, outputCopyButton;
    @FXML
    private TextArea inputTextArea, outputTextArea;
    @FXML
    private ChoiceBox inputLangChoiceBox, outputLangChoiceBox;
    @FXML
    private SplitPane translatePane;
    @FXML
    private void initialize() {
        inputLangChoiceBox.setItems(FXCollections.observableArrayList("English", "Vietnam"));
        outputLangChoiceBox.setItems(FXCollections.observableArrayList("English", "Vietnam"));
        inputLangChoiceBox.setValue("English");
        outputLangChoiceBox.setValue("Vietnam");
        //disable divider in split pane
        translatePane.getDividers().get(0).positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                translatePane.getDividers().get(0).setPosition(0.5);
            }
        });
    }


    @FXML
    public void onTranslateButtonClick() throws IOException {
        String input = inputTextArea.getText();
        String output;
        if (inputLangChoiceBox.getValue().equals("English")) {
            output = GoogleAPI.translate("en", "vi", input);
        } else {
            output = GoogleAPI.translate("vi", "en", input);
        }
        outputTextArea.setText(output);
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
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(inputTextArea.getText());
        clipboard.setContent(content);
        Notifications.create().title("Notification").text("Copied To Clipboard").owner(rootPane).hideAfter(Duration.seconds(3)).show();
    }

    @FXML
    public void onOutputCopyToClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(outputTextArea.getText());
        clipboard.setContent(content);
        Notifications.create().title("Notification").text("Copied To Clipboard").owner(rootPane).hideAfter(Duration.seconds(3)).show();
    }
}
