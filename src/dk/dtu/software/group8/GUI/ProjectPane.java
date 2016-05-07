package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import dk.dtu.software.group8.ProjectActivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Morten on 02/05/16.
 */
public class ProjectPane extends StandardPane {

    private ProjectActivityPane projectActivityPane;
    private ListView activitiesListView;
    private ManageProjectPane manageProjectPane;
    private CreateProjectActivityPane createProjectActivityPane;
    private Project project;

    private ProjectsPane projectsPane;
    private ObservableList<ProjectActivity> obsActivities;


    public ProjectPane(ProjectActivityPane projectActivityPane, PManagementSystem pms) {
        super(pms, true);

        this.projectActivityPane = projectActivityPane;

        manageProjectPane = new ManageProjectPane(pms);
        createProjectActivityPane = new CreateProjectActivityPane(pms, this);

        activitiesListView = new ListView();
        activitiesListView.setOnMouseClicked(e -> manageActivity(e));

        rightContainer.getChildren().addAll(manageProjectPane, createProjectActivityPane);

        addTitleToCenterContainer("Activities");
        addNewExpandingChildToCenterContainer(activitiesListView);

    }



    public void setProject(Project project) {

        this.project = project;

        createProjectActivityPane.setProject(project);

        refresh();

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
            ProjectActivity projectActivity = (ProjectActivity) activitiesListView.getSelectionModel().getSelectedItem();
            if (projectActivity != null) {
                projectActivityPane.setProjectPane(this);
                projectActivityPane.setProjectActivity(projectActivity);
                projectActivityPane.toFront();
            }
        }
    }


    public void setProjectsPane(ProjectsPane projectsPane) {
        this.projectsPane = projectsPane;
    }

    public void refresh() {
        obsActivities = FXCollections.observableList(project.getActivities());
        activitiesListView.setItems(obsActivities);

        activitiesListView.refresh();
    }
}
