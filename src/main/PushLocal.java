package main;

import common.Notification;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import network.client.NetClient;
import ui.StageController;
import ui.debugmenu.DebugMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by brian on 8/29/15.
 */
public class PushLocal extends Application {

    private static PushLocal singleton;
    private StageController stageController;
    private static SimpleStringProperty LOGGER = new SimpleStringProperty("Application initializing...");
    private NetClient netClient;
    private TrayIcon trayIcon;
    private String iconPath;

    public static void main(String[] args) {
        launch(args);
    }

    public static PushLocal fetch() {
        return singleton;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initIconPath();
        addToSystemTray();

        singleton = this;
        primaryStage.close(); // Get rid of useless init stage

        stageController = new StageController();
        stageController.setStage(new DebugMenu(LOGGER, 1024, 768));
        log("Application initialized");

        trayIcon.addActionListener(e -> Platform.runLater(stageController::showStage));

        netClient = new NetClient(this);
        netClient.setDaemon(true);
        netClient.start();

        Notification.Post("Test", "First Line", "Second Line");
    }

    private void initIconPath() throws URISyntaxException {
        URL url = getClass().getResource("PL.png");
        File file = new File(url.toURI());
        iconPath = file.getAbsolutePath();
    }

    private void addToSystemTray() {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            Image icon = Toolkit.getDefaultToolkit().getImage("src/main/PL.png");
            trayIcon = new TrayIcon(icon, "Push Local");
            trayIcon.setImageAutoSize(true);
            try {
                systemTray.add(trayIcon);
            }
            catch (AWTException e) {
                log(e.getMessage());
            }
        }
    }

    public synchronized void log(String text) {
        LOGGER.setValue(LOGGER.getValueSafe() + "\n" + text);
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public String getIconPath() {
        return iconPath;
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        netClient.dispose();
        SystemTray.getSystemTray().remove(trayIcon);
    }
}
