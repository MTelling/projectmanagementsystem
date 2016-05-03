package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Morten on 02/05/16.
 */
public class ManageActivityPopup extends Stage {

    private PManagementSystem pms;
    private Test activity;

    public ManageActivityPopup(PManagementSystem pms, Test activity) {

        this.pms = pms;
        this.activity = activity;

        //Set window properties.
        this.setTitle(activity.name);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);


        ManageActivityPane manageActivityPane = new ManageActivityPane();

        Scene scene = new Scene(manageActivityPane);

        //Connect scene to stylesheet
        scene.getStylesheets().add(this.getClass().getResource("layout.css").toExternalForm());

        this.setScene(scene);
        this.sizeToScene();
    }


}
