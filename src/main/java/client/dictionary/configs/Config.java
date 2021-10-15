package client.dictionary.configs;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static JSONObject config;

    public static JSONObject getAllConfigs() {
        return config;
    }

    public static void initialize() {
        try {
            String content = new String(Files.readAllBytes(Path.of("src/main/java/client/dictionary/configs/settings.json")));
            config = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            FileWriter file = new FileWriter("src/main/java/client/dictionary/configs/settings.json");
            file.write(String.valueOf(config));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}