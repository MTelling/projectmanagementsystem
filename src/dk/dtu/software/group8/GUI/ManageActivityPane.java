package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.*;
import javafx.scene.control.*;

import java.time.Year;

/**
 * Created by Morten on 02/05/16.
 */
public class ManageActivityPane extends ControlPane {

    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private TextField expectedHoursField;
    private Activity activity;

    public ManageActivityPane(PManagementSystem pms, Activity activity) {
        super(pms, "Manage Activity");
        this.activity = activity;

        //Create the labels.
        Label typeLbl = new Label("Activity Type:");
        Label expectedHoursLbl = new Label("Expected Hours:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");

        //Create text fields and date pickers.
        Label typeField = new Label(activity.getActivityType());

        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();


        //Add everything to the grid.
        controlsGrid.add(typeLbl, 0,0);
        controlsGrid.add(typeField, 1,0);

        controlsGrid.add(startDateLbl, 0,2);
        controlsGrid.add(startDatePicker,1,2);
        controlsGrid.add(endDateLbl, 0, 3);
        controlsGrid.add(endDatePicker,1,3);


        if (activity instanceof ProjectActivity) {
            expectedHoursField = new TextField();

            controlsGrid.add(expectedHoursLbl, 0, 1);
            controlsGrid.add(expectedHoursField,1,1);
        }

        Button endBtn = new Button("End Activity");
        Button saveBtn = new Button("Save Activity");

        //Connect controls to buttons
        saveBtn.setOnAction(e -> saveActivity());

        this.addButton(endBtn);
        this.addButton(saveBtn);

        this.update();
    }

    private void saveActivity() {
        if (activity instanceof ProjectActivity) {

            try {

                pms.manageActivityDates(((ProjectActivity) activity).getProject(),
                        activity,
                        YearWeek.fromDate(startDatePicker.getValue()),
                        YearWeek.fromDate(endDatePicker.getValue()));



            } catch (Exception e) {
                Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                error.showAndWait();
            }

            //TODO: Set expected hours.
            //TODO: Should projectpane be refreshed?
            //projectPane.refresh();

        }


    }

    private void update() {
        if (activity instanceof ProjectActivity) {
            expectedHoursField.setText(Integer.toString(((ProjectActivity) activity).getApproximatedHours()));
        }
    }

}
