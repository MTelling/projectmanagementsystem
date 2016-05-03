package dk.dtu.software.group8.GUI;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * Created by Morten on 03/05/16.
 */
public class ErrorPrompt extends Alert {

    public ErrorPrompt(AlertType alertType, String msg) {
        super(alertType);

        this.setTitle("Error!");
        this.setHeaderText(null);
        this.setGraphic(null);
        this.setContentText(msg);

        this.initModality(Modality.APPLICATION_MODAL);


    }
}
