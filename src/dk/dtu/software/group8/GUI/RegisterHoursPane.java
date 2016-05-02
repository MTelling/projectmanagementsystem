package dk.dtu.software.group8.GUI;

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
public class RegisterHoursPane extends BorderPane {


    private PManagementSystem pms;

    public RegisterHoursPane(PManagementSystem pms) {
        this.getStyleClass().add("RegisterHoursPane");
        this.pms = pms;

        TitlePane titlePane = new TitlePane("Register Hours", TitleFontSize.LARGE);

        //Create the center items. First two containers.
        VBox centerContainer = new VBox();
        centerContainer.getStyleClass().add("Container");
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
        VBox.setVgrow(activitiesList, Priority.ALWAYS);

        //Add all children to the center container
        centerContainer.getChildren().addAll(datePickContainer, activitiesList);

        //Create the right view for adding hours and viewing total hours.
        ControlHoursPane controlHoursPane = new ControlHoursPane();

        //////////////////////////TEST //////////////////////////

        List<Test> testList = new ArrayList<Test>();
        for (int i = 0; i < 20; i++) {
            testList.add(new Test("Activity" + i, "22/4/16", "22/5/16", "test"));
        }

        ObservableList<Test> obsActivities = FXCollections.observableList(testList);

        activitiesList.setItems(obsActivities);

        //////////////////////////////////////////////////////////


        this.setCenter(centerContainer);
        this.setTop(titlePane);
        this.setRight(controlHoursPane);

    }
}
