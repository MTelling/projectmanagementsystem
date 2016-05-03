package dk.dtu.software.group8.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;


/**
 * Created by Morten on 02/05/16.
 */
public class ManageProjectPane extends ControlPane {

    private Test test;

    private TextField nameField;

    public void setProject(Test test) {
        this.test = test;

        titlePane.setText("Manage Project");
        nameField.setText(test.name);

    }

    public ManageProjectPane() {
        super("N/A");

        //Create the labels.
        Label nameLbl = new Label("Name:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");
        Label projectManagerLbl = new Label("Project Manager:");

        //Create text fields and date pickers.
        nameField = new TextField();
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        Button assignProjectManagerBtn = new Button("Make Me Project Manager");
        assignProjectManagerBtn.setTooltip(new Tooltip("This will make you the project manager."));

        //Add everything to the grid
        controlsGrid.add(nameLbl, 0,0);
        controlsGrid.add(nameField,1,0);
        controlsGrid.add(startDateLbl, 0,1);
        controlsGrid.add(startDatePicker,1,1);
        controlsGrid.add(endDateLbl, 0, 2);
        controlsGrid.add(endDatePicker,1,2);
        controlsGrid.add(projectManagerLbl, 0, 3);
        controlsGrid.add(assignProjectManagerBtn,1,3);


        Button endBtn = new Button("End Project");
        Button saveBtn = new Button("Save Project");

        this.addButton(endBtn);
        this.addButton(saveBtn);

    }

    public void update(Test test) {
        this.test = test;

        nameField.setText(test.name);
        titlePane.setText(test.name);
    }



}
