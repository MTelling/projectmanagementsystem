package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Activity;
import dk.dtu.software.group8.PManagementSystem;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morten on 30/04/16.
 */
public class EmployeeActivitiesOverview extends BorderPane {
    private List<Activity> activityList;
    private TableView<Activity> table;

    public EmployeeActivitiesOverview(List<Activity> activityList) {
        this.activityList = activityList;
        StackPane titlePane = new TitlePane("Current activities", TitleFontSize.MEDIUM);

        createTable();
        setCenter(table);
        setTop(titlePane);
    }


    private void createTable() {

        //TODO: Should take the current activities from user from pms.
        ObservableList<Activity> obsActivities = FXCollections.observableList(activityList);
        //Create the table.
        table = new TableView();

        //Set uneditable and nonclickable.
        table.setEditable(false);
        table.setSelectionModel(null);

        //Create table columns
        TableColumn<Activity, String> nameCol = new TableColumn<>("Name");
        TableColumn<Activity, String> startDateCol = new TableColumn<>("Start Week");
        TableColumn<Activity, String> endDateCol = new TableColumn<>("End Week");
        TableColumn[] columns = {nameCol, startDateCol, endDateCol};

        //Set settings for the columns.
        for(TableColumn<Activity, String> column : columns) {
            column.setSortable(false);
            column.setResizable(false);
        }

        nameCol.prefWidthProperty().bind(table.widthProperty().divide(5).multiply(3));
        startDateCol.prefWidthProperty().bind(table.widthProperty().divide(5).multiply(1));
        endDateCol.prefWidthProperty().bind(table.widthProperty().divide(5).multiply(1));



        //Connect to model
        table.setItems(obsActivities);
        nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getActivityType()));
        startDateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStartWeek().toString()));
        endDateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEndWeek().toString()));

        //Add all columns to the table.
        table.getColumns().addAll(nameCol, startDateCol, endDateCol);


    }

    public void updateTable() {
        table.refresh();
    }

}
