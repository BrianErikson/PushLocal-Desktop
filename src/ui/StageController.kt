package ui

import javafx.stage.Stage

/**
 * Created by brian on 8/29/15.
 */
class StageController {
    var stage: Stage? = null
    get() {
        return field
    }
    set(value) {
        field = value
        field?.show()
    }

    fun showStage() {
        if (this.stage != null) {
            this.stage!!.width = 1024.0
            this.stage!!.height = 768.0
            this.stage!!.isMaximized = true
            this.stage!!.show();
        }
    }
}
