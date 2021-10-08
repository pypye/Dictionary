package client.dictionary.stages;

import client.dictionary.configs.CssConfig;
import client.dictionary.controllers.PopupController;
import client.dictionary.controllers.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Popup {
    private Stage stage;
    private Scene scene;

    public Popup(String title, String word) {
        try {
            stage = new Stage();
            FXMLLoader root = new FXMLLoader(getClass().getResource("/client/dictionary/popup-view.fxml"));
            scene = new Scene(root.load(), 720, 480, false, SceneAntialiasing.BALANCED);
            if (CssConfig.getConfig()) {
                scene.getStylesheets().add(SceneController.DARK_CSS);
            } else {
                scene.getStylesheets().add(SceneController.LIGHT_CSS);
            }
            PopupController popupController = root.getController();
            popupController.setWordText(word);
            stage.setTitle(title);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(SceneController.getStage());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
