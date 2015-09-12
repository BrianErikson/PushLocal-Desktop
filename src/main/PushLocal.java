package main;

import common.OsUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import network.client.NetClient;
import ui.StageController;
import ui.debugmenu.DebugMenu;
import ui.notification.NotificationController;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by brian on 8/29/15.
 */
public class PushLocal extends Application {

    private static PushLocal singleton;
    private static SimpleStringProperty LOGGER = new SimpleStringProperty("Application initializing...");
    private StageController stageController;
    private NetClient netClient;
    private TrayIcon trayIcon;
    private String iconPath;
    private ArrayList<Node> notificationNodes;

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
        notificationNodes = new ArrayList<>();
        stageController.setStage(new DebugMenu(LOGGER, 1024, 768, notificationNodes));
        log("Application initialized");

        if (trayIcon != null)
            trayIcon.addActionListener(e -> Platform.runLater(stageController::showStage));

        netClient = new NetClient(this);
        netClient.setDaemon(true);
        netClient.start();

        postNotification("PushLocal.Desktop", "Test", "First Line", "Second Line");
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
            } catch (AWTException e) {
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

    public Stage getStage() {
        return stageController.getStage();
    }

    // main & TCP thread
    public synchronized void postNotification(String from, String title, String text, String subText) throws IOException {
        OsUtils.postNotification(title, text, subText);

        FXMLLoader loader = new FXMLLoader(NotificationController.class.getResource("notification.fxml"));
        Node notification = loader.load();
        NotificationController controller = loader.getController();
        controller.setFrom(from);
        controller.setTitle(title);
        controller.setText(text);
        controller.setSubText(subText);
        notificationNodes.add(notification);
        if (stageController.getStage() instanceof DebugMenu) {
            Platform.runLater(() -> ((DebugMenu) stageController.getStage()).addNotification(notification));
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        netClient.dispose();
        if (trayIcon != null)
            SystemTray.getSystemTray().remove(trayIcon);
    }
}
