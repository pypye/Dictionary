package client.dictionary.controllers;

import api.AudioManager;
import api.SpeechToTextAPI;
import api.TextToSpeechAPIOffline;
import api.TextToSpeechAPIOnline;
import base.advanced.Dictionary;
import client.dictionary.configs.CssConfig;
import client.dictionary.configs.PlayAudioConfig;
import client.dictionary.stages.Notification;
import client.dictionary.stages.Popup;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

public class OfflineController extends MenuController {
    private ArrayList<String> outputDictionary;
    private int countLazy = 0;
    private String currentWord;
    private Alert alert;
    private Thread voiceRegThread;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField searchInput;
    @FXML
    private VBox outputVbox, explainVbox;
    @FXML
    private HBox definitionHBox;
    @FXML
    private ScrollPane listWordScroll;
    @FXML
    private Label wordLabel, pronounLabel;

    public AnchorPane getRootPane() {
        return rootPane;
    }

    @FXML
    public void initialize() {
        definitionHBox.setVisible(false);
        //add thread make program run continuously.
        Thread thread = new Thread(() -> Platform.runLater(() -> {
            outputDictionary = Dictionary.dictionarySearcher("");
            Platform.runLater(this::addListWordButton);
        }));
        thread.setDaemon(true);
        thread.start();
        //lazy load on scroll
        listWordScroll.vvalueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (listWordScroll.getVvalue() >= 0.75) {
                countLazy += 50;
                addListWordButton();
            }
        });
        //voice recognise alert
        alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Voice Recognition");
        alert.setContentText("Đang xử lý âm thanh...");
        alert.setResizable(false);
        alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Button cancel = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        EventHandler<ActionEvent> event = e -> voiceRegThread.stop();
        cancel.setOnAction(event);
    }

    @FXML
    public void onPlayAudioButton() {
        if (PlayAudioConfig.getConfig()) {
            Thread thread = new Thread(() -> TextToSpeechAPIOnline.getTextToSpeech(wordLabel.getText()));
            thread.setDaemon(true);
            thread.start();
        } else {
            Thread thread = new Thread(() -> TextToSpeechAPIOffline.getTextToSpeech(wordLabel.getText()));
            thread.setDaemon(true);
            thread.start();
        }
    }

    @FXML
    public void onMicrophoneButtonClick() {
        if (!AudioManager.isRecording()) {
            Notification.show("Bắt đầu ghi âm. Bấm nút microphone để dừng", rootPane, CssConfig.getConfig());
            voiceRegThread = new Thread(() -> {
                AudioManager.startRecording();
                String searchResult = SpeechToTextAPI.getSpeechToText();
                Platform.runLater(() -> {
                    alert.close();
                    searchInput.setText(searchResult);
                    onTypeSearchInput();
                });
            });
            voiceRegThread.setDaemon(true);
            voiceRegThread.start();
        } else {
            alert.show();
            AudioManager.stopRecording();
        }
    }

    @FXML
    public void onTypeSearchInput() {
        String input = searchInput.getText();
        Thread thread = new Thread(() -> {
            outputDictionary = Dictionary.dictionarySearcher(input);
            Platform.runLater(() -> {
                outputVbox.getChildren().clear();
                countLazy = 0;
                addListWordButton();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void onPressEnterSearchInput() {
        if (!outputDictionary.get(0).equals("")) {
            onClickResultButton(outputDictionary.get(0));
        }
    }

    private void addListWordButton() {
        if (outputDictionary.size() <= 1 && outputDictionary.get(0).equals("")) {
            Label notExist = new Label("Từ này không tồn tại");
            Button addWord = new Button("Thêm từ...");
            EventHandler<ActionEvent> event = e -> new Popup("Add", searchInput.getText());
            notExist.setWrapText(true);
            notExist.setPadding(new Insets(0, 0, 10, 0));
            addWord.getStyleClass().add("btn");
            addWord.setOnAction(event);
            outputVbox.getChildren().add(notExist);
            outputVbox.getChildren().add(addWord);
            return;
        }
        for (int i = countLazy; i < Math.min(outputDictionary.size(), countLazy + 50); i++) {
            String result = outputDictionary.get(i);
            Button resultButton = new Button(result);
            EventHandler<ActionEvent> event = e -> onClickResultButton(result);
            resultButton.getStyleClass().add("menu-btn");
            resultButton.setWrapText(true);
            resultButton.setOnAction(event);
            outputVbox.getChildren().add(resultButton);
        }
    }

    public void onClickResultButton(String result) {
        explainVbox.getChildren().clear();
        JSONObject selectedWord = Dictionary.dictionaryLookup(result);
        currentWord = result;
        wordLabel.setText(result);
        if (!selectedWord.getString("pronoun").equals("")) {
            pronounLabel.setText("[" + selectedWord.getString("pronoun") + "]");
        } else {
            pronounLabel.setText("");
        }
        definitionHBox.setVisible(true);
        JSONArray type = selectedWord.getJSONArray("type");
        createTree(type, 0);
    }

    private void createTree(JSONArray array, int depth) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject child = array.getJSONObject(i);
            String childName = child.keys().next();
            if (child.get(childName) instanceof JSONArray) {
                Label childLabel = new Label(duplicateTab(depth) + childName);
                explainVbox.getChildren().add(setStyleLabelByDepth(childLabel, depth));
                JSONArray childArray = child.getJSONArray(childName);
                createTree(childArray, depth + 1);
            } else {
                Label childLabel = new Label(duplicateTab(depth) + childName + ": " + child.getString(childName));
                explainVbox.getChildren().add(setStyleLabelByDepth(childLabel, depth));
            }

        }
    }

    private Label setStyleLabelByDepth(Label label, int depth) {
        if (depth == 0) {
            label.getStyleClass().add("word-type-label");
        } else if (depth == 1) {
            label.getStyleClass().add("word-explain-label");
        } else if (depth == 2) {
            label.getStyleClass().add("word-example-label");
        }
        label.setWrapText(true);
        return label;
    }

    private String duplicateTab(int times) {
        return "\t".repeat(Math.max(0, times));
    }

    @FXML
    public void onEditWordButton() {
        new Popup("Edit", currentWord);
    }

    @FXML
    public void onDeleteWordButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setResizable(false);
        alert.setContentText("Bạn chắc chắn muốn xoá từ này?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dictionary.dictionaryDelete(currentWord);
            onTypeSearchInput();
            explainVbox.getChildren().clear();
            definitionHBox.setVisible(false);
            Notification.show("Xoá từ thành công", rootPane, CssConfig.getConfig());
        }
    }
}