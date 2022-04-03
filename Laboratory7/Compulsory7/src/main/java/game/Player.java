package game;

import lombok.Data;

import java.util.List;
import java.util.Scanner;

@Data
public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running;

    public Player(String name) {
        this.name = name;
    }

    private boolean isValid(String word, List<Tile> extracted) {
        if (word == null)
            return false;

        for (int index = 0; index < word.length(); index++)
            if (!extracted.contains(new Tile(word.charAt(index))))
                return false;
        return true;
    }

    private boolean submitWord() {
        List<Tile> extracted = game.getBag().extractTiles(7);

        if (extracted.isEmpty()) {
            return false;
        }

        StringBuilder wordBuilder = new StringBuilder();
        for (Tile tile : extracted) {
            wordBuilder.append(tile.getLetter());
        }
        String word = wordBuilder.toString();

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
    }
}
