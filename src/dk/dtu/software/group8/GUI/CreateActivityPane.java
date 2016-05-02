package dk.dtu.software.group8.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * Created by Morten on 02/05/16.
 */
public class CreateActivityPane extends ControlPane {

    public CreateActivityPane() {
        super("Create Activity");

        //Create the labels.
        Label typeLbl = new Label("Activity Type:");
        Label expectedHoursLbl = new Label("Expected Hours:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");

        //Create text fields and date pickers.
        TextField typeField = new TextField();
        TextField expectedHoursField = new TextField();

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();


        //Add everything to the grid
        controlsGrid.add(typeLbl, 0,0);
        controlsGrid.add(typeField, 1,0);
        controlsGrid.add(expectedHoursLbl, 0, 1);
        controlsGrid.add(expectedHoursField,1,1);
        controlsGrid.add(startDateLbl, 0,2);
        controlsGrid.add(startDatePicker,1,2);
        controlsGrid.add(endDateLbl, 0, 3);
        controlsGrid.add(endDatePicker,1,3);


        Button createBtn = new Button("Create Activity");
        this.addButton(createBtn);
    }
}
