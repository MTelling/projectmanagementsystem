package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OverviewPane extends BorderPane {

    private PManagementSystem pms;

    public OverviewPane(PManagementSystem pms) {
        this.getStyleClass().add("OverviewPane");

        this.pms = pms;

        StackPane titlePane = new TitlePane("Welcome " + pms.getCurrentEmployee().getFirstName(), TitleFontSize.LARGE);

        EmployeeActivitiesOverview employeeActivitiesOverview =
                new EmployeeActivitiesOverview(pms.getCurrentEmployee().getCurrentActivities());
        EmployeeOverview employeeOverview = new EmployeeOverview(pms);

        this.setCenter(employeeActivitiesOverview);
        this.setTop(titlePane);
        this.setRight(employeeOverview);

    }






}
