import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

public class GamePlay extends Application {
    private Scene root;
    private Stage window;
    private boolean turnX = true;
    private int[][] board = {{2,2,2},{2,2,2},{2,2,2}};

    private void addValue(int X, int Y){
        if(turnX){
            board[X][Y] = 1;
        }
        else{
            board[X][Y] = 0;
        }
    }
    private boolean checkWin(int x, int y){
        boolean check = false;
        //Horizontal Compare
        int num = board[x][0];
        if(num!=2 && num == board[x][1] && num == board[x][2]){
            check=true;
        }
        //Vertical Compare
        num =board[0][y];
        if(num!=2 && num == board[1][y] && num == board[2][y]){
            check=true;
        }
        //Left-Diagonal Compare
        num = board[0][0];
        if(num!=2 && num == board[1][1] && num == board[2][2]){
            check=true;
        }
        //Right-Diagonal Ccompare
        num = board[2][0];
        if(num!=2 && num == board[1][1] && num == board[0][2]){
            check = true;
        }
        return check;
    }
    private void draw(StackPane layout, int X, int Y){
        Text text = new Text();
        layout.setOnMouseClicked(event->{
            if(event.getButton()== MouseButton.PRIMARY){
                if(turnX && !layout.getChildren().contains(text)){
                    addValue(X,Y);
                    turnX=false;
                    text.setText("X");
                    text.setId("symbol");
                    layout.getChildren().add(text);
                }
                else if(!layout.getChildren().contains(text)){
                    addValue(X,Y);
                    text.setText("O");
                    text.setId("symbol");
                    layout.getChildren().add(text);
                    turnX=true;
                }
                if(checkWin(X,Y)){
                    alertBox();
                }
            }
        });
    }
    private void alertBox(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Ended!");
        if(!turnX){
        alert.setHeaderText("Player X Won");
        }
        else{
            alert.setHeaderText("Player O Won");
        }
        alert.setContentText("Play Again?");

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType exit = new ButtonType("Exit");
        alert.getButtonTypes().setAll(playAgain,exit);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == playAgain){
            board = new int[][]{{2,2,2}, {2,2,2}, {2,2,2}};
            turnX = true;
            createScene();
        } else if(result.get() == exit){
            Platform.exit();
            System.exit(0);
        }
        alert.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });
    }
    private void createScene() {
        BorderPane container = new BorderPane();
        Pane board = new Pane();
        HBox top = new HBox();
        HBox author = new HBox();

        author.setPrefSize(400,10);
        Text text = new Text("Version 0.1");
        author.getChildren().addAll(text);

        top.setPrefSize(400,99);
        top.setId("background-image");

        container.setPadding(new Insets(0, 9, 0, 10));
        container.setTop(top);
        container.setCenter(board);
        container.setBottom(author);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                    Tile tile = new Tile();
                    draw(tile, x, y);
                    tile.setTranslateX(x * 133);
                    tile.setTranslateY(y * 133);
                    board.getChildren().add(tile);
                }
            }
        root = new Scene(container,420,520);
        root.getStylesheets().add("tiles.css");
        window.setScene(root);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setTitle("TicTacToe- Developed By Attaf Habib");
        window.setResizable(false);
        createScene();
        window.show();
    }
}