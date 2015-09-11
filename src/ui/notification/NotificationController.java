package ui.notification;

import com.sun.istack.internal.Nullable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
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

    public void setAll(Image image, String title, String text, String subText) {
        setImage(image);
        setTitle(title);
        setText(text);
        setSubText(subText);
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

    public void onMouseClicked() {
        // TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
