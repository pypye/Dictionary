package client.dictionary.controllers;

import base.basic.DictionaryCommandLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class OfflineController extends MenuController{
    private DictionaryCommandLine dictionary;
    @FXML
    private TextField searchInput;
    @FXML
    private VBox outputVbox, explainVbox;
    @FXML
    public void initialize() throws FileNotFoundException {
        dictionary = new DictionaryCommandLine();
        dictionary.getDictionaryCommandLine().insertFromFile();
        explainVbox.getChildren().clear();
    }
    @FXML
    public void onSubmitSearchInput() {
        String input = searchInput.getText();
        ArrayList<String> output = dictionary.dictionarySearcher(input);
        outputVbox.getChildren().clear();
        for(String result : output){
            Button resultButton = new Button(result);
            EventHandler<ActionEvent> event = e -> {
                String explain = dictionary.getDictionaryCommandLine().dictionaryLookup(result);
                explainVbox.getChildren().clear();
                Label wordLabel = new Label(result);
                Label explainLabel = new Label(explain);
                explainVbox.getChildren().add(wordLabel);
                explainVbox.getChildren().add(explainLabel);

            };
            resultButton.getStyleClass().add("menu-btn");
            resultButton.setWrapText(true);
            resultButton.setOnAction(event);
            outputVbox.getChildren().add(resultButton);
        }
    }
}