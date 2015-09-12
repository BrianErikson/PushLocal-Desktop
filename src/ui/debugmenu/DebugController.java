package ui.debugmenu;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DebugController implements Initializable {
    @FXML
    public FlowPane notification_box;
    @FXML
    public ListView<String> filterList;
    @FXML
    public ContextMenu filterContextMenu;
    @FXML
    private TextArea console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLog(SimpleStringProperty log) {
        console.textProperty().bind(log);
    }

    public void setFilterList(ObservableList<String> filterList) {
        this.filterList.setItems(filterList);
    }


    public void addNotification(Node notification) {
        notification_box.getChildren().add(notification);
    }
}
