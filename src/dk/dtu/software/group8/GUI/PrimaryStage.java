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
    private ProjectActivityPane projectActivityPane;
    private PManagementSystem pms;
    private StackPane mainPane;

    private OverviewPane overviewPane;
    private ProjectsPane projectsPane;
    private ProjectPane projectPane;
    private RegisterHoursPane registerHoursPane;


    public PrimaryStage(PManagementSystem pms) {

        this.pms = pms;

        //Create the root pane.
        BorderPane root = new BorderPane();

        //Create the left menu.
        MenuPane menuPane = new MenuPane(this);

        //Create the center view
        mainPane = new StackPane();

        overviewPane = new OverviewPane(pms);
        projectActivityPane = new ProjectActivityPane(pms);
        projectPane = new ProjectPane(projectActivityPane, pms);
        projectsPane = new ProjectsPane(projectPane, pms);

        registerHoursPane = new RegisterHoursPane(pms);


        mainPane.getChildren().addAll(projectActivityPane,
                projectPane,
                registerHoursPane,
                projectsPane,
                overviewPane);

        //mainPane.getChildren().addAll(registerHoursPane, activitiesPane, projectsPane, overviewPane);

        root.setLeft(menuPane);
        root.setCenter(mainPane);

        //Make the scene.
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        //Connect scene to stylesheet
        scene.getStylesheets().add(this.getClass().getResource("layout.css").toExternalForm());

        //Stage settings.
        this.setTitle("Project Management System");
        this.setScene(scene);
        this.centerOnScreen();
        this.sizeToScene();
    }


    public void present(String sceneId) {
        if (sceneId.equals("Projects")) {
            projectsPane.toFront();
        } else if (sceneId.equals("Register Hours")){
            registerHoursPane.toFront();
        } else if (sceneId.equals("Overview")) {
            overviewPane.refresh();
            overviewPane.toFront();
        }
    }
}
