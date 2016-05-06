package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import dk.dtu.software.group8.YearWeek;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * Created by Morten on 02/05/16.
 */
public class CreateProjectActivityPane extends ControlPane {

    private final TextField startWeekField;
    private final TextField startYearField;
    private final TextField endWeekField;
    private final TextField endYearField;
    private final TextField expectedHoursField;
    private final TextField typeField;
    private ProjectPane projectPane;
    private Project project;

    public CreateProjectActivityPane(PManagementSystem pms, ProjectPane projectPane) {
        super(pms, "Create Activity");
        this.projectPane = projectPane;

        //Create the labels.
        Label typeLbl = new Label("Activity Type:");
        Label expectedHoursLbl = new Label("Expected Hours:");
        Label startWeekLbl = new Label("Start Week:");
        Label startYearLbl = new Label("Start Year:");
        Label endWeekLbl = new Label("End Week:");
        Label endYearLbl = new Label("End Year:");


        typeField = new TextField();
        startWeekField = new TextField();
        startYearField = new TextField();
        endWeekField = new TextField();
        endYearField = new TextField();

        expectedHoursField = new TextField();


        //Add everything to the grid.
        controlsGrid.add(typeLbl, 0,0);
        controlsGrid.add(typeField, 1,0);
        controlsGrid.add(expectedHoursLbl, 0, 1);
        controlsGrid.add(expectedHoursField,1,1);
        controlsGrid.add(startWeekLbl, 0,2);
        controlsGrid.add(startWeekField, 1, 2);
        controlsGrid.add(startYearLbl,0,3);
        controlsGrid.add(startYearField,1,3);
        controlsGrid.add(endWeekLbl, 0, 4);
        controlsGrid.add(endWeekField,1,4);
        controlsGrid.add(endYearLbl, 0, 5);
        controlsGrid.add(endYearField,1,5);



        Button createBtn = new Button("Create Activity");


        //Connect button to control
        createBtn.setOnAction(e -> createActivity());

        this.addButton(createBtn);
    }

    private void createActivity() {
        try {
            YearWeek startWeek = new YearWeek(Integer.parseInt(startYearField.getText()),
                    Integer.parseInt(startWeekField.getText()));

            YearWeek endWeek = new YearWeek(Integer.parseInt(endYearField.getText()),
                    Integer.parseInt(endWeekField.getText()));

            pms.createActivityForProject(project,
                    typeField.getText(),
                    startWeek,
                    endWeek,
                    Integer.parseInt(expectedHoursField.getText()));


            projectPane.refresh();

        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
