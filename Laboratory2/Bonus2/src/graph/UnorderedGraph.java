package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnorderedGraph {
    int numberOfNodes;
    private List<List<Integer>> adjacencyLists = new ArrayList<>();

    public UnorderedGraph(List<Edge> edges, int numberOfNodes) {
        // Initialize all adjacency lists
        this.numberOfNodes = numberOfNodes;
        for (int src = 0; src < numberOfNodes; ++src)
            this.adjacencyLists.add(src, new ArrayList<>());

        // Add destinations of edges to their respective adjacency lists
        for (Edge edge : edges) {
            this.adjacencyLists.get(edge.getSrc()).add(edge.getDest());
            this.adjacencyLists.get(edge.getDest()).add(edge.getSrc());
        }
        sortAdjacencyLists();
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public List<List<Integer>> getAdjacencyLists() {
        return adjacencyLists;
    }

    public List<Integer> getAdjacencyListForNode(int node) {
        return adjacencyLists.get(node);
    }

    public void sortAdjacencyLists() {
        for (int src = 0; src < numberOfNodes; ++src)
            Collections.sort(adjacencyLists.get(src));
    }

    public void printGraph() {
        for(int src = 0; src < adjacencyLists.size(); ++src) {
            System.out.print(src + ": ");
            for (int node : adjacencyLists.get(src))
                System.out.print(node + " ");
            System.out.print("\n");
        }
    }
}
