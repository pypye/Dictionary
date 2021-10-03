package client.dictionary.controllers;

import base.advanced.Dictionary;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class OfflineController extends MenuController {
    private Dictionary dictionary;
    private ArrayList<String> outputDictionary;
    private int countLazy = 0;
    @FXML
    private TextField searchInput;
    @FXML
    private VBox outputVbox, explainVbox;
    @FXML
    private ScrollPane listWordScroll;

    @FXML
    public void initialize() {
        explainVbox.getChildren().clear();
        //add thread help program run continuously.
        Platform.runLater(() -> {
            try {
                dictionary = new Dictionary("src/main/java/data/output/english-vietnamese.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputDictionary = dictionary.dictionarySearcher("");
            addListWordButton();
        });
        //lazy load on scroll
        listWordScroll.vvalueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (listWordScroll.getVvalue() >= 0.75) {
                countLazy += 50;
                addListWordButton();
            }
        });
    }

    @FXML
    public void onTypeSearchInput() {
        String input = searchInput.getText();
        //add thread help program run continuously.
        Platform.runLater(() -> {
            outputDictionary = dictionary.dictionarySearcher(input);
            outputVbox.getChildren().clear();
            countLazy = 0;
            addListWordButton();
        });
    }
    private void addListWordButton(){
        for (int i = countLazy; i < Math.min(outputDictionary.size(), countLazy + 50); i++) {
            String result = outputDictionary.get(i);
            Button resultButton = new Button(result);
            EventHandler<ActionEvent> event = e -> {
                explainVbox.getChildren().clear();
                JSONObject explain = dictionary.dictionaryLookup(result);
                Label wordLabel = new Label(result);
                explainVbox.getChildren().add(wordLabel);
                addTreeView(explain);
            };
            resultButton.getStyleClass().add("menu-btn");
            resultButton.setWrapText(true);
            resultButton.setOnAction(event);
            outputVbox.getChildren().add(resultButton);
        }
    }
    private void addTreeView(JSONObject object){
        JSONArray type = object.getJSONArray("type");
        TreeView treeView = new TreeView();
        TreeItem rootItem = new TreeItem("Explanation");
        createTree(type, rootItem);
        rootItem.setExpanded(true);
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);

        explainVbox.getChildren().add(treeView);
    }
    //run a recursion to create a tree view
    private void createTree(JSONArray array, TreeItem tree){
        for (int i = 0; i < array.length(); i++) {
            JSONObject child = array.getJSONObject(i);
            String childName = child.keys().next();
            TreeItem childTree = new TreeItem(childName);
            if(child.get(childName) instanceof JSONArray) {
                JSONArray childArray = child.getJSONArray(childName);
                createTree(childArray, childTree);
            } else {
                childTree = new TreeItem(child);
            }
            childTree.setExpanded(true);
            tree.getChildren().add(childTree);
        }
    }
}