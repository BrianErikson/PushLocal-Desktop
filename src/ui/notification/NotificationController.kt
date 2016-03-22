package ui.notification

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Side
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import main.PushLocal

import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.ResourceBundle

/**
 * Created by BrianErikson on 9/10/15.
 */
class NotificationController : Initializable {
    @FXML var title: Label? = null
    @FXML var text: Label? = null
    @FXML var subText: Label? = null
    @FXML var time: Label? = null
    @FXML var imageView: ImageView? = null

    private var contextMenu: ContextMenu? = null
    var origin: String = ""

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val ignore = MenuItem("Ignore")
        val remove = MenuItem("Remove")

        contextMenu = ContextMenu(ignore, remove)
        ignore.setOnAction { event ->
            PushLocal.singleton!!.addFilteredNotification(origin)
            removeFromParent()
            contextMenu!!.hide()
        }
        remove.setOnAction { event ->
            removeFromParent()
            contextMenu!!.hide()
        }

        val formatDate = SimpleDateFormat("h:mm a")
        val time = formatDate.format(Date())
        this.time?.text = time
    }

    fun setAll(image: Image, title: String, text: String, subText: String, origin: String) {
        setImage(image)
        setTitle(title)
        setText(text)
        setSubText(subText)
        this.origin = origin
    }

    fun setImage(image: Image) {
        this.imageView?.image = image
    }

    fun setTitle(title: String) {
        this.title?.text = title
    }

    fun setText(text: String) {
        this.text?.text = text
    }

    fun setSubText(subText: String) {
        this.subText?.text = subText
    }

    fun onMouseClicked(e: MouseEvent) {
        if (e.button == MouseButton.SECONDARY) {
            val selectedNode = e.pickResult.intersectedNode
            contextMenu!!.show(selectedNode, Side.RIGHT, 0.0, 0.0)
        }
    }

    fun removeFromParent() {
        (imageView?.parent?.parent as FlowPane).children.remove(imageView?.parent)
    }
}
