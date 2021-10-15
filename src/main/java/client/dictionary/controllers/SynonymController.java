package client.dictionary.controllers;

import api.SynonymAPI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.json.JSONArray;
import org.json.JSONObject;

public class SynonymController extends MenuController {
    @FXML
    private TextField searchTextField;
    @FXML
    private VBox contentVBox;

    @FXML
    public void onSubmitSearchBtn() {
        Thread thread = new Thread(() -> {
            JSONObject list = SynonymAPI.getSynonymList(searchTextField.getText());
            Platform.runLater(() -> {
                contentVBox.getChildren().clear();
                fetchData(list, "synonyms");
                fetchData(list, "antonyms");
                fetchData(list, "hypernyms");
                fetchData(list, "hyponyms");
            });
        });
        thread.setDaemon(true);
        thread.start();
        contentVBox.getChildren().clear();
        contentVBox.getChildren().add(new Label("Đang lấy dữ liệu..."));
    }

    private void fetchData(JSONObject list, String type) {
        JSONArray wordlist = list.getJSONArray(type);
        if (wordlist.length() < 1) return;
        TextFlow wordlistBox = new TextFlow();
        contentVBox.getChildren().add(new Label(type));
        contentVBox.getChildren().add(wordlistBox);
        for (Object v : wordlist) {
            Button wordItem = new Button((String) v);
            EventHandler<ActionEvent> event = e -> {
                searchTextField.setText((String) v);
                contentVBox.getChildren().clear();
                onSubmitSearchBtn();
            };
            wordItem.getStyleClass().add("btn-selected");
            wordItem.getStyleClass().add("btn-margin-wrap-content");
            wordItem.setOnAction(event);
            wordlistBox.getChildren().add(wordItem);
        }
    }
}
