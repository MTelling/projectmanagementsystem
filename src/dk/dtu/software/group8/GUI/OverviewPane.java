package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OverviewPane extends BorderPane {

    private PManagementSystem pms;

    public OverviewPane(PManagementSystem pms) {
        this.pms = pms;

        //Create the activities overview
        BorderPane activitiesOverview = activitiesOverview();

        //Place on the mother pane.
        this.setCenter(activitiesOverview);

        //Remove focus color. Otherwise there's going to be a blue bar around on osx.
        this.setStyle("-fx-focus-color: transparent;");
    }

    private BorderPane activitiesOverview() {

        BorderPane activitiesOverview = new BorderPane();

        ScrollPane scrollPane = new ScrollPane();

        //Settings for the scrollpane.
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        //Create the overlying vbox containing each activity.
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);

        //Create the constraints for the GridPanes presenting activities
        //We do it here, so the app doesn't have to make 20 of these.
        ColumnConstraints consOne = new ColumnConstraints();
        consOne.setPercentWidth(40);
        ColumnConstraints consTwo = new ColumnConstraints();
        consTwo.setPercentWidth(30);
        ColumnConstraints consThree = new ColumnConstraints();
        consThree.setPercentWidth(30);
        ColumnConstraints[] constraints = {consOne, consTwo, consThree};

        //TODO: This should be for activity in users activities.
        for (int i = 0; i<20; i++) {
            vbox.getChildren().add(threeItemGrid("Implementation", "22/4-2016", "16/5-2016", constraints, false));
        }

        //Set the vbox as the scrollpanes content
        scrollPane.setContent(vbox);

        //Add this to the activitiesOverview as well as a title bar.
        activitiesOverview.setTop(threeItemGrid("Activity", "Start Date", "End Date", constraints, true));
        activitiesOverview.setCenter(scrollPane);

        return activitiesOverview;
    }

    private GridPane threeItemGrid(String col1, String col2, String col3, ColumnConstraints[] constraints, boolean bold) {
        //Create the grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(8,20,0,20));

        //Add the constraints
        grid.getColumnConstraints().addAll(constraints);

        //Add three pretty labels
        grid.add(prettyLabel(col1, bold), 0, 0);
        grid.add(prettyLabel(col2, bold), 1, 0);
        grid.add(prettyLabel(col3, bold), 2, 0);

        return grid;
    }

    private Label prettyLabel(String text, boolean bold) {
        Label label = new Label(text);
        if (bold) {
            label.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        } else {
            label.setFont(Font.font("Verdana", 16));
        }

        return label;
    }

}
