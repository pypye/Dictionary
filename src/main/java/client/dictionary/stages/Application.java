package client.dictionary.stages;

import base.advanced.Dictionary;
import client.dictionary.configs.Config;
import client.dictionary.configs.DatabaseConfig;
import client.dictionary.controllers.SceneController;
import data.MongoDB;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/client/dictionary/offline-view.fxml"));
        SceneController.initializeApplication(stage, root);
    }

    @Override
    public void stop() throws IOException {
        Config.save();
        if (DatabaseConfig.getConfig()) MongoDB.close();
        else Dictionary.save();
    }

    public static void main(String[] args) {
        launch();
    }
}