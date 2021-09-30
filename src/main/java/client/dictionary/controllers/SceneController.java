package client.dictionary.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    public static void initializeApplication(Stage _stage, Parent _root) {
        stage = _stage;
        root = _root;
        scene = new Scene(root);
        renderScene("Dictionary - Offline");
    }

    public static void switchToOffline() throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/client/dictionary/offline-view.fxml"));
        renderScene("Dictionary - Offline");
    }

    public static void switchToOnline() throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/client/dictionary/online-view.fxml"));
        renderScene("Dictionary - Online");
    }

    private static void renderScene(String title) {
        scene.setRoot(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
