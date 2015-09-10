package ui.debugmenu;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    public VBox notification_box;
    @FXML
    private TextArea console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLog(SimpleStringProperty log) {
        console.textProperty().bind(log);
    }
}
