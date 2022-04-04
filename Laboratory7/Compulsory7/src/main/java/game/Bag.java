package game;


import lombok.Data;

import java.util.*;

@Data
/** Class where we store the tiles and from which the player can select a number of tiles. */
public class Bag {
    private final Map<Tile, Integer> tiles = new HashMap<>();
    private final List<Tile> boardTiles = new ArrayList<>();

    public Bag() { // TODO for homework with set number of tiles and set points
    }

    public Bag(Random random) {
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            int points = random.nextInt(1, 11);
            addTilesCountTimes(10, ch, points);
            boardTiles.add(new Tile(ch, points));
        }
    }

    public void addTilesCountTimes(int count, char character, int points) {
        for (int no = 0; no < count; ++no) {
            Tile newTile = new Tile(character, points);
            if (!tiles.containsKey(newTile))
                tiles.put(newTile, 1);
            else
                tiles.put(newTile, tiles.get(newTile) + 1);
        }
    }

    public int getPointsForLetter(char letter) {
        for (Tile tile : boardTiles) {
            if (tile.getLetter() == letter)
                return tile.getPoints();
        }
        return 0;
    }

    public Tile getTileByLetter(char letter) {
        for (Tile tileEntry : tiles.keySet()) {
            if (tileEntry.getLetter() == letter)
                return tileEntry;
        }
        return null;
    }

    public synchronized List<Tile> extractTiles(int howMany) {
        Random random = new Random();
        List<Tile> extracted = new ArrayList<>();

        for (int counter = 0; counter < howMany; ++counter) {
            if (tiles.isEmpty()) {
                break;
            }

            char toExtract = (char) random.nextInt('A', 'Z' + 1);
            Tile currTile = getTileByLetter(toExtract);
            if (currTile == null) {
                counter--;
                continue;
            }

            tiles.put(currTile, tiles.get(currTile) - 1);
            if (tiles.get(currTile) == 0) {
                tiles.remove(currTile);
            }

            extracted.add(currTile);
        }

        return extracted;
    }
}
