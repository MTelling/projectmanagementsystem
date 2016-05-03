package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Activity;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by Morten on 30/04/16.
 */
public class EmployeeActivitiesTableView extends TableView {

    public EmployeeActivitiesTableView(List<Activity> activityList) {
        //TODO: Should take the current activities from user from pms.
        ObservableList<Activity> obsActivities = FXCollections.observableList(activityList);

        //Set uneditable and nonclickable.
        this.setEditable(false);
        this.setSelectionModel(null);

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

        //Set size for individual columns
        nameCol.prefWidthProperty().bind(this.widthProperty().divide(5).multiply(3));
        startDateCol.prefWidthProperty().bind(this.widthProperty().divide(5).multiply(1));
        endDateCol.prefWidthProperty().bind(this.widthProperty().divide(5).multiply(1));

        //Connect to model
        this.setItems(obsActivities);
        nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getActivityType()));
        startDateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStartDate().toString()));
        endDateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEndDate().toString()));

        //Add all columns to the table.
        this.getColumns().addAll(nameCol, startDateCol, endDateCol);

    }


}
