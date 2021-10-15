package client.dictionary.configs;

public class DatabaseConfig {
    public static boolean getConfig() {
        return Config.getAllConfigs().getBoolean("database-online");
    }

    public static void setConfig(boolean value) {
        Config.getAllConfigs().put("database-online", value);
    }

}
