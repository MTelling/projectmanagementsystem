package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class OverviewPane extends StandardPane {

    private final EmployeeActivitiesTableView employeeActivitiesTableView;
    private final ListView personalActivitiesListView;
    private final EmployeeOverviewPane employeeOverviewPane;

    private ObservableList obsPersonalActivities;

    public OverviewPane(PManagementSystem pms) {
        super(pms, false);

        titlePane.setText("Welcome " + pms.getCurrentEmployee().getFirstName());
        employeeActivitiesTableView =
                new EmployeeActivitiesTableView(pms.getCurrentEmployee().getProjectActivities());
        employeeOverviewPane = new EmployeeOverviewPane(pms);

        personalActivitiesListView = new ListView();
        obsPersonalActivities = FXCollections.observableList(pms.getCurrentEmployee().getPersonalActivities());
        personalActivitiesListView.setItems(obsPersonalActivities);

        CreatePersonalActivityPane createPersonalActivityPane = new CreatePersonalActivityPane(pms, this);


        addTitleToCenterContainer("All project activities");
        addNewExpandingChildToCenterContainer(employeeActivitiesTableView);
        addTitleToCenterContainer("All personal activities");
        addNewExpandingChildToCenterContainer(personalActivitiesListView);
        rightContainer.getChildren().add(employeeOverviewPane);
        rightContainer.getChildren().add(createPersonalActivityPane);

    }


    @Override
    protected void close() {
        toBack();
    }

    @Override
    public void toFront() {
        super.toFront();
        employeeOverviewPane.update();
    }


    public void refresh() {
        obsPersonalActivities = FXCollections.observableList(pms.getCurrentEmployee().getPersonalActivities());
        personalActivitiesListView.setItems(obsPersonalActivities);


        employeeActivitiesTableView.refresh();
        personalActivitiesListView.refresh();
    }
}
