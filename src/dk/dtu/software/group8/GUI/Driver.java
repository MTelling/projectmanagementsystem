package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.Exceptions.AlreadyAssignedProjectManagerException;
import dk.dtu.software.group8.Exceptions.WrongDateException;
import dk.dtu.software.group8.PManagementSystem;
import dk.dtu.software.group8.Project;
import dk.dtu.software.group8.ProjectActivity;
import dk.dtu.software.group8.YearWeek;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Created by Morten on 25/04/16.
 */
public class Driver extends Application {

    private PManagementSystem pms;
    private PrimaryStage primaryStage;

    public static void main(String[] args) {

        Application.launch(args);
    }

    public void start(Stage loginStage) {

        //Create an instance of the pms
        this.pms = new PManagementSystem();

        beforeDemo();

        //Create the login stage
        loginStage = new LoginStage(this, pms);

        //To start just show the loginstage.
        loginStage.show();
    }

    public void startPrimaryStage() {

        demo();

        primaryStage = new PrimaryStage(pms);

        primaryStage.show();
    }

    private void beforeDemo() {

        try {
            pms.signIn("huba");
            Project project = pms.createProject(LocalDate.now(), LocalDate.now().plusMonths(10));
            pms.assignManagerToProject(project);

            for (int i = 0; i < 5; i++) {
                try {
                    ProjectActivity activity = pms.createActivityForProject(project,
                            "Implementation",
                            YearWeek.fromDate(LocalDate.now().plusDays(10)),
                            YearWeek.fromDate(LocalDate.now().plusDays(40)),
                            20);

                    pms.addEmployeeToActivity(project, activity, pms.getCurrentEmployee());

                } catch (Exception e) {
                    Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                    error.showAndWait();
                }
            }


        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        for (int i = 0; i < 10; i++) {
            try {
                pms.createProject(LocalDate.now(), LocalDate.now().plusMonths(10));
            } catch (WrongDateException e) {
                Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                error.showAndWait();
            }
        }
    }

    private void demo() {
        Project project = null;
        try {
            project = pms.createProject(LocalDate.now(), LocalDate.now().plusMonths(10));
        } catch (WrongDateException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        try {
            pms.assignManagerToProject(project);
        } catch (AlreadyAssignedProjectManagerException e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
            error.showAndWait();
        }

        for (int i = 0; i < 5; i++) {
            try {
                ProjectActivity activity = pms.createActivityForProject(project,
                        "Implementation",
                        YearWeek.fromDate(LocalDate.now().plusDays(10)),
                        YearWeek.fromDate(LocalDate.now().plusDays(40)),
                        20);

                pms.addEmployeeToActivity(project, activity, pms.getCurrentEmployee());


            } catch (Exception e) {
                Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, e.getMessage());
                error.showAndWait();
            }
        }

    }


}
