package common;

import main.PushLocal;

import java.awt.*;
import java.io.IOException;

/**
 * Created by brian on 8/30/15.
 */
public class Notification {
    public static void Post(String title, String text, String subText) throws IOException {
        if (OsUtils.isWindows()) {
            PushLocal.fetch().getTrayIcon().displayMessage(title, text + "\n" + subText, TrayIcon.MessageType.INFO);
        }
        else if (OsUtils.isLinux()) {
            Runtime.getRuntime().exec(new String[]{
                    "notify-send",
                    title,
                    text + "\\n<i>"
                            + subText + "</i>"
            });
        }
    }
}
