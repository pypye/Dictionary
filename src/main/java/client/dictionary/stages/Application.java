package client.dictionary.stages;

import client.dictionary.configs.CssConfig;
import client.dictionary.controllers.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/dictionary/offline-view.fxml"));
        SceneController.initializeApplication(stage, root);
    }

    @Override
    public void stop() {
        CssConfig.saveConfig();
    }

    public static void main(String[] args) {
        launch();
    }
}