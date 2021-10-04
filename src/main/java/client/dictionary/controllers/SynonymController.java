package client.dictionary.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

public class SynonymController extends MenuController{
    @FXML
    private SplitPane synonymSplitPane;

    @FXML
    public void initialize(){
        //disable divider in split pane
        synonymSplitPane.getDividers().get(0).positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                synonymSplitPane.getDividers().get(0).setPosition(0.5);
            }
        });
    }
}
