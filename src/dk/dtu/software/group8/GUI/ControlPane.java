package dk.dtu.software.group8.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Morten on 02/05/16.
 */
public abstract class ControlPane extends VBox {

    protected TitlePane titlePane;
    protected GridPane controlsGrid;
    private HBox btnPane;

    public ControlPane(String titleText) {

        BorderPane borderPane = new BorderPane();
        this.getStyleClass().add("ControlPane");

        titlePane = new TitlePane(titleText);

        controlsGrid = new GridPane();
        controlsGrid.setAlignment(Pos.CENTER);
        controlsGrid.getStyleClass().add("ControlPaneGrid");

        //Create the bottom.
        btnPane = new HBox();
        btnPane.getStyleClass().add("BtnPane");
        btnPane.setAlignment(Pos.CENTER);

        borderPane.setTop(titlePane);
        borderPane.setCenter(controlsGrid);
        borderPane.setBottom(btnPane);

        this.getChildren().add(borderPane);

    }

    protected void addButton(Button btn) {
        btnPane.getChildren().add(btn);
    }

}
