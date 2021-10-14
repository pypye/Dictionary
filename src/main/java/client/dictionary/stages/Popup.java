package client.dictionary.stages;

import base.advanced.Dictionary;
import client.dictionary.configs.CssConfig;
import client.dictionary.controllers.PopupController;
import client.dictionary.controllers.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Popup {
    public Popup(String title, String word) {
        try {
            Stage stage = new Stage();
            FXMLLoader root = new FXMLLoader(getClass().getResource("/client/dictionary/popup-view.fxml"));
            Scene scene = new Scene(root.load(), 720, 720, false, SceneAntialiasing.BALANCED);
            scene.getStylesheets().add(CssConfig.getConfig() ? SceneController.DARK_CSS : SceneController.LIGHT_CSS);
            stage.setTitle(title);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(SceneController.getStage());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            PopupController popupController = root.getController();
            popupController.setWordTextField(word);
            if (title.equals("Edit")) loadWordStructure(popupController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWordStructure(PopupController popupController) {
        JSONObject selectedWord = Dictionary.dictionaryLookup(popupController.getWordTextField().getText());
        popupController.setPronounTextField(selectedWord.getString("pronoun"));
        JSONArray type = selectedWord.getJSONArray("type");
        for (int i = 0; i < type.length(); i++) {
            JSONObject typeChild = type.getJSONObject(i);
            String typeChildName = typeChild.keys().next();
            popupController.setTypeText(typeChildName);
            popupController.onClickAddTypeButton();
            JSONArray explain = typeChild.getJSONArray(typeChildName);
            for (int j = 0; j < explain.length(); j++) {
                JSONObject explainChild = explain.getJSONObject(j);
                String exampleChildName = explainChild.keys().next();
                popupController.setExplainText(exampleChildName);
                popupController.onClickAddExplainButton(popupController.getCurrentTypeParentVBox());
                JSONArray example = explainChild.getJSONArray(exampleChildName);
                for (int k = 0; k < example.length(); k++) {
                    JSONObject exampleChild = example.getJSONObject(k);
                    String exampleLeft = exampleChild.keys().next();
                    String exampleRight = exampleChild.getString(exampleLeft);
                    popupController.setExampleText1(exampleLeft);
                    popupController.setExampleText2(exampleRight);
                    popupController.onClickAddExampleButton(popupController.getCurrentExplainParentVBox());
                }
            }
        }
    }
}
