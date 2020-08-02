import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    public Tile(){
        Rectangle tile = new Rectangle(133,133);
        tile.getStyleClass().add("background-color");
        getChildren().add(tile);
    }
}
