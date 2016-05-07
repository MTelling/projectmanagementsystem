package dk.dtu.software.group8.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias
 */
public class MenuPane extends VBox {

    private PrimaryStage primaryStage;
    private List<MenuButton> menuButtons;

    /**
     * Created by Marcus
     */
    public MenuPane(PrimaryStage primaryStage) {

        this.primaryStage = primaryStage;
        this.getStyleClass().add("MenuPane");

        menuButtons = new ArrayList<>();

        MenuButton overviewBtn = new MenuButton("Overview", "overview_icon.png");
        overviewBtn.setId("overviewBtn");
        MenuButton projectsBtn = new MenuButton("Projects", "projects_icon.png");
        projectsBtn.setId("projectsBtn");
        MenuButton registerHoursBtn = new MenuButton("Register Hours", "timeregister_icon.png");
        registerHoursBtn.setId("registerHoursBtn");
        menuButtons.add(overviewBtn);
        menuButtons.add(projectsBtn);
        menuButtons.add(registerHoursBtn);

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

    /**
     * Created by Morten
     */
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