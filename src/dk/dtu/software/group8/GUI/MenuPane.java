package dk.dtu.software.group8.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morten on 25/04/16.
 */
public class MenuPane extends VBox {

    private PrimaryStage primaryStage;
    private List<MenuButton> menuButtons;

    public MenuPane(PrimaryStage primaryStage) {

        this.primaryStage = primaryStage;
        this.getStyleClass().add("MenuPane");

        menuButtons = new ArrayList<>();
        menuButtons.add(new MenuButton("Overview", "overview_icon.png"));
        menuButtons.add(new MenuButton("Projects", "projects_icon.png"));
        menuButtons.add(new MenuButton("Activities", "activities_icon.png"));
        menuButtons.add(new MenuButton("Register Hours", "timeregister_icon.png"));

        //Overview should by active by default:
        menuButtons.get(0).setId("active");

        //Add the listener for clicks.
        for (MenuButton menuButton: menuButtons) {
            addClickListener(menuButton);
        }

        //Settings
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(menuButtons);

    }

    private void addClickListener(MenuButton menuButton) {
        menuButton.setOnMouseClicked(e -> {
            for (MenuButton menuBtn: menuButtons) {
                if (menuBtn.equals(menuButton)) {
                    menuBtn.setId("active");
                    primaryStage.present(menuBtn.getName());
                } else {
                    menuBtn.setId("");
                }
            }
        });
    }


}
