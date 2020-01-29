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

    //determine if there is empty token
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[j][i].getToken() == ' ') {
                    return false; //continue game if there is empty token
                }
            }
        }
        return true; //break game if board if full
    }

    //determine if any player won
    public boolean ifPlayerWon(char token) {

        //horizontal or vertical
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getToken() == token && board[i][1].getToken() == token && board[i][2].getToken() == token ||
                    board[0][i].getToken() == token && board[1][i].getToken() == token && board[2][i].getToken() == token) {
                return true;
            }
        }

        //diagonal
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

        //move of computer player
        private void computerMove() {
            int randomRow = random.nextInt(3);
            int randomColumn = random.nextInt(3);
            board[randomRow][randomColumn].setToken(player);
        }

        public char getToken() {
            return token;
        }

        //setting tokens
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

        //what happens when player clicks mouse
        private void mouseEvent() {
            if (token == ' ' && player != 'E') { //if cell is empty and game continues
                    setToken(player);
                    checkState();
            }
        }

        private void checkState() {
            while (player != 'E') { //if game continues
                if (player == 'X') { //if now is player's turn
                    setOnMouseClicked(event -> mouseEvent());

                    if (ifPlayerWon(player)) {
                        player = 'E'; //end game if player won
                    } else if (isBoardFull()) {
                        player = 'E'; //end game if there is no empty token
                    } else {
                        player = 'O'; //change player
                    }

                } else if (player == 'O') { //if now is computer's turn
                    computerMove();

                    if (ifPlayerWon(player)) {
                        player = 'E'; //end game if computer won
                    } else if (isBoardFull()) {
                        player = 'E'; //end game if there is no empty token
                    } else {
                        player = 'X'; //change player
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
