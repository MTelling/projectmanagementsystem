package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Morten on 25/04/16.
 */
public class LoginStage extends Stage {

    private PManagementSystem pms;
    private PrimaryStage primaryStage;

    private TextField userNameInput;
    private Label wrongLoginLabel;

    public LoginStage(PManagementSystem pms, PrimaryStage primaryStage) {
        final int HEIGHT = 150;
        final int WIDTH = 400;

        this.pms = pms;
        this.primaryStage = primaryStage;

        Scene scene = new Scene(loginPane(), WIDTH, HEIGHT);

        scene.getStylesheets().add(this.getClass().getResource("layout.css").toExternalForm());


        //Set the stage.
        this.setTitle("Project Management System - Log In");

        this.setScene(scene);
        this.setResizable(false);
        this.centerOnScreen();
        this.sizeToScene();

    }

    private GridPane loginPane() {
        GridPane loginPane = new GridPane();

        loginPane.getStyleClass().add("LoginPane");
        //Style the login pane.
        loginPane.setAlignment(Pos.CENTER);

        //Create all elements needed.
        Text welcomeText = new Text("Welcome to the Project Management System");
        welcomeText.getStyleClass().add("WelcomeText");

        Label userNameLabel = new Label("Id:");
        wrongLoginLabel = new Label("That is not a valid id.");
        wrongLoginLabel.getStyleClass().add("WrongLoginLabel");

        wrongLoginLabel.setVisible(false);
        userNameInput = new TextField();
        Button loginBtn = new Button("Login");
        //We want the button to be to the outer right.
        //To do this we need to put it in a hbox first.
        HBox hbox = new HBox();
        hbox.getStyleClass().add("HBoxForLoginBtn");
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        hbox.getChildren().add(wrongLoginLabel);
        hbox.getChildren().add(loginBtn);

        //Place the elements on the screen.
        loginPane.add(welcomeText, 0, 0, 2, 1);
        loginPane.add(userNameLabel, 0, 1);
        loginPane.add(userNameInput, 1, 1);
        loginPane.add(hbox, 0, 2, 2, 1);

        //Login when button is clicked.
        loginBtn.setOnAction(e -> login());
        //Login if enter is pressed in text field.
        userNameInput.setOnKeyPressed(e -> login(e));

        return loginPane;
    }

    private void login(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    private void login() {
        if (pms.signIn(userNameInput.getText())) {
            //If the user is allowed in, show the primary stage and close the login stage.

            primaryStage.update();
            primaryStage.show();
            this.close();

        } else { //The username wasn't valid - show message.
            wrongLoginLabel.setVisible(true);
        }
    }
}
