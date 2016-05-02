package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
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
    private Test test;


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

        manageProjectPane = new ManageProjectPane();
        createActivityPane = new CreateActivityPane();

        VBox rightContainer = new VBox();
        rightContainer.getChildren().addAll(manageProjectPane, createActivityPane);

        this.setTop(titlePane);
        this.setRight(rightContainer);
        this.setBottom(exitBtnPane);

    }

    public void showProject(Test test) {

        this.test = test;
        this.toFront();

        this.titlePane.setText(test.name);
        this.manageProjectPane.setProject(test);

        ///////////////TEST//////////////////////////
        List<Test> testList = new ArrayList<>();
        for (int i = 1; i < 40; i++) {
            testList.add(new Test("Activity" + i, "22/4/16", "22/6/16", Integer.toString(i)));
        }
        ///////////////////////////////////////////

        activitiesListView = new ListView();
        ObservableList<Test> obsActivities = FXCollections.observableList(testList);
        activitiesListView.setItems(obsActivities);

        activitiesListView.setOnMouseClicked(e -> manageActivity(e));

        this.setCenter(activitiesListView);
    }

    private void close() {
        this.toBack();
    }

    private void manageActivity(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Test activity = (Test)activitiesListView.getSelectionModel().getSelectedItem();
            Stage manageActivityPopup = new ManageActivityPopup(pms, activity);

            manageActivityPopup.show();
        }
    }

}
