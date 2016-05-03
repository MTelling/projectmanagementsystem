package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;

public class OverviewPane extends StandardPane {

    private final EmployeeActivitiesTableView employeeActivitiesTableView;

    public OverviewPane(PManagementSystem pms) {
        super(pms, false);

        titlePane.setText("Welcome " + pms.getCurrentEmployee().getFirstName());
        employeeActivitiesTableView =
                new EmployeeActivitiesTableView(pms.getCurrentEmployee().getCurrentActivities());
        EmployeeOverviewPane employeeOverviewPane = new EmployeeOverviewPane(pms);

        addTitleToCenterContainer("Current activities");
        addNewExpandingChildToCenterContainer(employeeActivitiesTableView);
        rightContainer.getChildren().add(employeeOverviewPane);

    }


    @Override
    protected void close() {
        toBack();
    }

    public void refresh() {
        employeeActivitiesTableView.refresh();
    }
}
