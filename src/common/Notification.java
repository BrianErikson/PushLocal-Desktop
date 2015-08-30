package common;

import java.io.IOException;

/**
 * Created by brian on 8/30/15.
 */
public class Notification {
    public static void Post(String title, String text, String subText) throws IOException {
        if (OsUtils.isWindows()) {
            // TODO: Create a tray icon and post it that way
            // http://docs.oracle.com/javase/6/docs/api/java/awt/TrayIcon.html
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
