package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

/**
 * Created by Morten on 30/04/16.
 */
public class EmployeeOverview extends GridPane {

    private PManagementSystem pms;

    private final double WIDTH = 300;

    public EmployeeOverview(PManagementSystem pms) {
        this.pms = pms;

        this.setMinWidth(WIDTH);
        this.getStyleClass().add("EmployeeOverview");

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
            this.add(labels[i], 0, i);
            this.add(labels[i+1], 1, i);
        }

    }
}
