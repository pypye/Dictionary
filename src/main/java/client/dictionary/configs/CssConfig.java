package client.dictionary.configs;

public class CssConfig {
    public static boolean getConfig() {
        return Config.getAllConfigs().getBoolean("css-dark");
    }

    public static void setConfig(boolean value) {
        Config.getAllConfigs().put("css-dark", value);
    }
}
