package edu.sdccd.cisc191.template;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SudokuBoard {
    // Also made private so that it's useful when testing the classes
    private static final int SIZE = 9;
    private TextField[][] cells = new TextField[SIZE][SIZE]; //same size as the sudoku board
    private GridPane grid;

    public SudokuBoard() {
        initializeBoard();
    }

    private void initializeBoard() {
        grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(3);
        grid.setVgap(3);
        grid.setAlignment(Pos.CENTER);

        // Create text fields and add them to the grid
        for (int row = 0; row < SIZE; row++) { //sets size of the cells
            for (int col = 0; col < SIZE; col++) {
                TextField cell = new TextField();
                cell.setPrefWidth(40);
                cell.setPrefHeight(40);
                cell.setAlignment(Pos.CENTER);
                cell.getStyleClass().add("cell");

                // Add text field to the array and grid
                cells[row][col] = cell;
                grid.add(cell, col, row);
            }
        }
    }

    public GridPane getGrid() {
        return grid;
    }

    //setCell method checks if the value is 0. If it is, it sets the text of the cell to an empty string, effectively clearing it
    public void setCell(int row, int col, int value) {
        cells[row][col].setText(value == 0 ? "" : String.valueOf(value));
    }
    //getCell retrieves the value of a cell, enabling the application to read user input for solution validation or other processing
    //If parsing fails, then the inputted text is incorrect
    public int getCell(int row, int col) {
        try {
            String text = cells[row][col].getText();
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

