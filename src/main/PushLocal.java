package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import network.client.NetClient;
import ui.StageController;
import ui.debugmenu.DebugMenu;

import javax.annotation.processing.SupportedSourceVersion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by brian on 8/29/15.
 */
public class PushLocal extends Application {

    private static PushLocal singleton;
    private StageController stageController;
    private static SimpleStringProperty LOGGER = new SimpleStringProperty("Application initializing...");
    private NetClient netClient;
    private TrayIcon trayIcon;

    public static void main(String[] args) {
        launch(args);
    }

    public static PushLocal fetch() {
        return singleton;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            Image icon = Toolkit.getDefaultToolkit().getImage("src/main/PL.png");
            trayIcon = new TrayIcon(icon, "Push Local");
            trayIcon.setImageAutoSize(true);

            try {
                systemTray.add(trayIcon);
                trayIcon.displayMessage("Push Local", "This is a test messsage", TrayIcon.MessageType.NONE);
            }
            catch (AWTException e) {
                System.err.println(e);
            }
        }
        singleton = this;
        primaryStage.close(); // Get rid of useless init stage

        stageController = new StageController();
        stageController.setStage(new DebugMenu(LOGGER, 1024, 768));
        log("Application initialized");

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(stageController::showStage);
            }
        });

        netClient = new NetClient(this);
        netClient.setDaemon(true);
        netClient.start();
    }

    public synchronized void log(String text) {
        LOGGER.setValue(LOGGER.getValueSafe() + "\n" + text);
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }
}
