package game;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Bag {
    private final List<Tile> tileList = new ArrayList<>();

    public Bag() { // TODO for homework with set number of tiles and set points
    }

    public Bag(Random random) {
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            int points = random.nextInt(1, 11);
            addTilesCountTimes(10, ch, points);
        }
    }

    public void addTilesCountTimes(int count, char character, int points) {
        for (int no = 0; no < count; ++no) {
            tileList.add(new Tile(character, points));
        }
    }

    public synchronized List<Tile> extractTiles(int howMany) {
        Random random = new Random();
        List<Tile> extracted = new ArrayList<>();

        for (int i = 0; i < howMany; i++) {
            if (tileList.isEmpty()) {
                break;
            }

            Tile currTile = tileList.get(random.nextInt(0, tileList.size()));
            tileList.remove(currTile);
            extracted.add(currTile);
        }

        return extracted;
    }
}
