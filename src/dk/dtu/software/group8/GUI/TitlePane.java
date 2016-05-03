package dk.dtu.software.group8.GUI;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Created by Morten on 02/05/16.
 */
public class TitlePane extends StackPane {

    private Text title;

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

    public void setText(String text) {
        this.title.setText(text);
    }
}
