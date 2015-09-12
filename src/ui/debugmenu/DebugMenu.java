package ui.debugmenu;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class DebugMenu extends Stage {
    private DebugController debugController;

    public DebugMenu(ObservableList<String> filterList, SimpleStringProperty log, int width, int height, ArrayList<Node> notifications) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("debugMenu.fxml"));
        try {
            loader.load();
            debugController = loader.getController();
            debugController.setLog(log);
            debugController.setFilterList(filterList);
            notifications.forEach(this::addNotification);
            SplitPane root = loader.getRoot();
            setScene(new Scene(root, width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNotification(Node notification) {
        debugController.addNotification(notification);
    }
}
