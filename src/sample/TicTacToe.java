package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.Random;


public class TicTacToe extends Application {

    private char player = 'X';
    private Cell[][] board = new Cell[3][3];

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[j][i] = new Cell();
                gridPane.add(board[j][i], j, i);
            }
        }

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setTitle("Tic Tac Toe Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[j][i].getToken() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean ifPlayerWon(char token) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getToken() == token && board[i][1].getToken() == token && board[i][2].getToken() == token ||
                    board[0][i].getToken() == token && board[1][i].getToken() == token && board[2][i].getToken() == token) {
                return true;
            }
        }

        if (board[0][0].getToken() == token && board[1][1].getToken() == token && board[2][2].getToken() == token ||
                board[0][2].getToken() == token && board[1][1].getToken() == token && board[2][0].getToken() == token) {
            return true;

        }
        return false;
    }

    public class Cell extends Pane {

        private char token = ' ';
        private Random random = new Random();

        public Cell() {
            setStyle("-fx-border-color: black");
            setPrefSize(100, 100);
            setOnMouseClicked(event -> mouseEvent());
        }

        private void computerMove() {
            int randomRow = random.nextInt(10);
            int randomColumn = random.nextInt(10);
            board[randomRow][randomColumn].setToken(player);
        }

        public char getToken() {
            return token;
        }

        public void setToken(char sign) {
            token = sign;

            if (token == 'X') {
                Text text = new Text("X");
                setShape(text);
            } else if (token == 'O') {
                Text text = new Text("O");
                setShape(text);
            }
        }

        private void mouseEvent() {
            if (token == ' ' && player != 'E') { //if cell is empty and game continues
                if (player == 'X') {
                    setToken(player);
                    checkState();
                }
            }
        }

        private void checkState() {
            while (player != 'E') { //if game continues
                if (player == 'X') { //if now is player's turn
                    setOnMouseClicked(event -> mouseEvent());
                    player = 'O';
                    if (ifPlayerWon(player)) {
                        player = 'E'; //end game
                    } else if (isBoardFull()) {
                        player = 'E';
                    }

                } else if (player == 'O') { //if now is computer's turn
                    computerMove();
                    player = 'X';
                    if (ifPlayerWon(player)) {
                        player = 'E'; //end game
                    } else if (isBoardFull()) {
                        player = 'E';
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
