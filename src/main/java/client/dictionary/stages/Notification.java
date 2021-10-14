package client.dictionary.stages;

import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notification {
    public static void show(String text, AnchorPane rootPane, boolean isDark) {
        Notifications notifications = Notifications.create().title("Notification").text(text).owner(rootPane).hideAfter(Duration.seconds(3));
        if (isDark) notifications.darkStyle();
        notifications.show();
    }
}
