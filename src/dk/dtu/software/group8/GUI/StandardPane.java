package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.*;
import dk.dtu.software.group8.Exceptions.NoAccessException;
import dk.dtu.software.group8.Exceptions.TooManyActivitiesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by Morten on 03/05/16.
 */
public abstract class StandardPane extends BorderPane {

    protected VBox rightContainer;
    protected VBox centerContainer;
    protected TitlePane titlePane;
    protected PManagementSystem pms;
    protected TitlePane centerTitle;


    public StandardPane(PManagementSystem pms, boolean isCloseable) {

        this.pms = pms;

        this.getStyleClass().add("StandardPane");

        //Set window properties.
        titlePane = new TitlePane("N/A", TitleFontSize.LARGE);


        if (isCloseable) {
            //Create the exit button
            StackPane exitBtnPane = new StackPane();
            exitBtnPane.getStyleClass().add("ExitBtnPane");
            Button exitBtn = new Button("Go Back");
            exitBtnPane.getChildren().add(exitBtn);
            //Connect exit button to controls
            exitBtn.setOnMouseClicked(e -> close());
            this.setBottom(exitBtnPane);
        }

        centerContainer = new VBox();
        centerContainer.getStyleClass().add("CenterContainer");

        rightContainer = new VBox();
        rightContainer.setAlignment(Pos.TOP_CENTER);
        rightContainer.getStyleClass().add("RightContainer");

        //Put everything on this pane.
        this.setTop(titlePane);
        this.setCenter(centerContainer);
        this.setRight(rightContainer);

    }

    protected void addTitleToCenterContainer(String title) {

        centerTitle = new TitlePane(title, TitleFontSize.MEDIUM);
        centerContainer.getChildren().add(centerTitle);

    }

    protected void addNewExpandingChildToCenterContainer(Node node) {
        centerContainer.getChildren().add(node);
        VBox.setVgrow(node, Priority.ALWAYS);
    }

    protected abstract void close();

}
