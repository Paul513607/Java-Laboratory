package game;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/** Class which lets players 'add words' and output them on the screen. */
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@EqualsAndHashCode
public class Board {
    private final List<String> wordList = new ArrayList<>();

    public synchronized void addWord(Player player, String word) {
        wordList.add(word);
        System.out.println(player.getName() + ": " + word);
    }

    @Override
    public String toString() {
        return "Board{" +
                "wordList=" + wordList +
                '}';
    }
}
