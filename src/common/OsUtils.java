package common;

import main.PushLocal;

import java.awt.*;
import java.io.IOException;

/**
 * Created by brian on 8/30/15.
 */
public class OsUtils {
    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isLinux() {
        return getOsName().startsWith("Linux");
    }

    public static void postNotification(String title, String text, String subText) throws IOException {
        PushLocal pl = PushLocal.fetch();
        if (isWindows()) {
            pl.getTrayIcon().displayMessage(title, text + "\n" + subText, TrayIcon.MessageType.NONE);
        } else if (isLinux()) {
            Runtime.getRuntime().exec(new String[]{
                    "notify-send",
                    title,
                    text + "\\n<i>"
                            + subText + "</i>",
                    //TODO "-i " + pl.getIconPath()
            });
        }
    }
}
