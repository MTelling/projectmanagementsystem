package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Created by Morten on 25/04/16.
 */
public class ProjectsPane extends BorderPane {

    private ProjectPane projectPane;
    private PManagementSystem pms;
    private ListView projectListView;

    public ProjectsPane(ProjectPane projectPane, PManagementSystem pms) {

        this.projectPane = projectPane;
        this.pms = pms;

        projectPane.setProjectsPane(this);

        this.getStyleClass().add("ProjectsPane");

        StackPane titlePane = new TitlePane("Projects", TitleFontSize.LARGE);

        projectListView = new ListView();
        ObservableList<Project> obsProjects = FXCollections.observableList(pms.getProjects());
        projectListView.setItems(obsProjects);
        projectListView.setOnMouseClicked(e -> openProject(e));

        CreateProjectPane createProjectPane = new CreateProjectPane(pms, this);

        this.setTop(titlePane);
        this.setCenter(projectListView);
        this.setRight(createProjectPane);

    }

    public void refresh() {
        projectListView.refresh();
    }

    private void openProject(MouseEvent e) {
        if (e.getClickCount() == 2) {
            projectPane.setProject((Project)projectListView.getSelectionModel().getSelectedItem());
            projectPane.show();
        }
    }

}
