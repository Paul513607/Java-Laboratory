package game;

import game.prefixtreeutil.PrefixTreeManager;
import lombok.*;

import java.io.FileNotFoundException;
import java.util.*;

/** The class which manages the game. An object of this class adds players and starts the game. It contains the main method. */
@Getter(onMethod_ = {@Synchronized})
public class Game {
    private final Bag bag = new Bag();
    private final Board board = new Board();
    private Dictionary dictionary = new PrefixTreeDictionary();
    private Timekeeper timekeeper;

    private final List<Player> players = new ArrayList<>();
    private final List<Thread> playerThreads = new ArrayList<>();
    private boolean isGameOn = true;
    private Player currPlayer;
    private int currPlayerIndex = 0;

    public void setGameOn(boolean gameOn) {
        isGameOn = gameOn;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void initDictionary(String filePath) {
        try {
            // dictionary.readDictionaryFromFile(filePath);
            System.out.println("Done reading");

            PrefixTreeManager treeManager = new PrefixTreeManager();
            // treeManager.saveDictionary((PrefixTreeDictionary) this.dictionary);
            this.dictionary = treeManager.loadDictionary();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public void addTimekeeper() {
        timekeeper = Timekeeper.getInstance(this);
    }

    public void play() {
        currPlayer = players.get(currPlayerIndex);
        for (Player player : players) {
            Thread playerThread = new Thread(player);
            playerThreads.add(playerThread);
            playerThread.start();
        }

        Thread timekeeperThread = new Thread(timekeeper);
        timekeeperThread.setDaemon(true);
        timekeeperThread.start();
    }

    public void moveToNextPlayer() {
        currPlayerIndex++;
        if (currPlayerIndex == players.size())
            currPlayerIndex = 0;
        currPlayer = players.get(currPlayerIndex);
    }

    public void joinPlayers() {
        for (Thread thread : playerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void determinePlacements() {
        players.sort((o1, o2) -> (-1) * Integer.compare(o1.getTotalPoints(), o2.getTotalPoints()));
        for (int placement = 1; placement <= players.size(); ++placement)
            System.out.println("Place " + placement + ": " + players.get(placement - 1).getName());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initDictionary("src/main/resources/dictionary-words/dictionary/en_US-custom.dic");
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));
        game.addTimekeeper();

        game.getTimekeeper().startTimer();

        game.play();
        game.joinPlayers();
        game.determinePlacements();

        game.getTimekeeper().stopTimer();
        game.getTimekeeper().displayRunningTime();
    }
}
