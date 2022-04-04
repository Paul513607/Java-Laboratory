package game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
/** The class which manages the game. An object of this class adds players and starts the game. It contains the main method. */
public class Game {
    private final Bag bag = new Bag(new Random());
    private final Board board = new Board();
    private final Dictionary dictionary = new MockDictionary();
    private final List<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public void play() {
        for (Player player : players) {
            Thread playerThread = new Thread(player);
            playerThread.start();
        }
    }

    public static void main(String args[]) {
        Game game = new Game();
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));
        game.play();
    }
}
