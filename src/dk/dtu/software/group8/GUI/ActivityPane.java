package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.*;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 * Created by Morten on 02/05/16.
 */
public class ActivityPane extends StandardPane {

    private Activity activity;
    private ProjectPane projectPane;
    private ComboBox<Employee> employeeComboBox;
    private ListView<Employee> assignedEmpListView;
    private ObservableList<Employee> obsAssignedEmpList;
    private boolean setupRight;

    //TODO: Change this when an activity has it's own project
    private Project project;


    public ActivityPane(PManagementSystem pms) {
        super(pms, true);
        setupRight = false;

        assignedEmpListView = new ListView<Employee>();


        addTitleToCenterContainer("Employees");
        addNewExpandingChildToCenterContainer(assignedEmpListView);

    }

    @Override
    protected void close() {
        projectPane.refresh();
        this.toBack();
    }

    private void setupRightContainer() {
        //This is the pane that can manage the activity.
        ManageActivityPane manageActivityPane = new ManageActivityPane(pms, activity);

        TitlePane addEmployeeTitle = new TitlePane("Add employee to activity", TitleFontSize.MEDIUM);
        employeeComboBox = new ComboBox<>();
        ObservableList<Employee> obsAvailableEmployees = FXCollections.observableList(pms.getEmployees());
        employeeComboBox.setItems(obsAvailableEmployees);

        Button addEmployeeBtn = new Button("Add employee");
        addEmployeeBtn.setOnAction(e -> addEmployeeToActivity());

        //Add the ability to add an employee to the right.
        rightContainer.getChildren().addAll(manageActivityPane, addEmployeeTitle, employeeComboBox, addEmployeeBtn);
    }

    private void addEmployeeToActivity() {
        //TODO: Change this once an activity has it's project.

        Employee emp = employeeComboBox.getValue();
        try {

            pms.addEmployeeToActivity(project, (ProjectActivity) activity, emp);
            refresh();

        } catch (NoAccessException | TooManyActivitiesException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

    }

    public void setActivity(Activity activity) {
        this.activity = activity;

        //If the manage activity pane isn't on, set it on.
        if (!setupRight) {
            setupRightContainer();
            setupRight = true;
        }

        obsAssignedEmpList
                = FXCollections.observableList(((ProjectActivity) activity).getEmployees());

        assignedEmpListView.setItems(obsAssignedEmpList);

        titlePane.setText(activity.getActivityType());

        refresh();
    }

    public void refresh() {
        //TODO: Do this smarter!
        obsAssignedEmpList = FXCollections.observableList(((ProjectActivity) activity).getEmployees());
        assignedEmpListView.setItems(obsAssignedEmpList);

        assignedEmpListView.refresh();
    }

    public void setProjectPane(ProjectPane projectPane) {
        this.projectPane = projectPane;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
