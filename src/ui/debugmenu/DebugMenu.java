package ui.debugmenu;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class DebugMenu extends Stage {
    private Controller controller;

    public DebugMenu(SimpleStringProperty log, int width, int height) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("debugMenu.fxml"));
        try {
            loader.load();
            controller = loader.getController();
            controller.setLog(log);
            SplitPane root = loader.getRoot();
            setScene(new Scene(root, width, height));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
