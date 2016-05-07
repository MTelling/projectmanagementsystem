package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Morten on 25/04/16.
 */
public class ProjectsPane extends StandardPane {

    private ProjectPane projectPane;
    private ListView projectListView;

    public ProjectsPane(ProjectPane projectPane, PManagementSystem pms) {
        super(pms, false);

        titlePane.setText("Projects");

        this.projectPane = projectPane;

        projectPane.setProjectsPane(this);

        projectListView = new ListView();
        ObservableList<Project> obsProjects = FXCollections.observableList(pms.getProjects());
        projectListView.setItems(obsProjects);

        projectListView.setOnMouseClicked(e -> openProject(e));

        CreateProjectPane createProjectPane = new CreateProjectPane(pms, this);


        //Add all elements to the pane.
        rightContainer.getChildren().add(createProjectPane);
        addNewExpandingChildToCenterContainer(projectListView);

    }

    public void refresh() {
        ObservableList<Project> obsProjects = FXCollections.observableList(pms.getProjects());
        projectListView.setItems(obsProjects);
        projectListView.refresh();
    }

    private void openProject(MouseEvent e) {
        if (e.getClickCount() == 2) {
            projectPane.setProject((Project)projectListView.getSelectionModel().getSelectedItem());
            projectPane.show();
        }
    }

    @Override
    protected void close() {
        this.toBack();
    }
}
