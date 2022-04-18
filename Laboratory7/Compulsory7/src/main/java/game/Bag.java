package game;


import lombok.*;

import java.util.*;

/** Class where we store the tiles and from which the player can select a number of tiles. */
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
@EqualsAndHashCode
public class Bag {
    private final Map<Tile, Integer> tiles = new HashMap<>();
    private String availableTiles = "";

    public Bag() {
        addTilesCountTimes(9, 'A', 1);
        addTilesCountTimes(2, 'B', 3);
        addTilesCountTimes(2, 'C', 3);
        addTilesCountTimes(4, 'D', 2);
        addTilesCountTimes(12, 'E', 1);
        addTilesCountTimes(2, 'F', 4);
        addTilesCountTimes(3, 'G', 2);
        addTilesCountTimes(2, 'H', 4);
        addTilesCountTimes(9, 'I', 1);
        addTilesCountTimes(1, 'J', 8);
        addTilesCountTimes(1, 'K', 5);
        addTilesCountTimes(4, 'L', 1);
        addTilesCountTimes(2, 'M', 3);
        addTilesCountTimes(6, 'N', 1);
        addTilesCountTimes(8, 'O', 1);
        addTilesCountTimes(2, 'P', 3);
        addTilesCountTimes(1, 'Q', 10);
        addTilesCountTimes(6, 'R', 1);
        addTilesCountTimes(4, 'S', 1);
        addTilesCountTimes(6, 'T', 1);
        addTilesCountTimes(4, 'U', 1);
        addTilesCountTimes(2, 'V', 4);
        addTilesCountTimes(2, 'W', 4);
        addTilesCountTimes(1, 'X', 8);
        addTilesCountTimes(2, 'Y', 4);
        addTilesCountTimes(1, 'Z', 10);
    }

    public Bag(Random random) {
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            int points = random.nextInt(1, 11);
            addTilesCountTimes(10, ch, points);
        }
    }

    public static boolean isCharTileUpper(char character) {
        return (character >= 'A' && character <= 'Z');
    }

    public void addTilesCountTimes(int count, char character, int points) {
        if (!isCharTileUpper(character))
            character -= 32;

        if (availableTiles.indexOf(character) == -1);
            availableTiles = availableTiles + character;
        for (int no = 0; no < count; ++no) {
            Tile newTile = new Tile(character, points);
            if (!tiles.containsKey(newTile))
                tiles.put(newTile, 1);
            else
                tiles.put(newTile, tiles.get(newTile) + 1);
        }
    }

    public synchronized int getPointsForLetter(char letter) {
        for (Tile tile : tiles.keySet()) {
            if (tile.getLetter() == letter)
                return tile.getPoints();
        }
        return 0;
    }

    public synchronized Tile getTileByLetter(char letter) {
        for (Tile tileEntry : tiles.keySet()) {
            if (tileEntry.getLetter() == letter)
                return tileEntry;
        }
        return null;
    }

    public boolean isTileMapEmpty() {
        for (Tile tile : tiles.keySet())
            if (tiles.get(tile) > 0)
                return false;
        return true;
    }

    public synchronized List<Tile> extractTiles(Player player, int howMany) {
        Random random = new Random();
        List<Tile> extracted = new ArrayList<>();

        for (int counter = 0; counter < howMany; ++counter) {
            if (isTileMapEmpty()) {
                break;
            }

            int toExtractIndex = random.nextInt(0, availableTiles.length());
            char toExtract = availableTiles.charAt(toExtractIndex);
            Tile currTile = getTileByLetter(toExtract);
            if (currTile == null) {
                counter--;
                continue;
            }

            if (tiles.get(currTile) > 0) {
                tiles.put(currTile, tiles.get(currTile) - 1);
                extracted.add(currTile);
            }
            else {
                counter--;
                availableTiles = availableTiles.replaceAll(("" + currTile.getLetter()), "");
            }
        }

        return extracted;
    }
}
