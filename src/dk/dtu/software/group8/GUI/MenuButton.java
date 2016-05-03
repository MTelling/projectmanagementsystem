package dk.dtu.software.group8.GUI;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
        Image img = new Image(iconPath, BUTTON_SIZE, BUTTON_SIZE, true, true);
        ImageView icon = new ImageView(img);
        Label label = new Label(name);

        this.getChildren().addAll(icon, label);

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
