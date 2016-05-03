package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import dk.dtu.software.group8.YearWeek;
import javafx.scene.control.*;

import java.time.Year;


/**
 * Created by Morten on 02/05/16.
 */
public class CreateActivityPane extends ControlPane {

    private TextField expectedHoursField;
    private TextField typeField;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ProjectPane projectPane;
    private Project project;

    public CreateActivityPane(PManagementSystem pms, ProjectPane projectPane) {
        super(pms, "Create Activity");
        this.projectPane = projectPane;

        //Create the labels.
        Label typeLbl = new Label("Activity Type:");
        Label expectedHoursLbl = new Label("Expected Hours:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");

        //Create text fields and date pickers.
        typeField = new TextField();
        expectedHoursField = new TextField();

        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();


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


        //Connect button to control
        createBtn.setOnAction(e -> createActivity());

        this.addButton(createBtn);
    }

    private void createActivity() {
        try {
            pms.createActivityForProject(project,
                    typeField.getText(),
                    YearWeek.fromDate(startDatePicker.getValue()),
                    YearWeek.fromDate(endDatePicker.getValue()),
                    Integer.parseInt(expectedHoursField.getText()));


            projectPane.refresh();

        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
