package ui.debugmenu;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DebugController implements Initializable {
    @FXML
    public FlowPane notification_box;
    @FXML
    private TextArea console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLog(SimpleStringProperty log) {
        console.textProperty().bind(log);
    }


    public void addNotification(Node notification) {
        notification_box.getChildren().add(notification);
    }
}
