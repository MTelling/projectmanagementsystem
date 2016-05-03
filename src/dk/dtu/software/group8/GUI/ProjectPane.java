package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Activity;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morten on 02/05/16.
 */
public class ProjectPane extends BorderPane {

    private TitlePane titlePane;
    private PManagementSystem pms;
    private ListView activitiesListView;
    private ManageProjectPane manageProjectPane;
    private CreateActivityPane createActivityPane;
    private Project project;

    private ProjectsPane projectsPane;


    public ProjectPane(PManagementSystem pms) {
        this.pms = pms;
        this.getStyleClass().add("ProjectPane");

        //Create the exit button
        StackPane exitBtnPane = new StackPane();
        exitBtnPane.getStyleClass().add("ExitBtnPane");
        Button exitBtn = new Button("Go Back");
        exitBtnPane.getChildren().add(exitBtn);
        //Connect exit button to controls
        exitBtn.setOnMouseClicked(e -> close());

        //Create Title bar
        titlePane = new TitlePane("N/A", TitleFontSize.LARGE);

        manageProjectPane = new ManageProjectPane(pms);
        createActivityPane = new CreateActivityPane(pms, this);

        VBox rightContainer = new VBox();
        rightContainer.getChildren().addAll(manageProjectPane, createActivityPane);

        this.setTop(titlePane);
        this.setRight(rightContainer);
        this.setBottom(exitBtnPane);

    }

    public void showProject(Project project) {

        this.project = project;

        createActivityPane.setProject(project);

        activitiesListView = new ListView();
        ObservableList<Activity> obsActivities = FXCollections.observableList(project.getActivities());
        activitiesListView.setItems(obsActivities);

        activitiesListView.setOnMouseClicked(e -> manageActivity(e));

        update();

        this.setCenter(activitiesListView);

        this.toFront();
    }


    private void update() {
        this.titlePane.setText("Project Id: " + project.getId());
        this.manageProjectPane.setProject(project);

        activitiesListView.refresh();
    }

    private void close() {
        this.toBack();
        projectsPane.refresh();
    }

    private void manageActivity(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Activity activity = (Activity)activitiesListView.getSelectionModel().getSelectedItem();
            Stage manageActivityPopup = new ManageActivityPopup(pms, activity);

            manageActivityPopup.show();
        }
    }


    public void setProjectsPane(ProjectsPane projectsPane) {
        this.projectsPane = projectsPane;
    }

    public void refresh() {
        activitiesListView.refresh();
    }
}
