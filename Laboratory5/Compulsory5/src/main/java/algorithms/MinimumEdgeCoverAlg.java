package algorithms;

import items.ClassificationType;
import items.Item;
import items.ItemGraph;
import items.ItemLink;
import org.bouncycastle.asn1.x509.IetfAttrSyntax;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MinimumEdgeCoverAlg {
    private ItemGraph<Object, ItemLink> itemGraph;
    private Set<ItemLink> maxCardMatchSet = new HashSet<>();
    private Set<Item> itemSet = new HashSet<>();
    private Set<ClassificationType> typeSet = new HashSet<>();
    private Map<Item, Integer> itemDegrees = new HashMap<>();
    private Map<ClassificationType, Integer> typeDegrees = new HashMap<>();

    public MinimumEdgeCoverAlg() {
    }

    public MinimumEdgeCoverAlg(ItemGraph<Object, ItemLink> itemGraph, Set<ItemLink> maxCardMatchSet) {
        this.itemGraph = itemGraph;
        for (ItemLink itemLink : maxCardMatchSet)
            if (itemLink != null)
                this.maxCardMatchSet.add(itemLink);
        processEdges();
    }

    private void processEdges() {
        for (ItemLink edge : maxCardMatchSet) {
            itemSet.add(edge.getItem());
            typeSet.add(edge.getType());
        }
    }

    private void findItemDegreesMap() {
        for (Item item : itemGraph.getItemList()) {
            if (!itemDegrees.containsKey(item)) {
                itemDegrees.put(item, 0);
                if (itemSet.contains(item))
                    itemDegrees.put(item, 1);
            }
        }
    }

    private void findTypeDegreesMap() {
        for (ClassificationType type : itemGraph.getTypeList()) {
            if (!typeDegrees.containsKey(type)) {
                typeDegrees.put(type, 0);
                if (itemSet.contains(type))
                    typeDegrees.put(type, 1);
            }
        }
    }

    private Item getUnusedItem() {
        for (Item item : itemSet) {
            if (itemDegrees.get(item) == 0)
                return item;
        }
        return null;
    }

    private ClassificationType getUnusedType() {
        for (ClassificationType type : typeSet) {
            if (typeDegrees.get(type) == 0)
                return type;
        }
        return null;
    }

    private ClassificationType getMinDegreeTypeOfItem(Item item) {
        Set<ClassificationType> currTypeSet = itemGraph.itemLinks(item);
        int minimumDegree = Integer.MAX_VALUE;
        ClassificationType minType = null;

        for (ClassificationType type : currTypeSet)
            if (typeDegrees.get(type) < minimumDegree) {
                minimumDegree = typeDegrees.get(type);
                minType = type;
            }
        return minType;
    }

    private Item getMinDegreeItemOfType(ClassificationType type) {
        Set<Item> currItemSet = itemGraph.typeLinks(type);
        int minimumDegree = Integer.MAX_VALUE;
        Item minItem = null;

        for (Item item : currItemSet)
            if (itemDegrees.get(item) < minimumDegree) {
                minimumDegree = itemDegrees.get(item);
                minItem = item;
            }
        return minItem;
    }

    public ItemGraph<Object, ItemLink> getItemGraph() {
        return itemGraph;
    }

    public void setItemGraph(ItemGraph<Object, ItemLink> itemGraph) {
        this.itemGraph = itemGraph;
    }

    public Set<ItemLink> getMaxCardMatchSet() {
        return maxCardMatchSet;
    }

    public void setMaxCardMatchSet(Set<ItemLink> maxCardMatchSet) {
        this.maxCardMatchSet = maxCardMatchSet;
    }

    @Override
    public String toString() {
        return "MinimumEdgeCoverAlg{" +
                "itemGraph=" + itemGraph +
                ", maxCardMatchSet=" + maxCardMatchSet +
                '}';
    }

    public Set<ItemLink> minimumEdgeCoverAlgRun() {
        findItemDegreesMap();
        findTypeDegreesMap();
        Set<ItemLink> minimumEdgeCover = new HashSet<>(maxCardMatchSet);

        Item currItem = getUnusedItem();
        while (currItem != null) {
            ClassificationType type = getMinDegreeTypeOfItem(currItem);
            if (type != null) {
                minimumEdgeCover.add(itemGraph.getEdge(currItem, type));
                typeDegrees.put(type, typeDegrees.get(type) + 1);
            }
            itemDegrees.put(currItem, itemDegrees.get(currItem) + 1);
            currItem = getUnusedItem();
        }

        ClassificationType currType = getUnusedType();
        while (currType != null) {
            Item item = getMinDegreeItemOfType(currType);
            if (item != null) {
                minimumEdgeCover.add(itemGraph.getEdge(item, currType));
                itemDegrees.put(item, itemDegrees.get(item) + 1);
            }
            typeDegrees.put(currType, typeDegrees.get(currType) + 1);
            currType = getUnusedType();
        }

        return minimumEdgeCover;
    }
}
