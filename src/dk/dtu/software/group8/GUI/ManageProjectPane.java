package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import javafx.scene.control.*;

import java.util.Optional;

/**
 * Created by Marcus
 */
public class ManageProjectPane extends ControlPane {

    private DatePicker endDatePicker;
    private DatePicker startDatePicker;
    private Label projectManager;
    private Button assignProjectManagerBtn;
    private Project project;

    private TextField nameField;

    /**
     * Created by Morten
     */
    public ManageProjectPane(PManagementSystem pms) {
        super(pms, "N/A");
        //Create the labels.
        Label nameLbl = new Label("Name:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");
        Label projectManagerLbl = new Label("Project Manager:");

        //Create text fields and date pickers.
        nameField = new TextField();
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();

        projectManager = new Label("N/A");

        assignProjectManagerBtn = new Button("Make Me Project Manager");
        assignProjectManagerBtn.setTooltip(new Tooltip("This will make you the project manager."));

        //Add everything to the grid
        controlsGrid.add(nameLbl, 0,0);
        controlsGrid.add(nameField,1,0);
        controlsGrid.add(startDateLbl, 0,1);
        controlsGrid.add(startDatePicker,1,1);
        controlsGrid.add(endDateLbl, 0, 2);
        controlsGrid.add(endDatePicker,1,2);
        controlsGrid.add(projectManagerLbl, 0, 3);
        controlsGrid.add(projectManager, 1,3);
        controlsGrid.add(assignProjectManagerBtn,1,3);


        Button endBtn = new Button("End Project");
        Button saveBtn = new Button("Save Project");

        //Connect buttons to controls
        saveBtn.setOnAction(e -> save());
        assignProjectManagerBtn.setOnAction(e -> setProjectManager());

        this.addButton(endBtn);
        this.addButton(saveBtn);

    }

    /**
     * Created by Tobias
     */
    private void update() {
        nameField.setText(project.getName());

        startDatePicker.setValue(project.getStartDate());
        endDatePicker.setValue(project.getEndDate());

        //Set the project manager field.
        if (project.getProjectManager() != null) {
            projectManager.setText(project.getProjectManager().getFirstName() + " "
                    + project.getProjectManager().getLastName());

            projectManager.setVisible(true);
            assignProjectManagerBtn.setVisible(false);
        } else { //Show the button to make yourself project manager if there is none.

            assignProjectManagerBtn.setVisible(true);
            projectManager.setVisible(false);

        }
    }

    /**
     * Created by Marcus
     */
    public void setProject(Project project) {
        this.project = project;

        titlePane.setText("Manage Project");

        update();
    }

    /**
     * Created by Morten
     */
    private void save() {
        try {

            pms.changeNameOfProject(project, nameField.getText());
            pms.manageProjectDates(project, startDatePicker.getValue(), endDatePicker.getValue());

            Alert success = new SuccessPrompt();
            success.showAndWait();

        } catch (NoAccessException e) {

            //If the emp is not project manager, give the emp the ability to make himself that.
            if (project.getProjectManager() == null) {
                Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                dialog.setHeaderText(e.getMessage());
                dialog.setContentText("Do you want to be the project manager?");
                dialog.setTitle("Error!");

                Optional<ButtonType> result = dialog.showAndWait();

                //If the user chooses to be a project manager, he should be made one.
                if (result.get() == ButtonType.OK) {

                    try {
                        pms.assignManagerToProject(project);
                    } catch (AlreadyAssignedProjectManagerException e1) {
                        Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                        error.showAndWait();
                    }

                    //and the values entered should be saved.
                    save();
                }


            } else { //If there already is a project manager, just show default error message.
                Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                error.showAndWait();
            }
        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        update();
    }

    /**
     * Created by Tobias
     */
    private void setProjectManager(){
        try {
            pms.assignManagerToProject(project);
        } catch (AlreadyAssignedProjectManagerException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        update();
    }
}