package edu.sdccd.cisc191.template;

import java.io.Serializable; //Serializable saves the player information and score to a separate file

public class HighScoreEntry implements Serializable {
    private String playerName;
    private int score;

    public HighScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }
    //For the getters, I obviously want to get the name and high score
    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }


    @Override
    public String toString() {
        return playerName + " - Score: " + score; //When finished with puzzle, the window displays your name along with the high score
    }
}


