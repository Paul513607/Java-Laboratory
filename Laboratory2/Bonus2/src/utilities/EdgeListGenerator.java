package utilities;

import graph.Edge;

import java.util.ArrayList;
import java.util.Random;

public class EdgeListGenerator {
    // We generate a random number of edges that can be at least MIN_EDGES and at most MAX_EDGES as well as that amount of different edges.
    public static final int MIN_EDGES = 5;
    public static final int MAX_EDGES = 10;
    public static final int NUMBER_OF_NODES = 9; // We have at most NUMBER_OF_NODES nodes (from 0 to NUMBER_OF_NODES - 1)

    public ArrayList<Edge> generateRandomEdgesList() {
        Random rand = new Random();
        ArrayList<Edge> edges = new ArrayList<>();
        int edgesCount = rand.nextInt(MIN_EDGES, MAX_EDGES + 1);
        for (int i = 0; i < edgesCount; ++i) {
            int src = rand.nextInt(0, NUMBER_OF_NODES);
            int dest = src;
            while (dest == src) // We do not allow edges from a node to itself
                dest = rand.nextInt(0, NUMBER_OF_NODES);
            Edge edge = new Edge(src, dest);
            if (edges.contains(edge))   // If the edge already exists, we retry to generate it
                i--;
            else
                edges.add(edge);
        }
        return edges;
    }

    // Wheel graph with 9 nodes (and 16 edges)
    public ArrayList<Edge> inputEdgesList() {
        int edgesCount = 16;
        ArrayList<Edge> edges = new ArrayList<>(edgesCount);
        edges.add(new Edge(0, 1));
        edges.add(new Edge(1, 2));
        edges.add(new Edge(2, 3));
        edges.add(new Edge(3, 4));
        edges.add(new Edge(4, 5));
        edges.add(new Edge(5, 6));
        edges.add(new Edge(6, 7));
        edges.add(new Edge(7, 0));
        edges.add(new Edge(0, 8));
        edges.add(new Edge(1, 8));
        edges.add(new Edge(2, 8));
        edges.add(new Edge(3, 8));
        edges.add(new Edge(4, 8));
        edges.add(new Edge(5, 8));
        edges.add(new Edge(6, 8));
        edges.add(new Edge(7, 8));

        return edges;
    }
}
