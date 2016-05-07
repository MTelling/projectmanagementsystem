package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import dk.dtu.software.group8.ProjectActivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ProjectPane extends StandardPane {

    private ProjectActivityPane projectActivityPane;
    private ListView activitiesListView;
    private ManageProjectPane manageProjectPane;
    private CreateProjectActivityPane createProjectActivityPane;
    private Project project;

    private ProjectsPane projectsPane;
    private ObservableList<ProjectActivity> obsActivities;

    /**
     * Created by Tobias
     */
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

    /**
     * Created by Marcus
     */
    public void setProject(Project project) {

        this.project = project;

        createProjectActivityPane.setProject(project);

        refresh();
    }

    /**
     * Created by Morten
     */
    public void show() {
        update();
        this.toFront();
    }

    /**
     * Created by Tobias
     */
    private void update() {
        this.titlePane.setText("Project Id: " + project.getId());
        this.manageProjectPane.setProject(project);

        refresh();
    }

    /**
     * Created by Marcus
     */
    protected void close() {
        this.toBack();
        projectsPane.refresh();
    }

    /**
     * Created by Morten
     */
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

    /**
     * Created by Tobias
     */
    public void setProjectsPane(ProjectsPane projectsPane) {
        this.projectsPane = projectsPane;
    }

    /**
     * Created by Marcus
     */
    public void refresh() {
        obsActivities = FXCollections.observableList(project.getActivities());
        activitiesListView.setItems(obsActivities);

        activitiesListView.refresh();
    }
}