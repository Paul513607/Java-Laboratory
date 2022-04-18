package game;

import lombok.*;

import java.util.*;

/** A class for the player logic. Players will be separate threads and each will play the game, extracting tiles and adding words to the board. */
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
@EqualsAndHashCode
public class Player implements Runnable {
    private String name;
    private Game game;
    private int totalPoints;

    List<Tile> chosenTiles = new ArrayList<>();

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

        List<Tile> extracted = new ArrayList<>(chosenTiles);
        extracted.addAll(game.getBag().extractTiles(this, 7 - chosenTiles.size()));

        if (extracted.size() < 7) {
            synchronized (game.getBoard()) {
                game.setGameOn(false);
                game.getBoard().notifyAll();
                return false;
            }
        }

        // try words of different sizes from the extracted tiles
        boolean foundWord = false;
        for (int wordSize = 7; wordSize >= 3; --wordSize) {
            List<Tile> extractedCopy = new ArrayList<>(extracted);

            chosenTiles.clear();
            Random random1 = new Random();
            chosenTiles = new ArrayList<>();
            while (chosenTiles.size() < wordSize && extractedCopy.size() > 0) {
                int index = random1.nextInt(0, extractedCopy.size());
                chosenTiles.add(extractedCopy.get(index));
                extractedCopy.remove(extractedCopy.get(index));
            }

            // We use a StringBuilder for faster concatenation of the letters.
            StringBuilder wordBuilder = new StringBuilder();
            for (Tile tile : chosenTiles) {
                wordBuilder.append(tile.getLetter());
            }
            String word = wordBuilder.toString();

            foundWord = checkForWord(word, chosenTiles);
            if (foundWord)
                break;

            // if we didn't find a word, we attempt to use the prefix search
            List<String> wordsWithPrefix = game.getDictionary().searchForGivenPrefix(word);
            List<Tile> remainingTiles = new ArrayList<>(extracted);
            remainingTiles.removeAll(chosenTiles);
            for (String newWord : wordsWithPrefix) {
                String remainingLetters = newWord.substring(word.length());
                boolean canFormWord = true;
                List<Tile> remainingTilesCopy = new ArrayList<>(remainingTiles);
                List<Tile> neededTiles = new ArrayList<>();

                for (int chIndex = 0; chIndex < remainingLetters.length(); ++chIndex) {
                    Tile tileForLetter = game.getBag().getTileByLetter(remainingLetters.charAt(chIndex));
                    if (!remainingTilesCopy.contains(tileForLetter)) {
                        canFormWord = false;
                        break;
                    }
                    else {
                        remainingTilesCopy.remove(tileForLetter);
                        neededTiles.add(tileForLetter);
                    }
                }

                if (canFormWord) {
                    chosenTiles.addAll(neededTiles);
                    foundWord = checkForWord(newWord, chosenTiles);
                }
            }

            if (foundWord)
                break;
        }

        if (foundWord) {
            extracted.removeAll(chosenTiles);
            chosenTiles.clear();
            chosenTiles.addAll(extracted);
        }
        else {
            for (Tile tile : extracted) {
                game.getBag().addTilesCountTimes(1, tile.getLetter(), tile.getPoints());
            }
            chosenTiles.clear();
        }

        return true;
    }

    private boolean checkForWord(String word, List<Tile> extracted) throws InterruptedException {
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
            return true;
        }
        return false;
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
