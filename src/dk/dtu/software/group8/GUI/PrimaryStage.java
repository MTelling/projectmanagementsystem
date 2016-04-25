package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Morten on 25/04/16.
 */
public class PrimaryStage extends Stage{

    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    private PManagementSystem pms;
    private StackPane mainPane;

    private OverviewPane overviewPane;
    private ProjectsPane projectsPane;
    private RegisterHoursPane registerHoursPane;
    private ActivitiesPane activitiesPane;


    public PrimaryStage(PManagementSystem pms) {

        this.pms = pms;

        //Create the root pane.
        BorderPane root = new BorderPane();

        //Create the left menu.
        MenuPane menuPane = new MenuPane(this);

        //Create the center view
        mainPane = new StackPane();

        overviewPane = new OverviewPane(pms);

        mainPane.getChildren().add(overviewPane);

        //mainPane.getChildren().addAll(registerHoursPane, activitiesPane, projectsPane, overviewPane);

        root.setLeft(menuPane);
        root.setCenter(mainPane);

        //Make the scene.
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        //Stage settings.
        this.setTitle("Project Management System");
        this.setScene(scene);
        this.setResizable(false);
        this.centerOnScreen();
        this.sizeToScene();
    }

    public void update() {
        //TODO: This can raise a NullPointerException. Should we catch? It shouldn't be a problem if login works.
    }

    public void present(String sceneId) {
        System.out.println("Got here with: " + sceneId);
        if (sceneId.equals("Activities")) {
            activitiesPane.toFront();
        } else if (sceneId.equals("Projects")) {
            projectsPane.toFront();
        } else if (sceneId.equals("Register Hours")){
            registerHoursPane.toFront();
        } else if (sceneId.equals("Overview")) {
            overviewPane.toFront();
        }
    }
}
