package ui.notification;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by BrianErikson on 9/10/15.
 */
public class NotificationController implements Initializable {
    @FXML
    public Label title;
    @FXML
    public Label text;
    @FXML
    public Label subText;
    @FXML
    public Label time;
    @FXML
    public ImageView imageView;

    private ContextMenu contextMenu;
    private String from;

    public void setAll(Image image, String title, String text, String subText) {
        setImage(image);
        setTitle(title);
        setText(text);
        setSubText(subText);
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setImage(Image image) {
        this.imageView.setImage(image);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setSubText(String subText) {
        this.subText.setText(subText);
    }

    public void onMouseClicked(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            Node selectedNode = e.getPickResult().getIntersectedNode();
            contextMenu.show(selectedNode, Side.RIGHT, 0, 0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MenuItem ignore = new MenuItem("Ignore");
        MenuItem remove = new MenuItem("Remove");

        contextMenu = new ContextMenu(ignore, remove);
        ignore.setOnAction(event -> {
            // TODO filter the notification
        });
        remove.setOnAction(event -> {
            ((FlowPane) imageView.getParent().getParent()).getChildren().remove(imageView.getParent());
            contextMenu.hide();
        });

        SimpleDateFormat formatDate = new SimpleDateFormat("h:mm a");
        String time = formatDate.format(new Date());
        this.time.setText(time);
    }
}
