package ui;

import javafx.stage.Stage;

/**
 * Created by brian on 8/29/15.
 */
public class StageController {

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.show();
    }

    public void showStage() {
        this.stage.setWidth(1024);
        this.stage.setHeight(768);
        this.stage.setMaximized(true);
    }
}
