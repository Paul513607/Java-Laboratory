package game;

import lombok.Getter;
import lombok.Synchronized;

import java.util.Random;

/** Class for keeping track of the time in game and stopping the game if it has elapsed. */
@Getter(onMethod_ = {@Synchronized})
public class Timekeeper implements Runnable {
    private static final int GAME_MAX_TIME = 60 * 1000; // 1 minute
    private final Game game;

    private long startTime;
    private long endTime;
    private double duration;

    private static Timekeeper timekeeperInstance = null;

    private Timekeeper(Game game) {
        this.game = game;
    }

    public static Timekeeper getInstance(Game game) {
        if (timekeeperInstance == null)
            timekeeperInstance = new Timekeeper(game);

        return timekeeperInstance;
    }

    public synchronized void stopGame() {
        game.setGameOn(false);

        game.getBag().getTiles().clear();
        notifyAll();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(GAME_MAX_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[Timekeeper]: Time has elapsed!");

        stopGame();
    }

    public void displayMaximumTime() {
        System.out.println("Maximum time for the game: " + GAME_MAX_TIME);
    }

    public void displayRunningTime() {
        System.out.println("Time taken for the game: " + duration);
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
        duration = (double) (endTime - startTime) / 1000000000.0;
    }
}
