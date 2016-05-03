package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.*;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by Morten on 02/05/16.
 */
public class ActivityPane extends BorderPane {

    private VBox rightContainer;
    private TitlePane titlePane;
    private ListView<Employee> assignedEmpListView;
    private PManagementSystem pms;
    private Activity activity;
    private ProjectPane projectPane;
    private ComboBox<Employee> employeeComboBox;

    //TODO: Change this when an activity has it's own project
    private Project project;

    public ActivityPane(PManagementSystem pms) {

        this.pms = pms;
        this.getStyleClass().add("ActivityPane");


        //Set window properties.
        titlePane = new TitlePane("N/A", TitleFontSize.LARGE);

        assignedEmpListView = new ListView<Employee>();

        //Create the exit button
        StackPane exitBtnPane = new StackPane();
        exitBtnPane.getStyleClass().add("ExitBtnPane");
        Button exitBtn = new Button("Go Back");
        exitBtnPane.getChildren().add(exitBtn);
        //Connect exit button to controls
        exitBtn.setOnMouseClicked(e -> close());

        //Put everything on this pane.
        this.setTop(titlePane);
        this.setCenter(assignedEmpListView);
        this.setBottom(exitBtnPane);

    }

    private void close() {
        projectPane.refresh();
        this.toBack();
    }

    private void setupRightContainer() {
        //This is the pane that can manage the activity.
        ManageActivityPane manageActivityPane = new ManageActivityPane(pms, activity);

        //This part adds the ability to add an employee.
        rightContainer = new VBox();
        rightContainer.getStyleClass().add("ActivityRightContainer");
        rightContainer.setAlignment(Pos.TOP_CENTER);
        TitlePane addEmployeeTitle = new TitlePane("Add employee to activity", TitleFontSize.MEDIUM);
        employeeComboBox = new ComboBox<>();
        ObservableList<Employee> obsAvailableEmployees = FXCollections.observableList(pms.getEmployees());
        employeeComboBox.setItems(obsAvailableEmployees);

        Button addEmployeeBtn = new Button("Add employee");
        addEmployeeBtn.setOnAction(e -> addEmployeeToActivity());

        //Add the ability to add an employee to the right.
        rightContainer.getChildren().addAll(manageActivityPane, addEmployeeTitle, employeeComboBox, addEmployeeBtn);
        this.setRight(rightContainer);
    }

    private void addEmployeeToActivity() {
        //TODO: Change this once an activity has it's project.

        Employee emp = employeeComboBox.getValue();
        try {

            pms.addEmployeeToActivity(project, (ProjectActivity) activity, emp);
            assignedEmpListView.refresh();

        } catch (NoAccessException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        } catch (TooManyActivitiesException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

    }

    public void setActivity(Activity activity) {
        this.activity = activity;

        //If the manage activity pane isn't on, set it on.
        if (this.getRight() == null) {
            setupRightContainer();
        }

        ObservableList<Employee> obsAssignedEmpList
                = FXCollections.observableList(((ProjectActivity) activity).getEmployees());

        assignedEmpListView.setItems(obsAssignedEmpList);

        titlePane.setText(activity.getActivityType());

        refresh();
    }

    public void refresh() {
        assignedEmpListView.refresh();
    }

    public void setProjectPane(ProjectPane projectPane) {
        this.projectPane = projectPane;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
