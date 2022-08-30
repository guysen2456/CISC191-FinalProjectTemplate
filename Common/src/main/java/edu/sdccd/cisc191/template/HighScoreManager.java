package edu.sdccd.cisc191.template;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
//Since there can be high scores, importing java.util.List can allow duplicates of different high scores
import java.util.List;

public class HighScoreManager {
    private static final String FILE_NAME = "highscores.ser";
    private List<HighScoreEntry> highScores;

    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }

    public void addHighScore(String playerName, int score) {
        HighScoreEntry entry = new HighScoreEntry(playerName, score);
        highScores.add(entry);
        //Collections helps me list how many people finished this game and how many points they accumulated
        //Integer.compare() compares high score with different players
        Collections.sort(highScores, (entry1, entry2) -> Integer.compare(entry1.getScore(), entry2.getScore()));
        saveHighScores();
    }

    public List<HighScoreEntry> getHighScores() {
        return new ArrayList<>(highScores);
    }

    private void saveHighScores() {
        //FileOutputStream helps me write new data to a file, it can be read by FileInputStream
        //ObjectOutputStream provides new object to write with new file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHighScores() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            highScores = (List<HighScoreEntry>) in.readObject();
        } catch (FileNotFoundException e) {
            // File not found, starting with an empty high score list

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


