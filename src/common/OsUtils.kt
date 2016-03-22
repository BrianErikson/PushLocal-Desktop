package common

import main.PushLocal

import java.awt.*
import java.io.IOException

/**
 * Created by brian on 8/30/15.
 */
object OsUtils {
    private var _osName: String? = null
    val osName: String
        get() {
            if (_osName == null) {
                _osName = System.getProperty("os.name")
            }
            return _osName as String
        }

    val isWindows: Boolean
        get() = osName.startsWith("Windows")

    val isLinux: Boolean
        get() = osName.startsWith("Linux")

    @Throws(IOException::class)
    fun postNotification(title: String, text: String, subText: String) {
        val pl = PushLocal.singleton
        if (isWindows) {
            if (subText.contains("null")) {
                pl!!.trayIcon?.displayMessage(title, text + "\n", TrayIcon.MessageType.NONE)
            } else {
                pl!!.trayIcon?.displayMessage(title, text + "\n" + subText, TrayIcon.MessageType.NONE)
            }
        } else if (isLinux) {
            if (subText.contains("null")) {
                Runtime.getRuntime().exec(arrayOf("notify-send", title, text + "\\n<i>")//TODO "-i " + pl.getIconPath()
                )
            } else {
                Runtime.getRuntime().exec(arrayOf("notify-send", title, "$text\\n<i>$subText</i>")//TODO "-i " + pl.getIconPath()
                )
            }
        }
    }
}
