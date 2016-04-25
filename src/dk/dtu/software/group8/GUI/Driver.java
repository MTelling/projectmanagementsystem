package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Morten on 25/04/16.
 */
public class Driver extends Application {

    private PManagementSystem pms;
    private PrimaryStage primaryStage;

    public static void main(String[] args) {

        Application.launch(args);
    }

    public void start(Stage loginStage) {

        //Create an instance of the pms
        this.pms = new PManagementSystem();

        //Create the primary stage for later
        primaryStage = new PrimaryStage(pms);

        //Create the login stage
        loginStage = new LoginStage(pms, primaryStage);

        //To start just show the loginstage.
        loginStage.show();
    }


}
