package client.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PopupController {
    @FXML
    private TextField wordTextField;

    @FXML
    public void initialize() {
    }

    public void setWordText(String word) {
        wordTextField.setText(word);
    }

    @FXML
    public void onClickSaveButton() {
        System.out.println("clicked");
    }
}
