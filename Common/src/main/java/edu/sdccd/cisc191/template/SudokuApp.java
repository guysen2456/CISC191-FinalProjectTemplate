package edu.sdccd.cisc191.template;

import javafx.application.Application; //covers Week 2 for coding an application
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert; //made for alerting players if they made an error or not
import javafx.scene.control.Button; //used to design Submit Score and Check Solution buttons
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class SudokuApp extends Application {
    SudokuBoard sudokuBoard;
    SudokuGame sudokuGame;
    private HighScoreManager highScoreManager;
    int correctFills; // Track number of correct fills
    private int bonus = 10;

    public static void main(String[] args) {
        launch(args); // vital for launching the app
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku");
        // Calls the other classes in the same package
        sudokuGame = new SudokuGame();
        sudokuBoard = new SudokuBoard();
        highScoreManager = new HighScoreManager();
        correctFills = 0; // Initialize correct fills counter

        updateBoard();
        //Clicking on the button leads you to a specific action
        Button checkButton = new Button("Check Solution");
        checkButton.setOnAction(e -> checkSolution());

        Button submitButton = new Button("Submit Score");
        submitButton.setOnAction(e -> submitScore());

        //PLaces the grid and buttons in a vertical orientation
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(sudokuBoard.getGrid(), checkButton, submitButton);
        vbox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        //Window size
        Scene scene = new Scene(root, 400, 400);

        //Specifies the scene set for the app
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateBoard() {
        //Calls sudokuGame class for the puzzle
        int[][] puzzle = sudokuGame.getPuzzle();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // I used setCell method so I can set value on each cell
                sudokuBoard.setCell(row, col, puzzle[row][col]);
            }
        }
    }

    void checkSolution() {
        //Checks if the solution is correct
        int[][] currentBoard = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                currentBoard[row][col] = sudokuBoard.getCell(row, col);
            }
        }

        if (sudokuGame.checkSolution(currentBoard)) {
            //Updates correctFills
            correctFills = countCorrectFills(currentBoard);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText(null);
            alert.setContentText("Sudoku solved correctly! Your score: " + calculateScore());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Solution");
            alert.setHeaderText(null);
            alert.setContentText("Oops! Your solution is incorrect. Keep trying!");
            alert.showAndWait(); //showAndWait makes the game to wait for the user input after displaying message, otherwise, it does nothing
        }
    }

    private void submitScore() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Submit Score");
        dialog.setHeaderText("Enter your name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(playerName -> { //Helps calculate the number
            int score = calculateScore(); // Implements scoring logic
            highScoreManager.addHighScore(playerName, score); //Adds high score in highScoreManager
            showHighScores();
        });
    }

    private int countCorrectFills(int[][] board) {
        int count = 0;
        int[][] solution = sudokuGame.getSolution();
        //By iterating each cell, it compares with the input's solution and can determine whether he/she gets it right or not
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == solution[row][col]) { //Solution has to match the created board in order to get the number of correct fills
                    count++;
                }
            }
        }
        return count;
    }

    int calculateScore() {
        return correctFills * 100 + bonus;
    }

    private void showHighScores() {
        List<HighScoreEntry> highScores = highScoreManager.getHighScores(); //Calls highScoreManager to receive a list of high scores
        StringBuilder sb = new StringBuilder();
        sb.append("High Scores:\n");
        for (HighScoreEntry entry : highScores) {
            sb.append(entry).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("High Scores");
        alert.setHeaderText(null);
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }
}

