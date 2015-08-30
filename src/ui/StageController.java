package ui;

import javafx.stage.Stage;

/**
 * Created by brian on 8/29/15.
 */
public class StageController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
