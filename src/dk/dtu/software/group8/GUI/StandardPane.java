package dk.dtu.software.group8.GUI;

import dk.dtu.software.group8.PManagementSystem;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by Marcus
 */
public abstract class StandardPane extends BorderPane {

    protected VBox rightContainer;
    protected VBox centerContainer;
    protected TitlePane titlePane;
    protected PManagementSystem pms;
    protected TitlePane centerTitle;

    /**
     * Created by Marcus
     */
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

    /**
     * Created by Morten
     */
    protected void addTitleToCenterContainer(String title) {
        centerTitle = new TitlePane(title, TitleFontSize.MEDIUM);
        centerContainer.getChildren().add(centerTitle);
    }

    /**
     * Created by Tobias
     */
    protected void addNewExpandingChildToCenterContainer(Node node) {
        centerContainer.getChildren().add(node);
        VBox.setVgrow(node, Priority.ALWAYS);
    }

    /**
     * Created by Marcus
     */
    protected abstract void close();
}