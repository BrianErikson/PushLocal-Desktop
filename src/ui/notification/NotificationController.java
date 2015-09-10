package ui.notification;

import com.sun.istack.internal.Nullable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by BrianErikson on 9/10/15.
 */
public class NotificationController {
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

    public NotificationController(@Nullable Image image, String title, String text, @Nullable String subText) {
        if (image != null)
            imageView.setImage(image);
        if (subText != null)
            this.subText.textProperty().setValue(subText);

        this.title.textProperty().setValue(title);
        this.text.textProperty().setValue(text);
    }

    public NotificationController(Image image, String title, String text) {
        this(image, title, text, null);
    }

    public NotificationController(String title, String text, String subText) {
        this(null, title, text, subText);
    }

    public NotificationController(String title, String text) {
        this(null, title, text, null);
    }

    public void onMouseClicked() {
        // TODO
    }
}
