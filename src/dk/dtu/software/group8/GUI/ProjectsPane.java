package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morten on 25/04/16.
 */
public class ProjectsPane extends BorderPane {

    private ProjectPane projectPane;
    private PManagementSystem pms;
    private ListView projectListView;

    public ProjectsPane(PrimaryStage primaryStage, ProjectPane projectPane) {

        this.projectPane = projectPane;

        this.getStyleClass().add("ProjectsPane");

        StackPane titlePane = new TitlePane("Projects", TitleFontSize.LARGE);
        ///////////////TEST//////////////////////////
        List<Test> testList = new ArrayList<>();
        for (int i = 1; i < 40; i++) {
            testList.add(new Test("Test" + i, "22/4/16", "22/6/16", Integer.toString(i)));
        }
        ///////////////////////////////////////////

        projectListView = new ListView();
        ObservableList<Test> obsProjects = FXCollections.observableList(testList);
        projectListView.setItems(obsProjects);
        projectListView.setOnMouseClicked(e -> openProject(e));

        CreateProjectPane createProjectPane = new CreateProjectPane();

        this.setTop(titlePane);
        this.setCenter(projectListView);
        this.setRight(createProjectPane);

    }


    private void openProject(MouseEvent e) {
        if (e.getClickCount() == 2) {
            projectPane.showProject((Test)projectListView.getSelectionModel().getSelectedItem());
        }
    }

}
