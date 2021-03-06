package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.scene.control.Label;

/**
 * Created by Marcus
 */
public class EmployeeOverviewPane extends ControlPane {

    private Label empMonthHoursLblRight;
    private Label empTotalHoursLblRight;
    private Label empActivityCountLblRight;

    /**
     * Created by Morten
     */
    public EmployeeOverviewPane(PManagementSystem pms) {
        super(pms, "Overview");

        Label empTotalHoursLblLeft = new Label("Total Hours:");
        Label empMonthHoursLblLeft = new Label("Hours this month:");
        Label empActivityCountLblLeft = new Label("Current Activity Count:");
        empTotalHoursLblRight = new Label("0");
        empMonthHoursLblRight = new Label("0");
        empActivityCountLblRight = new Label("0");

        Label[] labels = {empTotalHoursLblLeft,
                empTotalHoursLblRight,
                empMonthHoursLblLeft,
                empMonthHoursLblRight,
                empActivityCountLblLeft,
                empActivityCountLblRight};

        for (int i = 0; i <= labels.length/2 + 1; i += 2) {
            controlsGrid.add(labels[i], 0, i);
            controlsGrid.add(labels[i+1], 1, i);
        }

        update();

    }

    /**
     * Created by Tobias
     */
    public void update() {
        empActivityCountLblRight.setText(Integer.toString(pms.getCurrentEmployee().getProjectActivities().size()));
    }
}
