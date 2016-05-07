package dk.dtu.software.group8.GUI;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Created by Morten on 27/04/16.
 */
public class MenuButton extends VBox {

    private final int BUTTON_SIZE = 64;

    private String name;

    public MenuButton(String name, String iconPath) {

        this.setId("");
        this.name = name;

        this.getStyleClass().add("MenuButton");
        this.setAlignment(Pos.CENTER);
        try {
            Image img = new Image(iconPath, BUTTON_SIZE, BUTTON_SIZE, true, true);
            ImageView icon = new ImageView(img);
            this.getChildren().add(icon);
        } catch (Exception e) {
            Alert error = new ErrorPrompt(Alert.AlertType.INFORMATION, "Couldn't load icons for menu buttons.");

            error.showAndWait();
        }

        Label label = new Label(name);

        this.getChildren().add(label);

        this.addHoverEffect();

    }

    private void addHoverEffect() {
        this.setOnMouseEntered(e -> {
            if (!this.getId().equals("active")) {
                setCursor(Cursor.HAND);
                this.setId("hover");
            }
        });

        this.setOnMouseExited(e -> {
            if (this.getId().equals("hover")) {
                setCursor(Cursor.DEFAULT);
                this.setId("");
            }
        });
    }

    public String getName() {
        return name;
    }


}
