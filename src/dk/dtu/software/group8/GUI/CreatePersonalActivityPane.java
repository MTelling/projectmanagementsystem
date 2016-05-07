package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Exceptions.IncorrectAttributeException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import dk.dtu.software.group8.PManagementSystem;
import javafx.scene.control.*;

import java.time.LocalDate;

/**
 * Created by Tobias
 */
public class CreatePersonalActivityPane extends ControlPane{

    private final TextField typeField;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private final OverviewPane overviewPane;

    /**
     * Created by Marcus
     */
    public CreatePersonalActivityPane(PManagementSystem pms, OverviewPane overviewPane) {
        super(pms, "Create Personal Activity");
        this.overviewPane = overviewPane;

        //Create the labels.
        Label typeLbl = new Label("Activity type:");
        Label startDateLbl = new Label("Start Date:");
        Label endDateLbl = new Label("End Date:");

        typeField = new TextField();
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();

        //Add everything to the grid.
        controlsGrid.add(typeLbl, 0,0);
        controlsGrid.add(typeField, 1,0);
        controlsGrid.add(startDateLbl, 0,1);
        controlsGrid.add(startDatePicker, 1, 1);
        controlsGrid.add(endDateLbl, 0, 2);
        controlsGrid.add(endDatePicker,1,2);



        Button createBtn = new Button("Create Activity");


        //Connect button to control
        createBtn.setOnAction(e -> createActivity());

        this.addButton(createBtn);
    }

    /**
     * Created by Morten
     */
    private void createActivity() {

        String type = typeField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        try {

            pms.createPersonalActivityForEmployee(type,startDate,endDate,pms.getCurrentEmployee());

            Alert success = new SuccessPrompt();
            success.showAndWait();

            overviewPane.refresh();

        } catch (WrongDateException | IncorrectAttributeException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }
    }
}
