package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivitiesOverview extends BorderPane {
    private PManagementSystem pms;
    private TableView<Test> table;

    public EmployeeActivitiesOverview(PManagementSystem pms) {
        this.pms = pms;

        StackPane titlePane = new TitlePane("Current activities", TitleFontSize.MEDIUM);

        createTable();
        setCenter(table);
        setTop(titlePane);
    }


    private void createTable() {

        ///////////////TEST//////////////////////////
        List<Test> testList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            testList.add(new Test("test", "22/4/16", "22/6/16", Integer.toString(i)));
        }
        ///////////////////////////////////////////

        //TODO: Should take the current activities from user from pms.
        ObservableList<Test> observableActivities = FXCollections.observableList(testList);
        //Create the table.
        table = new TableView();

        //Set uneditable and nonclickable.
        table.setEditable(false);
        table.setSelectionModel(null);

        //Create table columns
        TableColumn<Test, String> nameCol = new TableColumn<>("Name");
        TableColumn<Test, String> startDateCol = new TableColumn<>("Start Date");
        TableColumn<Test, String> endDateCol = new TableColumn<>("End Date");
        TableColumn<Test, String> originProjectCol = new TableColumn<>("Associated Project");
        TableColumn[] columns = {nameCol, startDateCol, endDateCol, originProjectCol};

        //Set settings for the columns.
        for(TableColumn<Test, String> column : columns) {
            column.setSortable(false);
            column.setResizable(false);
        }

        nameCol.prefWidthProperty().bind(table.widthProperty().divide(9).multiply(4));
        startDateCol.prefWidthProperty().bind(table.widthProperty().divide(9).multiply(1));
        endDateCol.prefWidthProperty().bind(table.widthProperty().divide(9).multiply(1));
        originProjectCol.prefWidthProperty().bind(table.widthProperty().divide(9).multiply(3));



        //Connect to model
        table.setItems(observableActivities);
        nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().name));
        startDateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().startDate));
        endDateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().endDate));
        originProjectCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().originProject));

        //Add all columns to the table.
        table.getColumns().addAll(nameCol, startDateCol, endDateCol, originProjectCol);


    }

    public void updateTable() {
        table.refresh();
    }

}

//TODO: Delete this.
class Test {
    public String name;
    public String startDate;
    public String endDate;
    public String originProject;

    public Test(String name, String startDate, String endDate, String originProject) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.originProject = originProject;
    }

    public String toString() {
        return name + " (" + startDate + " - " + endDate + ") " + originProject;
    }

}