package main

import common.OsUtils
import javafx.application.Application
import javafx.application.Platform
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.stage.Stage
import network.client.NetClient
import ui.StageController
import ui.debugmenu.DebugMenu
import ui.notification.NotificationController

import java.awt.*
import java.io.File
import java.io.IOException
import java.net.URISyntaxException
import java.net.URL
import java.util.ArrayList

/**
 * Created by brian on 8/29/15.
 */

fun main(args: Array<String>) {
    PushLocal(args)
}

class PushLocal(args: Array<String>) : Application() {
    var trayIcon: TrayIcon? = null
    private val filteredNotifications: ObservableList<String> = FXCollections.observableArrayList<String>()
    private val stageController: StageController = StageController()
    private var netClient: NetClient? = null
    private var iconPath: String = ""
    private val notificationNodes: ArrayList<Node> = ArrayList<Node>()

    init {
        singleton = this;
        Application.launch(*args)
    }

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        initIconPath()
        addToSystemTray()
        primaryStage.close() // Get rid of useless init stage

        stageController.stage = DebugMenu(filteredNotifications, LOGGER, 1024, 768, notificationNodes)
        log("Application initialized")

        if (trayIcon != null)
            trayIcon!!.addActionListener { e -> Platform.runLater { stageController.showStage() } }

        netClient = NetClient(this)
        netClient!!.isDaemon = true
        netClient!!.start()

        postNotification("PushLocal.Desktop", "Test", "First Line", "Second Line")
    }

    @Throws(URISyntaxException::class)
    private fun initIconPath() {
        val url = javaClass.getResource("PL.png")
        val file = File(url.toURI())
        iconPath = file.absolutePath
    }

    private fun addToSystemTray() {
        if (SystemTray.isSupported()) {
            val systemTray = SystemTray.getSystemTray()
            val icon = Toolkit.getDefaultToolkit().getImage("src/main/PL.png")
            trayIcon = TrayIcon(icon, "Push Local")
            trayIcon!!.isImageAutoSize = true
            try {
                systemTray.add(trayIcon)
            } catch (e: AWTException) {
                log(e.message as String)
            }
        }
    }

    @Synchronized fun log(text: String) {
        Platform.runLater { LOGGER.value = LOGGER.valueSafe + "\n" + text }
    }

    // main & TCP thread
    @Synchronized @Throws(IOException::class)
    fun postNotification(origin: String, title: String, text: String, subText: String) {
        var filtered = false
        for (s in filteredNotifications) {
            if (s.contains(origin))
                filtered = true
        }

        if (!filtered) {
            OsUtils.postNotification(title, text, subText)

            val loader = FXMLLoader(NotificationController::class.java.getResource("notification.fxml"))
            val notification = loader.load<Node>()
            val controller = loader.getController<NotificationController>()
            controller.origin = origin
            controller.setTitle(title)
            controller.setText(text)
            controller.setSubText(if (subText.contains("null")) "" else subText)
            notificationNodes.add(notification)
            if (stageController.stage is DebugMenu) {
                println("Is intance of debugMenu")
                Platform.runLater { (stageController.stage as DebugMenu).addNotification(notification) }
            }
        }
    }

    fun addFilteredNotification(origin: String) {
        filteredNotifications.add(origin)
    }

    @Throws(Exception::class)
    override fun stop() {
        super.stop()
        netClient?.dispose()
        if (trayIcon != null)
            SystemTray.getSystemTray().remove(trayIcon)
    }

    companion object {
        private val LOGGER = SimpleStringProperty("Application initializing...")
        private var _singleton: PushLocal? = null
        var singleton : PushLocal?
        get() {
            return _singleton
        }
        set(value) {
            if (_singleton == null) {
                _singleton = value
            }
        }
    }
}
