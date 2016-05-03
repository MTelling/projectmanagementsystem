package dk.dtu.software.group8.GUI;


import dk.dtu.software.group8.PManagementSystem;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * Created by Morten on 02/05/16.
 */
public class ControlHoursPane extends ControlPane {

    public ControlHoursPane(PManagementSystem pms) {
        super(pms, "Change/Edit Hours");

        Label totalHoursLbl = new Label("Total hours for day:");
        Label totalHoursField = new Label("0");
        Label setHoursLbl = new Label("Hours for chosen activity:");
        TextField setHoursField = new TextField("0");

        Button changeHoursBtn = new Button("Save hours");

        controlsGrid.add(totalHoursLbl, 0,0);
        controlsGrid.add(totalHoursField, 1,0);
        controlsGrid.add(setHoursLbl, 0,1);
        controlsGrid.add(setHoursField,1,1);

        this.addButton(changeHoursBtn);

    }
}
