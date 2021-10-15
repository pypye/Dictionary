package client.dictionary.controllers;

import base.advanced.Dictionary;
import client.dictionary.configs.Config;
import client.dictionary.configs.CssConfig;
import client.dictionary.configs.DatabaseConfig;
import data.MongoDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static FXMLLoader root;
    private static Parent parent;
    public static String DARK_CSS = Objects.requireNonNull(SceneController.class.getResource("/client/css/application-dark.css")).toExternalForm();
    public static String LIGHT_CSS = Objects.requireNonNull(SceneController.class.getResource("/client/css/application-light.css")).toExternalForm();

    public static Stage getStage() {
        return stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static FXMLLoader getRoot() {
        return root;
    }

    public static void initializeApplication(Stage _stage, FXMLLoader _root) throws IOException {
        Config.initialize();
        if (DatabaseConfig.getConfig()) MongoDB.initialize();
        else Dictionary.initialize();
        stage = _stage;
        root = _root;
        parent = root.load();
        scene = new Scene(parent, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add(CssConfig.getConfig() ? DARK_CSS : LIGHT_CSS);
        renderScene();
    }

    public static void switchToOffline() throws IOException {
        root = new FXMLLoader(SceneController.class.getResource("/client/dictionary/offline-view.fxml"));
        parent = root.load();
        renderScene();
    }

    public static void switchToOnline() throws IOException {
        root = new FXMLLoader(SceneController.class.getResource("/client/dictionary/online-view.fxml"));
        parent = root.load();
        renderScene();
    }

    public static void switchToSynonym() throws IOException {
        root = new FXMLLoader(SceneController.class.getResource("/client/dictionary/synonym-view.fxml"));
        parent = root.load();
        renderScene();
    }

    public static void switchToSettings() throws IOException {
        root = new FXMLLoader(SceneController.class.getResource("/client/dictionary/settings-view.fxml"));
        parent = root.load();
        renderScene();
    }

    private static void renderScene() {
        scene.setRoot(parent);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }
}
