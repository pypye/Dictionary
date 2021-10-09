package client.dictionary.controllers;

import api.TextToSpeechAPI;
import base.advanced.Dictionary;
import client.dictionary.stages.Popup;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class OfflineController extends MenuController {
    private ArrayList<String> outputDictionary;
    private int countLazy = 0;
    private String currentWord;
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

    @FXML
    public void initialize() {
        //add thread make program run continuously.
        new Thread(() -> Platform.runLater(() -> {
            outputDictionary = Dictionary.dictionarySearcher("");
            addListWordButton();
        })).start();
        //lazy load on scroll
        listWordScroll.vvalueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (listWordScroll.getVvalue() >= 0.75) {
                countLazy += 50;
                addListWordButton();
            }
        });
        definitionHBox.setVisible(false);
    }

    @FXML
    public void onPlayAudioButton() {
        new Thread(() -> TextToSpeechAPI.getTextToSpeech(wordLabel.getText())).start();
    }

    @FXML
    public void onTypeSearchInput() {
        String input = searchInput.getText();
        new Thread(() -> {
            outputDictionary = Dictionary.dictionarySearcher(input);
            Platform.runLater(() -> {
                outputVbox.getChildren().clear();
                countLazy = 0;
                addListWordButton();
            });
        }).start();
    }

    private void addListWordButton() {
        if (outputDictionary.size() <= 1 && outputDictionary.get(0) == "") {
            Label notExist = new Label("Từ này không tồn tại");
            Button addWord = new Button("Thêm từ...");
            EventHandler<ActionEvent> event = e -> {
                new Popup("Add", searchInput.getText());
            };
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
            EventHandler<ActionEvent> event = e -> {
                onClickResultButton(result);
            };
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

    //run a recursion to create a tree view
    private void createTree(JSONArray array, int depth) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject child = array.getJSONObject(i);
            String childName = child.keys().next();
            if (child.get(childName) instanceof JSONArray) {
                Label childLabel = new Label(duplicateStr("\t", depth) + childName);
                explainVbox.getChildren().add(setStyleLabelByDepth(childLabel, depth));
                JSONArray childArray = child.getJSONArray(childName);
                createTree(childArray, depth + 1);
            } else {
                Label childLabel = new Label(duplicateStr("\t", depth) + childName + ": " + child.getString(childName));
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

    private String duplicateStr(String source, int times) {
        String ans = "";
        for (int i = 0; i < times; i++) ans += source;
        return ans;
    }

    @FXML
    public void onEditWordButton() {
        new Popup("Edit", currentWord);
    }

    @FXML
    public void onDeleteWordButton() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

}