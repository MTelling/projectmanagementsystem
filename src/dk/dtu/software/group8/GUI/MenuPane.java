package dk.dtu.software.group8.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Created by Morten on 25/04/16.
 */
public class MenuPane extends VBox {

    private final int BUTTON_SIZE = 64;
    private PrimaryStage primaryStage;
    private String activeId;

    public MenuPane(PrimaryStage primaryStage) {

        this.primaryStage = primaryStage;

        VBox overviewBtn = menuButton("Overview", "overview_icon.png");
        VBox projectsBtn = menuButton("Projects", "projects_icon.png");
        VBox activitiesBtn = menuButton("Activities", "activities_icon.png");
        VBox timeRegisterBtn = menuButton("Register Hours", "timeregister_icon.png");

        //Overview should by active by default:
        activeId = "Overview";
        overviewBtn.setStyle("-fx-background-color: rgba(255,255,255,0.6);" + "-fx-border-color: rgba(0,0,0,0.8);");

        //Settings
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(overviewBtn, projectsBtn, activitiesBtn, timeRegisterBtn);
        this.setSpacing(60);
        this.setPadding(new Insets(10, 5, 10, 5));
        this.setStyle("-fx-background-color: rgba(0, 113, 255, 0.3);" +
                "-fx-border-style: hidden solid hidden hidden;" +
                " -fx-border-color: transparent rgba(0,0,0,0.5) transparent transparent;");
    }

    private VBox menuButton(String text, String iconPath) {
        int spacing = 2;
        VBox menuButton = new VBox(spacing);
        menuButton.setId(text);
        menuButton.setPadding(new Insets(4,4,4,4));
        menuButton.setAlignment(Pos.CENTER);
        Image img = new Image(iconPath, BUTTON_SIZE, BUTTON_SIZE, true, true);
        ImageView icon = new ImageView(img);
        Label label = new Label(text);

        menuButton.setStyle("-fx-border-color: rgba(0,0,0,0.8);");

        menuButton.getChildren().addAll(icon, label);

        addHoverEffect(menuButton);
        addClickListener(menuButton);

        return menuButton;
    }

    private void addHoverEffect(VBox vbox) {
        vbox.setOnMouseEntered(e -> {
            if (!vbox.getId().equals(activeId)) {
                setCursor(Cursor.HAND);
                vbox.setStyle("-fx-background-color: rgba(255,255,255,0.6);" + "-fx-border-color: rgba(0,0,0,0.8);");
            }
        });

        vbox.setOnMouseExited(e -> {
            if (!vbox.getId().equals(activeId)) {
                setCursor(Cursor.DEFAULT);
                vbox.setStyle("-fx-border-color: rgba(0,0,0,0.8);");
            }
        });
    }

    private void addClickListener(VBox vbox) {
        vbox.setOnMouseClicked(e -> {
            if (!vbox.getId().equals(activeId)) {
                primaryStage.present(vbox.getId());
                activeId = vbox.getId();
            }
        });
    }


}
