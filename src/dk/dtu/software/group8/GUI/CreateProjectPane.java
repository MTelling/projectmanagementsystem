package dk.dtu.software.group8.GUI;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;

/**
 * Created by Morten on 02/05/16.
 */
public class CreateProjectPane extends ControlPane {

    public CreateProjectPane() {
        super("Create Project");
        //Create the labels.
        Label nameLbl = new Label("Name:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");
        Label projectManagerLbl = new Label("Project Manager:");

        //Create text fields and date pickers.
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        //Create the labels.
        Label startDateLabel = new Label("Start Date:");
        Label endDateLabel = new Label("End Date:");


        //Add everything to the grid
        controlsGrid.add(startDateLabel, 0,0);
        controlsGrid.add(startDatePicker,1,0);
        controlsGrid.add(endDateLabel, 0, 1);
        controlsGrid.add(endDatePicker,1,1);

        //Create the button to create
        Button createProjectBtn = new Button("Create Project");
        this.addButton(createProjectBtn);
    }
}
