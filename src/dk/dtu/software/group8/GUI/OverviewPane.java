package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Created by Marcus
 */
public class OverviewPane extends StandardPane {

    private final EmployeeActivitiesTableView employeeActivitiesTableView;
    private final ListView personalActivitiesListView;
    private final EmployeeOverviewPane employeeOverviewPane;

    private ObservableList obsPersonalActivities;

    /**
     * Created by Tobias
     */
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

    /**
     * Created by Marcus
     */
    @Override
    protected void close() {
        toBack();
    }

    /**
     * Created by Morten
     */
    @Override
    public void toFront() {
        super.toFront();
        employeeOverviewPane.update();
    }

    /**
     * Created by Tobias
     */
    public void refresh() {
        obsPersonalActivities = FXCollections.observableList(pms.getCurrentEmployee().getPersonalActivities());
        personalActivitiesListView.setItems(obsPersonalActivities);

        employeeActivitiesTableView.refresh();
        personalActivitiesListView.refresh();
    }
}