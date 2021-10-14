package client.dictionary.configs;

public class PlayAudioConfig {
    public static boolean getConfig() {
        return Config.getAllConfigs().getBoolean("play-audio-online");
    }

    public static void setConfig(boolean value) {
        Config.getAllConfigs().put("play-audio-online", value);
    }
}
