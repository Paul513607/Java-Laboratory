package game;

import lombok.Data;

import java.util.List;
import java.util.Scanner;

@Data
/** A class for the player logic. Players will be separate threads and each will play the game, extracting tiles and adding words to the board. */
public class Player implements Runnable {
    private String name;
    private Game game;
    private int totalPoints;
    private boolean running;

    public Player(String name) {
        this.name = name;
        this.totalPoints = 0;
    }

    private boolean isValid(String word, List<Tile> extracted) {
        if (word == null)
            return false;

        for (int index = 0; index < word.length(); index++)
            if (!extracted.contains(new Tile(word.charAt(index))))
                return false;
        return true;
    }

    private void addWordPointsToTotal(String word) {
        for (int index = 0; index < word.length(); ++index) {
            int currTilePoints = game.getBag().getPointsForLetter(word.charAt(index));
            totalPoints += currTilePoints;
        }
    }

    private boolean submitWord() {
        List<Tile> extracted = game.getBag().extractTiles(7);

        if (extracted.isEmpty()) {
            return false;
        }

        // We use a StringBuilder for faster concatenation of the letters.
        StringBuilder wordBuilder = new StringBuilder();
        for (Tile tile : extracted) {
            wordBuilder.append(tile.getLetter());
        }
        String word = wordBuilder.toString();

        addWordPointsToTotal(word);
        game.getBoard().addWord(this, word);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void run() {
        boolean canSubmit = true;
        while (canSubmit) {
            canSubmit = submitWord();
        }

        // Pause for 0.5 seconds then print the totalPoints
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total points for " + name + " is: " + totalPoints);
    }
}
