package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.NegativeHoursException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.ProjectActivity;
import dk.dtu.software.group8.YearWeek;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by Morten on 02/05/16.
 */
public class ManageActivityPane extends ControlPane {


    private final TextField startWeekField;
    private final TextField startYearField;
    private final TextField endWeekField;
    private final TextField endYearField;
    private Label typeField;
    private TextField expectedHoursField;
    private ProjectActivity activity;

    public ManageActivityPane(PManagementSystem pms, ProjectActivity activity) {
        super(pms, "Manage Activity");
        this.activity = activity;

        //Create the labels.
        Label typeLbl = new Label("Activity Type:");
        Label expectedHoursLbl = new Label("Expected Hours:");
        Label startWeekLbl = new Label("Start Week:");
        Label startYearLbl = new Label("Start Year:");
        Label endWeekLbl = new Label("End Week:");
        Label endYearLbl = new Label("End Year:");

        //Create text fields and date pickers.
        typeField = new Label(activity.getActivityType());

        startWeekField = new TextField();
        startYearField = new TextField();
        endWeekField = new TextField();
        endYearField = new TextField();
        expectedHoursField = new TextField();



        //Add everything to the grid.
        controlsGrid.add(typeLbl, 0,0);
        controlsGrid.add(typeField, 1,0);
        controlsGrid.add(expectedHoursLbl, 0, 1);
        controlsGrid.add(expectedHoursField,1,1);
        controlsGrid.add(startWeekLbl, 0,2);
        controlsGrid.add(startWeekField, 1, 2);
        controlsGrid.add(startYearLbl,0,3);
        controlsGrid.add(startYearField,1,3);
        controlsGrid.add(endWeekLbl, 0, 4);
        controlsGrid.add(endWeekField,1,4);
        controlsGrid.add(endYearLbl, 0, 5);
        controlsGrid.add(endYearField,1,5);

        Button saveBtn = new Button("Save Activity");

        //Connect controls to buttons
        saveBtn.setOnAction(e -> saveActivity());

        this.addButton(saveBtn);
    }


    public void setActivity(ProjectActivity activity) {
        this.activity = activity;
    }


    private void saveActivity() {

        try {
            YearWeek startWeek = new YearWeek(Integer.parseInt(startYearField.getText()),
                    Integer.parseInt(startWeekField.getText()));

            YearWeek endWeek = new YearWeek(Integer.parseInt(endYearField.getText()),
                    Integer.parseInt(endWeekField.getText()));

            pms.manageActivityDates(activity, startWeek, endWeek);


        } catch (IncorrectAttributeException | NoAccessException | WrongDateException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION,
                    "You can only type weeks and years as numbers!");
            error.showAndWait();
        }

        try {
            int expectedHours = Integer.parseInt(expectedHoursField.getText());

            pms.changeActivityApproximatedHours(activity, expectedHours);
        } catch (NegativeHoursException | NoAccessException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION,
                    "You can only type numbers as expected hours!");
            error.showAndWait();
        }

    }

    public void refresh() {
        expectedHoursField.setText(Integer.toString(activity.getApproximatedHours()));
        startWeekField.setText(Integer.toString(activity.getStartWeek()));
        startYearField.setText(Integer.toString(activity.getStartYear()));

        endWeekField.setText(Integer.toString(activity.getEndWeek()));
        endYearField.setText(Integer.toString(activity.getEndYear()));

        typeField.setText(activity.getActivityType());

    }

}
