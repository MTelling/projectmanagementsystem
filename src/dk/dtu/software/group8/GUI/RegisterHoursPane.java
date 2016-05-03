package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Activity;
import dk.dtu.software.group8.PManagementSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morten on 25/04/16.
 */
public class RegisterHoursPane extends StandardPane {


    private PManagementSystem pms;

    public RegisterHoursPane(PManagementSystem pms) {
        super(pms, false);

        this.getStyleClass().add("RegisterHoursPane");

        titlePane.setText("Register Hours");

        //Create the center items. First two containers.
        HBox datePickContainer = new HBox();
        datePickContainer.getStyleClass().add("Container");
        datePickContainer.setAlignment(Pos.CENTER_LEFT);

        //Create the datepicker
        Label datePickLbl = new Label("Choose a day:");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePickContainer.getChildren().addAll(datePickLbl, datePicker);

        //Create the listview and make sure it fills the height.
        ListView activitiesList = new ListView();

        //Create the right view for adding hours and viewing total hours.
        ControlHoursPane controlHoursPane = new ControlHoursPane(pms);

        //TODO: This should get the activity for the given day.
        ObservableList<Activity> obsActivities = FXCollections.observableList(pms.getCurrentEmployee().getCurrentActivities());

        activitiesList.setItems(obsActivities);

        //Add all children to the center and right container.
        centerContainer.getChildren().add(datePickContainer);
        addNewExpandingChildToCenterContainer(activitiesList);
        rightContainer.getChildren().add(controlHoursPane);



    }

    @Override
    protected void close() {
        toBack();
    }
}
