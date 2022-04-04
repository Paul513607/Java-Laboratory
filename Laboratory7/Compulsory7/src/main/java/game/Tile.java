package game;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
/** Class for a Tile in the game. A tile has a character and a number of points. */
public class Tile {
    private final char letter;
    private final int points;

    public Tile (char letter) {
        this.letter = letter;
        this.points = 0;
    }

    public Tile (char letter, int points) {
        this.letter = letter;
        this.points = points;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "letter=" + letter +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, points);
    }
}