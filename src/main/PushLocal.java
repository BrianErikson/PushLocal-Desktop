package main;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import network.client.NetClient;
import ui.StageController;
import ui.debugmenu.DebugMenu;

/**
 * Created by brian on 8/29/15.
 */
public class PushLocal extends Application {

    private static PushLocal singleton;
    private StageController stageController;
    private static SimpleStringProperty LOGGER = new SimpleStringProperty("Application initializing...");
    private NetClient netClient;

    public static void main(String[] args) {
        launch(args);
    }

    public static PushLocal fetch() {
        return singleton;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        singleton = this;
        primaryStage.close(); // Get rid of useless init stage

        stageController = new StageController();
        stageController.setStage(new DebugMenu(LOGGER, 1024, 768));
        log("Application initialized");

        netClient = new NetClient(this);
        netClient.setDaemon(true);
        netClient.start();
    }

    public synchronized void log(String text) {
        LOGGER.setValue(LOGGER.getValueSafe() + "\n" + text);
    }
}
