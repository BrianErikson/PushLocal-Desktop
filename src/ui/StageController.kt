package ui

import javafx.stage.Stage

/**
 * Created by brian on 8/29/15.
 */
class StageController {
    var stage: Stage? = null

    fun showStage() {
        if (this.stage != null) {
            this.stage!!.width = 1024.0
            this.stage!!.height = 768.0
            this.stage!!.show();
        }
    }
}
