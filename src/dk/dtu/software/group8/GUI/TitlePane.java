package dk.dtu.software.group8.GUI;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Created by Morten
 */
public class TitlePane extends StackPane {

    private Text title;

    /**
     * Created by Marcus
     */
    public TitlePane(String text, TitleFontSize titleFontSize) {
        this.getStyleClass().add("TitlePane");
        title = new Text(text);
        if (titleFontSize == TitleFontSize.LARGE) {
            title.getStyleClass().add("TitlePaneLargeText");
        } else if (titleFontSize == TitleFontSize.MEDIUM) {
            title.getStyleClass().add("TitlePaneMediumText");
        }

        this.getChildren().add(title);
    }

    /**
     * Created by Morten
     */
    public void setText(String text) {
        this.title.setText(text);
    }
}