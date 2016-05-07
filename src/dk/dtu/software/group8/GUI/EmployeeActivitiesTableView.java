package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Activity;
import dk.dtu.software.group8.ProjectActivity;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by Marcus
 */
public class EmployeeActivitiesTableView extends TableView {

    public EmployeeActivitiesTableView(List<ProjectActivity> activityList) {
        ObservableList<ProjectActivity> obsActivities = FXCollections.observableList(activityList);

        //Set uneditable and nonclickable.
        this.setEditable(false);
        this.setSelectionModel(null);

        //Create table columns
        TableColumn<ProjectActivity, String> nameCol = new TableColumn<>("Name");
        TableColumn<ProjectActivity, String> startDateCol = new TableColumn<>("Start Week");
        TableColumn<ProjectActivity, String> endDateCol = new TableColumn<>("End Week");
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

        startDateCol.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(Integer.toString(data.getValue().getStartWeek())
                                + "."
                                + Integer.toString(data.getValue().getStartYear())));

        endDateCol.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(Integer.toString(data.getValue().getEndWeek())
                                + "."
                                + Integer.toString(data.getValue().getEndYear())));

        //Add all columns to the table.
        this.getColumns().addAll(nameCol, startDateCol, endDateCol);
    }
}