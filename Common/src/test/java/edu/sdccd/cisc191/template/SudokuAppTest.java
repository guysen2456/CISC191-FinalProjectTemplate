package edu.sdccd.cisc191.template;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuAppTest {
    private SudokuApp app;
    private JFXPanel panel; // To initialize JavaFX runtime

    @BeforeEach
    void setUp() {
        panel = new JFXPanel(); // Initializes JavaFX environment
        app = new SudokuApp();
    }

    @Test
    void testHighScoreManager() { //Tests OOD
        HighScoreManager manager = new HighScoreManager();
        manager.addHighScore("Alice", 500);
        manager.addHighScore("Bob", 300);
    }

    @Test
    void testSudokuAppJavaFX() {// Tests JavaFX
        app.correctFills = 30;
        int score = app.calculateScore();
        assertEquals(3010, score);
    }

    @Test
    void testCollections() {// Test Collection
        HighScoreManager manager = new HighScoreManager();
        manager.addHighScore("Alice", 500);
        manager.addHighScore("Bob", 300);
        manager.addHighScore("Charlie", 700);

        List<HighScoreEntry> highScores = manager.getHighScores();
        Collections.sort(highScores, (entry1, entry2) -> Integer.compare(entry2.getScore(), entry1.getScore())); // Sort in descending order

        assertEquals(3, highScores.size());
        assertEquals("Charlie", highScores.get(0).getPlayerName());
        assertEquals("Alice", highScores.get(1).getPlayerName());
        assertEquals("Bob", highScores.get(2).getPlayerName());
    }


    @Test
    void testSudokuSolverBasics() {
        // Test Java Basics
        int[][] puzzle = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        assertTrue(SudokuSolver.solve(puzzle));

        int[][] board = new int[9][9];
        board[0][0] = 5;
        assertFalse(SudokuSolver.isValid(board, 0, 1, 5));
        assertTrue(SudokuSolver.isValid(board, 0, 1, 3));
    }
    @Test
    void IOTest(){
        String testFileName = "test_io_streams.txt";
        String content = "This is a test for I/O streams.";

        // Write content to file
        try (FileOutputStream fos = new FileOutputStream(testFileName)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception during writing to file");
        }
        StringBuilder readContent = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(testFileName)) {
            int ch;
            while ((ch = fis.read()) != -1) {
                readContent.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception during reading from file");
        }
        assertEquals(content, readContent.toString());
    }
}
