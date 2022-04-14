package game;

import lombok.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/** A class for the player logic. Players will be separate threads and each will play the game, extracting tiles and adding words to the board. */
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
@EqualsAndHashCode
public class Player implements Runnable {
    private String name;
    private Game game;
    private int totalPoints;

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

    private boolean submitWord() throws InterruptedException {
        if (!game.isGameOn())
            return false;

        Random random = new Random();
        int howManyToExtract = random.nextInt(3, 11);
        List<Tile> extracted = game.getBag().extractTiles(this, howManyToExtract);

        if (extracted.isEmpty()) {
            synchronized (game.getBoard()) {
                game.setGameOn(false);
                game.getBoard().notifyAll();
                return false;
            }
        }

        // We use a StringBuilder for faster concatenation of the letters.
        StringBuilder wordBuilder = new StringBuilder();
        for (Tile tile : extracted) {
            wordBuilder.append(tile.getLetter());
        }
        String word = wordBuilder.toString();

        checkForWord(word, extracted);

        return true;
    }

    private void checkForWord(String word, List<Tile> extracted) throws InterruptedException {
        synchronized (game.getBoard()) {
            while (!game.getCurrPlayer().equals(this) && game.isGameOn()) {
                try {
                    game.getBoard().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (game.getDictionary().isWord(word)) {
            addWordPointsToTotal(word);
            game.getBoard().addWord(this, word);
            game.getDictionary().removeWord(word);

            Thread.sleep(500);

            synchronized (game.getBoard()) {
                // System.out.println(game.getBag().getTiles());
                game.moveToNextPlayer();
                game.getBoard().notify();
            }

            Thread.sleep(500);
        } else {
            // System.out.println("[" + name + "] Trying " + word + "...");
            for (Tile tile : extracted) {
                game.getBag().addTilesCountTimes(1, tile.getLetter(), tile.getPoints());
            }
        }
    }

    @Override
    public void run() {
        boolean canSubmit = true;
        while (canSubmit) {
            try {
                canSubmit = submitWord();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
