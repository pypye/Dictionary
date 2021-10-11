package client.dictionary.configs;

public class DatabaseConfig {
    public static boolean getConfig() {
        return Config.getAllConfigs().getBoolean("online-database");
    }

    public static void setConfig(boolean value) {
        Config.getAllConfigs().put("online-database", value);
    }

}
