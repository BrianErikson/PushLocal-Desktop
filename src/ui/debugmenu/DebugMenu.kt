package ui.debugmenu

import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.stage.Stage

import java.io.IOException
import java.util.ArrayList
import java.util.function.Consumer

class DebugMenu(filterList: ObservableList<String>, log: SimpleStringProperty, width: Int, height: Int, notifications: ArrayList<Node>) : Stage() {
    private var debugController: DebugController? = null

    init {
        val loader = FXMLLoader(javaClass.getResource("debugMenu.fxml"))
        try {
            loader.load<Any>()
            debugController = loader.getController<DebugController>()
            debugController!!.setLog(log)
            debugController!!.setFilterList(filterList)
            notifications.forEach { this.addNotification(it) }
            val root = loader.getRoot<SplitPane>()
            scene = Scene(root, width.toDouble(), height.toDouble())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun addNotification(notification: Node) {
        debugController?.addNotification(notification)
    }
}
