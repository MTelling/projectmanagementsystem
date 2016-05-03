package dk.dtu.software.group8.GUI;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * Created by Morten on 03/05/16.
 */
public class SuccessPrompt extends Alert {

    public SuccessPrompt() {
        super(AlertType.INFORMATION);

        this.setTitle("Success!");
        this.setHeaderText(null);
        this.setContentText("Data saved!");
        this.initModality(Modality.APPLICATION_MODAL);
    }
}
