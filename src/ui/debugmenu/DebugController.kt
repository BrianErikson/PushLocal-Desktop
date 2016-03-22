package ui.debugmenu

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.layout.FlowPane

import java.net.URL
import java.util.ResourceBundle
import kotlin.properties.Delegates

class DebugController : Initializable {
    @FXML var notification_box: FlowPane? = null
    @FXML var filterList: ListView<String>? = null
    @FXML var filterContextMenu: ContextMenu? = null
    @FXML private val console: TextArea? = null

    override fun initialize(location: URL, resources: ResourceBundle) {

    }

    fun setLog(log: SimpleStringProperty) {
        console!!.textProperty().bind(log)
    }

    fun setFilterList(filterList: ObservableList<String>) {
        this.filterList!!.items = filterList
    }


    fun addNotification(notification: Node) {
        notification_box!!.children.add(notification)
    }

    fun onFilterRemove(event: ActionEvent) {
        val item = filterList!!.selectionModel.selectedItem
        filterList!!.items.remove(item)
    }
}
