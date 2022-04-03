package game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
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
