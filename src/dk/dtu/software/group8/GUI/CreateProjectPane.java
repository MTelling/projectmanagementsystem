package dk.dtu.software.group8.GUI;


import dk.dtu.software.group8.Exceptions.WrongDateException;
import dk.dtu.software.group8.PManagementSystem;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * Created by Morten
 */
public class CreateProjectPane extends ControlPane {

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ProjectsPane projectsPane;

    /**
     * Created by Tobias
     */
    public CreateProjectPane(PManagementSystem pms, ProjectsPane projectsPane) {
        super(pms, "Create Project");
        this.projectsPane = projectsPane;

        //Create the labels.
        Label nameLbl = new Label("Name:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");
        Label projectManagerLbl = new Label("Project Manager:");

        //Create text fields and date pickers.
        startDatePicker = new DatePicker();
        startDatePicker.setId("createProjectStartDatePicker");
        endDatePicker = new DatePicker();
        endDatePicker.setId("createProjectEndDatePicker");

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
        createProjectBtn.setId("createProjectBtn");
        this.addButton(createProjectBtn);

        //Connect button to controls
        createProjectBtn.setOnAction(e -> createProject());
    }

    /**
     * Created by Marcus
     */
    private void createProject() {
        try {

            pms.createProject(startDatePicker.getValue(), endDatePicker.getValue());

            Alert success = new SuccessPrompt();

            projectsPane.refresh();
            success.showAndWait();


        } catch (WrongDateException e) {

            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();

        }
    }
}