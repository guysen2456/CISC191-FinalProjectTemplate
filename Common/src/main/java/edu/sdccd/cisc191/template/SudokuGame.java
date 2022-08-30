package edu.sdccd.cisc191.template;

import java.util.Random;

public class SudokuGame {
    private int[][] solution; //making sure that the sudoku puzzle has a solution
    private int[][] puzzle; //builds the puzzle grid
    private final Random random = new Random();

    public SudokuGame() {
        solution = new int[9][9]; // grid usually has a 9x9 board
        puzzle = new int[9][9];
        generateSolution(); //Calls the constructor
        generatePuzzle();
    }

    private void generateSolution() {
        // Fill the solution with a valid randomized Sudoku board
        SudokuSolver solver = new SudokuSolver();
        fillDiagonal();
        solver.solve(solution); //Generates new solution, which is also the same place as the puzzle
    }

    private void fillDiagonal() {
        for (int i = 0; i < 9; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        int num;
        //Since there are rows and columns, I need to do for-loops twice in one body
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    //Randomizes the number placement and +1 is to be sure that the numbers are random
                    num = random.nextInt(9) + 1;
                } while (!isSafeToPlace(solution, row + i, col + j, num));
                solution[row + i][col + j] = num;
            }
        }
    }

    private boolean isSafeToPlace(int[][] board, int row, int col, int num) {
        for (int x = 0; x < 9; x++) {
            // Check if the number is already in the row
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }
        // Check if the number is already in the 3x3 subgrid
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }
        // If the number is not found in the row, column, or subgrid, it is safe to place it
        return true;
    }

    private void generatePuzzle() {
        // Copy solution to puzzle and create a playable puzzle
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = solution[i][j]; //See line 21
            }
        }

        // Randomly remove numbers to create a puzzle
        int remaining = 40; // Adjust difficulty by changing number of cells to remove
        while (remaining > 0) {
            int row = random.nextInt(9); //randomizes the placement of the blank numbers
            int col = random.nextInt(9);
            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0;
                remaining--; //only removes part of the puzzle by typing the solution in
            }
        }
    }

    public boolean checkSolution(int[][] board) {
        // Compare the provided board with the solution
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] != solution[row][col]) { //If the user is incorrect, then it needs to be changed
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public int[][] getSolution() {
        return solution;
    }
}