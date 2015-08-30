package common;

import main.PushLocal;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by brian on 8/30/15.
 */
public class Notification {
    public static void Post(String title, String text, String subText) throws IOException {
        PushLocal pl = PushLocal.fetch();
        if (OsUtils.isWindows()) {
            pl.getTrayIcon().displayMessage(title, text + "\n" + subText, TrayIcon.MessageType.NONE);
        }
        else if (OsUtils.isLinux()) {
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
