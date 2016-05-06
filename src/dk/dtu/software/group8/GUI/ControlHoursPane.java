package dk.dtu.software.group8.GUI;


import dk.dtu.software.group8.PManagementSystem;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * Created by Morten on 02/05/16.
 */
public class ControlHoursPane extends ControlPane {

    private Label totalMinutesField;
    private TextField setMinutesField;
    private TextField setHoursField;
    private Label totalHoursField;
    private RegisterHoursPane registerHoursPane;

    public ControlHoursPane(PManagementSystem pms, RegisterHoursPane registerHoursPane) {
        super(pms, "Change/Edit Hours");
        this.registerHoursPane = registerHoursPane;


        Label totalHoursLbl = new Label("Total work on day:");
        totalHoursField = new Label("0");
        Label colon = new Label(":");
        totalMinutesField = new Label("0");
        Label setHoursLbl = new Label("Registered work on activity:");
        setHoursField = new TextField("0");
        setHoursField.setMaxWidth(50);
        Label colonTwo = new Label(":");

        setMinutesField = new TextField("0");
        setMinutesField.setMaxWidth(50);

        Button changeHoursBtn = new Button("Save hours");
        changeHoursBtn.setOnAction(e -> updateTimeOnActivity());

        controlsGrid.add(totalHoursLbl, 0, 0);
        controlsGrid.add(totalHoursField,1,0,1,1);
        controlsGrid.add(colon, 2,0,1,1);
        controlsGrid.add(totalMinutesField,3,0,1,1);

        controlsGrid.add(setHoursLbl, 0,1,1,1);
        controlsGrid.add(setHoursField,1,1,1,1);
        controlsGrid.add(colonTwo, 2,1,1,1);
        controlsGrid.add(setMinutesField,3,1,1,1);

        this.addButton(changeHoursBtn);

    }

    public void setTotalMinutesOnDay(int minutes) {
        String[] time = minutesToHoursAndMinutes(minutes);

        totalHoursField.setText(time[0]);
        totalMinutesField.setText(time[1]);
    }

    public void setTotalMinutesOnActivity(int minutes) {
        String[] time = minutesToHoursAndMinutes(minutes);

        setHoursField.setText(time[0]);
        setMinutesField.setText(time[1]);
    }

    private String[] minutesToHoursAndMinutes(int minutes) {
        String hours = Integer.toString(minutes / 60);
        String min = Integer.toString(minutes % 60);

        return new String[] {hours, min};
    }

    private void updateTimeOnActivity() {
        String[] time = new String[2];
        time[0] = setHoursField.getText();
        time[1] = setMinutesField.getText();

        registerHoursPane.registerTimeOnActivity(time);
    }
}
