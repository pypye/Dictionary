package client.dictionary.controllers;

import api.SynonymAPI;
import api.TextToSpeechAPI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SynonymController extends MenuController {
    @FXML
    private TextField searchTextField;
    @FXML
    private VBox contentVBox;

    @FXML
    public void initialize() {

    }

    @FXML
    public void onSubmitSearchBtn() {
        new Thread(() -> {
            JSONObject list = SynonymAPI.getSynonymList(searchTextField.getText());
            Platform.runLater(() -> {
                contentVBox.getChildren().clear();
                fetchData(list, "synonyms");
                fetchData(list, "antonyms");
                fetchData(list, "hypernyms");
                fetchData(list, "hyponyms");
            });
        }).start();
    }

    private void fetchData(JSONObject list, String type) {
        JSONArray wordlist = list.getJSONArray(type);
        TextFlow wordlistBox = new TextFlow();
        contentVBox.getChildren().add(new Label(type));
        contentVBox.getChildren().add(wordlistBox);
        for (Object v : wordlist) {
            Button wordItem = new Button((String) v);
            EventHandler<ActionEvent> event = e -> {
                searchTextField.setText((String) v);
                onSubmitSearchBtn();
            };
            wordItem.getStyleClass().add("btn-selected");
            wordItem.getStyleClass().add("btn-margin-wrap-content");
            wordItem.setOnAction(event);
            wordlistBox.getChildren().add(wordItem);
        }
    }
}
