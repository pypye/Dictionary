package client.dictionary.controllers;

import base.advanced.Dictionary;
import client.dictionary.configs.CssConfig;
import client.dictionary.stages.Notification;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class PopupController {
    private String typeText = null;
    private String explainText = null;
    private String exampleText1 = null;
    private String exampleText2 = null;

    @FXML
    private TextField wordTextField, pronounTextField;
    @FXML
    private VBox editVbox, currentTypeParentVBox, currentExplainParentVBox;

    public void setWordTextField(String word) {
        wordTextField.setText(word);
    }

    public void setPronounTextField(String word) {
        pronounTextField.setText(word);
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public void setExplainText(String explainText) {
        this.explainText = explainText;
    }

    public void setExampleText1(String exampleText1) {
        this.exampleText1 = exampleText1;
    }

    public void setExampleText2(String exampleText2) {
        this.exampleText2 = exampleText2;
    }

    public TextField getWordTextField() {
        return wordTextField;
    }

    public VBox getCurrentTypeParentVBox() {
        return currentTypeParentVBox;
    }

    public VBox getCurrentExplainParentVBox() {
        return currentExplainParentVBox;
    }

    @FXML
    public void onClickAddTypeButton() {
        VBox typeParentVBox = new VBox();
        currentTypeParentVBox = typeParentVBox;
        HBox typeHbox = new HBox();
        ImageView typeImageAddExplain = new ImageView(Objects.requireNonNull(PopupController.class.getResource("/client/icons/add.png")).toExternalForm());
        typeImageAddExplain.setFitHeight(25);
        typeImageAddExplain.setFitWidth(25);
        typeImageAddExplain.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExplainButton(typeParentVBox));
        TextField typeTextField = new TextField();
        if (typeText != null) {
            typeTextField.setText(typeText);
            typeText = null;
        }
        typeTextField.setPromptText("danh từ, động từ, tính từ, ...");
        typeTextField.setPrefWidth(489);
        ImageView typeImageRemove = new ImageView(Objects.requireNonNull(PopupController.class.getResource("/client/icons/remove.png")).toExternalForm());
        typeImageRemove.setFitHeight(25);
        typeImageRemove.setFitWidth(25);
        typeImageRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> editVbox.getChildren().remove(typeParentVBox));
        typeHbox.getChildren().add(typeImageAddExplain);
        typeHbox.getChildren().add(new Label("Loại từ"));
        typeHbox.getChildren().add(typeTextField);
        typeHbox.getChildren().add(typeImageRemove);
        typeHbox.setSpacing(10);
        typeHbox.setAlignment(Pos.CENTER_LEFT);
        typeParentVBox.getChildren().add(typeHbox);
        typeParentVBox.setSpacing(5);
        editVbox.getChildren().add(typeParentVBox);
    }

    public void onClickAddExplainButton(VBox parentVBox) {
        VBox explainParentVBox = new VBox();
        currentExplainParentVBox = explainParentVBox;
        HBox explainHbox = new HBox();
        ImageView explainImageAddExample = new ImageView(Objects.requireNonNull(PopupController.class.getResource("/client/icons/add.png")).toExternalForm());
        explainImageAddExample.setFitHeight(25);
        explainImageAddExample.setFitWidth(25);
        explainImageAddExample.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickAddExampleButton(explainParentVBox));
        TextField explainTextField = new TextField();
        explainTextField.setPromptText("Giải thích tiếng việt");
        if (explainText != null) {
            explainTextField.setText(explainText);
            explainText = null;
        }
        explainTextField.setPrefWidth(401);
        ImageView explainImageRemove = new ImageView(Objects.requireNonNull(PopupController.class.getResource("/client/icons/remove.png")).toExternalForm());
        explainImageRemove.setFitHeight(25);
        explainImageRemove.setFitWidth(25);
        explainImageRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> parentVBox.getChildren().remove(explainParentVBox));
        explainHbox.getChildren().add(explainImageAddExample);
        explainHbox.getChildren().add(new Label("Giải thích"));
        explainHbox.getChildren().add(explainTextField);
        explainHbox.getChildren().add(explainImageRemove);
        explainHbox.setSpacing(10);
        explainHbox.setPadding(new Insets(0, 0, 0, 70));
        explainHbox.setAlignment(Pos.CENTER_LEFT);
        explainParentVBox.getChildren().add(explainHbox);
        explainParentVBox.setSpacing(5);

        parentVBox.getChildren().add(explainParentVBox);
    }

    public void onClickAddExampleButton(VBox parentVBox) {
        VBox exampleParentVbox = new VBox();
        HBox exampleHbox = new HBox();
        TextField exampleField1 = new TextField();
        exampleField1.setPromptText("Câu tiếng anh");
        if (exampleText1 != null) {
            exampleField1.setText(exampleText1);
            exampleText1 = null;
        }
        exampleField1.setPrefWidth(193);
        TextField exampleField2 = new TextField();
        exampleField2.setPromptText("Câu tiếng việt");
        if (exampleText2 != null) {
            exampleField2.setText(exampleText2);
            exampleText2 = null;
        }
        exampleField2.setPrefWidth(193);
        ImageView exampleImageRemove = new ImageView(Objects.requireNonNull(PopupController.class.getResource("/client/icons/remove.png")).toExternalForm());
        exampleImageRemove.setFitHeight(25);
        exampleImageRemove.setFitWidth(25);
        exampleImageRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> parentVBox.getChildren().remove(exampleParentVbox));
        exampleHbox.getChildren().add(new Label("Ví dụ"));
        exampleHbox.getChildren().add(exampleField1);
        exampleHbox.getChildren().add(exampleField2);
        exampleHbox.getChildren().add(exampleImageRemove);
        exampleHbox.setSpacing(10);
        exampleHbox.setPadding(new Insets(0, 0, 0, 140));
        exampleHbox.setAlignment(Pos.CENTER_LEFT);
        exampleParentVbox.getChildren().add(exampleHbox);
        exampleParentVbox.setSpacing(5);
        parentVBox.getChildren().add(exampleParentVbox);
    }

    @FXML
    public void onClickSaveButton() {
        JSONObject wordJSON = new JSONObject();
        JSONArray typeArray = new JSONArray();
        for (int i = 0; i < editVbox.getChildren().size(); i++) {
            VBox typeVBox = (VBox) editVbox.getChildren().get(i);
            HBox typeHBox = (HBox) typeVBox.getChildren().get(0);
            JSONObject typeJSON = new JSONObject();
            JSONArray explainArray = new JSONArray();
            for (int j = 1; j < typeVBox.getChildren().size(); j++) {
                VBox explainVBox = (VBox) typeVBox.getChildren().get(j);
                HBox explainHBox = (HBox) explainVBox.getChildren().get(0);
                JSONObject explainJSON = new JSONObject();
                JSONArray exampleArray = new JSONArray();
                for (int k = 1; k < explainVBox.getChildren().size(); k++) {
                    VBox exampleVBox = (VBox) explainVBox.getChildren().get(k);
                    HBox exampleHBox = (HBox) exampleVBox.getChildren().get(0);
                    JSONObject exampleJSON = new JSONObject();
                    exampleJSON.put(((TextField) exampleHBox.getChildren().get(1)).getText(), ((TextField) exampleHBox.getChildren().get(2)).getText());
                    exampleArray.put(exampleJSON);
                }
                explainJSON.put(((TextField) explainHBox.getChildren().get(2)).getText(), exampleArray);
                explainArray.put(explainJSON);
            }
            typeJSON.put(((TextField) typeHBox.getChildren().get(2)).getText(), explainArray);
            typeArray.put(typeJSON);
        }
        wordJSON.put("pronoun", pronounTextField.getText());
        wordJSON.put("type", typeArray);
        Dictionary.dictionaryInsert(wordTextField.getText(), wordJSON);
        OfflineController offlineController = SceneController.getRoot().getController();
        offlineController.onTypeSearchInput();
        offlineController.onClickResultButton(wordTextField.getText());
        Stage stage = (Stage) editVbox.getScene().getWindow();
        stage.close();
        Notification.show("Thêm/Sửa từ thành công", offlineController.getRootPane(), CssConfig.getConfig());
    }
}
