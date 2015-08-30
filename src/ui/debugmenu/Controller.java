package ui.debugmenu;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private TextArea console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLog(SimpleStringProperty log) {
        console.textProperty().bind(log);
    }
}
