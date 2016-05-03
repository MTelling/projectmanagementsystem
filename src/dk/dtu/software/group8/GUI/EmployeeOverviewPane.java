package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

/**
 * Created by Morten on 30/04/16.
 */
public class EmployeeOverviewPane extends ControlPane {


    public EmployeeOverviewPane(PManagementSystem pms) {
        super(pms, "Overview");

        //TODO: This should get the emps actual hours and activity count.
        double empTotalHours = 2000.50;
        double empMonthHours = 140.00;
        int empActivityCount = 5;

        Label empTotalHoursLblLeft = new Label("Total Hours:");
        Label empMonthHoursLblLeft = new Label("Hours this month:");
        Label empActivityCountLblLeft = new Label("Current Activity Count:");
        Label empTotalHoursLblRight = new Label(Double.toString(empTotalHours));
        Label empMonthHoursLblRight = new Label(Double.toString(empMonthHours));
        Label empActivityCountLblRight = new Label(Integer.toString(empActivityCount));

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

    }
}
