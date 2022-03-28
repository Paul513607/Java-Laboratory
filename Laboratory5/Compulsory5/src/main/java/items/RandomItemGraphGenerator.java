package items;

import org.jgrapht.generate.GnmRandomBipartiteGraphGenerator;

import java.util.*;

public class RandomItemGraphGenerator {
    public final int MAX_NO_NODES = 30;
    public final int MAX_LINKS_PER_NODE = 8;

    public RandomItemGraphGenerator() {
    }

    private ClassificationType getRandomType() {
        Random random = new Random();
        int typeNo = random.nextInt(0, 13);
        return ClassificationType.values()[typeNo];
    }

    private List<Item> generateRandomItemList() {
        Random random = new Random();
        List<Item> listToGenerate = new ArrayList<>();

        int noNodes = random.nextInt(10, MAX_NO_NODES);
        for (int itemCount = 0; itemCount < noNodes; itemCount++) {
            int itemProb = random.nextInt(0, 2);
            Item item = null;
            if (itemProb == 0) {
                String bookId = "b" + itemCount;
                item = new Book(bookId);
            }
            else if (itemProb == 1) {
                String articleId = "a" + itemCount;
                item = new Article(articleId);
            }
            listToGenerate.add(item);
        }

        return listToGenerate;
    }

    public List<Item> generateItemClassifications() {
        Random random = new Random();
        List<Item> itemList = generateRandomItemList();

        for (Item item : itemList) {
            int noEdges = random.nextInt(0, MAX_LINKS_PER_NODE + 1);
            for (int count = 0; count < noEdges; ++count) {
                ClassificationType type;
                do {
                    type = getRandomType();
                } while (item.getClassificationTypesSet().contains(type));
                item.addClassification(type);
            }
        }
        return itemList;
    }
}
