package algorithms;

import items.*;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HopcroftKarpMaximumCardinalityMatchingAlg {
    private ItemGraph<Object, ItemLink> itemGraph = new ItemGraph<>();

    public HopcroftKarpMaximumCardinalityMatchingAlg() {
        RandomItemGraphGenerator randomItemGraphGenerator = new RandomItemGraphGenerator();
        this.itemGraph = new ItemGraph<>(randomItemGraphGenerator.generateItemClassifications());
    }

    public HopcroftKarpMaximumCardinalityMatchingAlg(ItemGraph<Object, ItemLink> itemGraph) {
        this.itemGraph = itemGraph;
    }

    public ItemGraph<Object, ItemLink> getItemGraph() {
        return itemGraph;
    }

    public void setItemGraph(ItemGraph<Object, ItemLink> itemGraph) {
        this.itemGraph = itemGraph;
    }

    @Override
    public String toString() {
        return "HopcroftKarpMaximumCardinalityMatching{" +
                "itemGraph=" + itemGraph +
                '}';
    }

    public Set<ItemLink> hopcroftKarpMaximumCardinalityMatchingAlgRun() {
        Set<Object> itemSet = new HashSet<>(itemGraph.getItemList());;
        Set<Object> typeSet = new HashSet<>(itemGraph.getTypeList());
        HopcroftKarpMaximumCardinalityBipartiteMatching<Object, ItemLink> hopcroftKarpMaximumCardinalityBipartiteMatching
                = new HopcroftKarpMaximumCardinalityBipartiteMatching<>(itemGraph, itemSet, typeSet);

        return hopcroftKarpMaximumCardinalityBipartiteMatching.getMatching().getEdges();
    }
}
