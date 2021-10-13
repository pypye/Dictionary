package client.dictionary.configs;

import base.advanced.Dictionary;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
    private static JSONObject config;

    public static JSONObject getAllConfigs() {
        return config;
    }

    public static void initialize() {
        try {
            File file = new File(Dictionary.class.getResource("/config/settings.json").getFile());
            String content = new String(Files.readAllBytes(file.toPath()));
            config = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            FileWriter file = new FileWriter(Dictionary.class.getResource("/config/settings.json").getFile());
            file.write(String.valueOf(config));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
