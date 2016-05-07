package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Employee;
import dk.dtu.software.group8.Exceptions.*;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.ProjectActivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 * Created by Tobias
 */
public class ProjectActivityPane extends StandardPane {

    private ListView consultantsListView;
    private ProjectActivity projectActivity;
    private ProjectPane projectPane;
    private ComboBox<Employee> employeeComboBox;
    private ListView assignedEmpListView;
    private ObservableList<Employee> obsAssignedEmpList;
    private boolean setupRight;

    private ObservableList<Employee> obsConsultants;
    private ObservableList<Employee> obsAvailableEmployees;
    private ManageActivityPane manageActivityPane;

    /**
     * Created by Tobias
     */
    public ProjectActivityPane(PManagementSystem pms) {
        super(pms, true);
        setupRight = false;

        assignedEmpListView = new ListView();
        consultantsListView = new ListView();

        addTitleToCenterContainer("Employees");
        addNewExpandingChildToCenterContainer(assignedEmpListView);
        addTitleToCenterContainer("Consultants");
        addNewExpandingChildToCenterContainer(consultantsListView);
    }

    /**
     * Created by Marcus
     */
    @Override
    protected void close() {
        projectPane.refresh();
        this.toBack();
    }

    /**
     * Created by Morten
     */
    private void setupRightContainer() {
        //This is the pane that can manage the projectActivity.
        manageActivityPane = new ManageActivityPane(pms, projectActivity);

        //Create the tools for adding an employee to the activity
        TitlePane addEmployeeTitle = new TitlePane("Add employee to activity", TitleFontSize.MEDIUM);
        employeeComboBox = new ComboBox<>();

        obsAvailableEmployees = null;

        updateAvailableEmployeeList();

        Button addEmployeeBtn = new Button("Add employee");
        Button addEmployeeAsConsultantBtn = new Button("Add employee as consultant");

        addEmployeeBtn.setOnAction(e -> addEmployeeToActivity());
        addEmployeeAsConsultantBtn.setOnAction(e -> addEmployeeToActivityAsConsultant());

        //Add the ability to add an employee to the right.
        rightContainer.getChildren().addAll(manageActivityPane, addEmployeeTitle, employeeComboBox,
                addEmployeeBtn, addEmployeeAsConsultantBtn);
    }

    /**
     * Created by Tobias
     */
    private void addEmployeeToActivity() {
        Employee emp = employeeComboBox.getValue();
        try {

            pms.addEmployeeToActivity(projectActivity, emp);
            refresh();

        } catch (NullNotAllowed | EmployeeAlreadyAddedException | NoAccessException | TooManyActivitiesException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

    }

    /**
     * Created by Marcus
     */
    private void addEmployeeToActivityAsConsultant() {
        Employee emp = employeeComboBox.getValue();

        try {

            pms.addEmployeeToActivityAsConsultant(projectActivity, emp);
            refresh();

        } catch (NoAccessException | InvalidEmployeeException | EmployeeAlreadyAddedException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.ERROR, e.getMessage());

            error.showAndWait();
        }

    }

    /**
     * Created by Morten
     */
    public void setProjectActivity(ProjectActivity projectActivity) {
        this.projectActivity = projectActivity;

        //If the manage projectActivity pane isn't on, set it on.
        if (!setupRight) {
            setupRightContainer();
            setupRight = true;
        }

        obsAssignedEmpList
                = FXCollections.observableList(projectActivity.getEmployees());

        obsConsultants = FXCollections.observableList(projectActivity.getConsultants());

        assignedEmpListView.setItems(obsAssignedEmpList);
        consultantsListView.setItems(obsConsultants);

        titlePane.setText(projectActivity.getActivityType());

        refresh();
    }

    /**
     * Created by Tobias
     */
    public void refresh() {

        //Set everything
        manageActivityPane.setActivity(projectActivity);

        obsAssignedEmpList = FXCollections.observableList(projectActivity.getEmployees());
        assignedEmpListView.setItems(obsAssignedEmpList);

        obsConsultants = FXCollections.observableList(projectActivity.getConsultants());
        consultantsListView.setItems(obsConsultants);

        //Refresh everything.
        assignedEmpListView.refresh();
        consultantsListView.refresh();
        manageActivityPane.refresh();

        updateAvailableEmployeeList();
    }

    /**
     * Created by Marcus
     */
    private void updateAvailableEmployeeList() {
        try {
            obsAvailableEmployees = FXCollections.observableList(
                    pms.findAvailableEmployees(projectActivity.getStartDate(),
                            projectActivity.getEndDate(),
                            projectActivity));
        } catch (WrongDateException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }
        employeeComboBox.setItems(obsAvailableEmployees);
    }

    /**
     * Created by Morten
     */
    public void setProjectPane(ProjectPane projectPane) {
        this.projectPane = projectPane;
    }
}