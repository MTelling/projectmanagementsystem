package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Activity;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Morten on 02/05/16.
 */
public class ProjectPane extends StandardPane {

    private ActivityPane activityPane;
    private ListView activitiesListView;
    private ManageProjectPane manageProjectPane;
    private CreateActivityPane createActivityPane;
    private Project project;

    private ProjectsPane projectsPane;
    private ObservableList<Activity> obsActivities;


    public ProjectPane(ActivityPane activityPane, PManagementSystem pms) {
        super(pms, true);

        this.activityPane = activityPane;

        manageProjectPane = new ManageProjectPane(pms);
        createActivityPane = new CreateActivityPane(pms, this);

        activitiesListView = new ListView();
        activitiesListView.setOnMouseClicked(e -> manageActivity(e));

        rightContainer.getChildren().addAll(manageProjectPane, createActivityPane);

        addTitleToCenterContainer("Activities");
        addNewExpandingChildToCenterContainer(activitiesListView);

    }



    public void setProject(Project project) {

        this.project = project;

        createActivityPane.setProject(project);

        obsActivities = FXCollections.observableList(project.getActivities());
        activitiesListView.setItems(obsActivities);

    }

    public void show() {
        update();
        this.toFront();
    }


    private void update() {
        this.titlePane.setText("Project Id: " + project.getId());
        this.manageProjectPane.setProject(project);

        refresh();
    }

    protected void close() {
        this.toBack();
        projectsPane.refresh();
    }

    private void manageActivity(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Activity activity = (Activity)activitiesListView.getSelectionModel().getSelectedItem();
            if (activity != null) {
                activityPane.setProjectPane(this);
                activityPane.setProject(project);
                activityPane.setActivity(activity);
                activityPane.toFront();
            }
        }
    }


    public void setProjectsPane(ProjectsPane projectsPane) {
        this.projectsPane = projectsPane;
    }

    public void refresh() {
        //TODO: Do this smarter.
        obsActivities = FXCollections.observableList(project.getActivities());
        activitiesListView.setItems(obsActivities);

        activitiesListView.refresh();
    }
}
