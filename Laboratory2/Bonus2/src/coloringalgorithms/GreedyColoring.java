package coloringalgorithms;

import graph.UnorderedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GreedyColoring {
    UnorderedGraph graph;
    ArrayList<Integer> nodePermutation;
    ArrayList<Integer> colors;

    public GreedyColoring(UnorderedGraph graph) {
        this.graph = graph;
        this.nodePermutation = new ArrayList<>(graph.getNumberOfNodes());
        this.generateNodePermutation();
        // We initialize the colors associated to the nodes (indexes) with -1 (i.e. not set yet)
        this.colors = new ArrayList<>(Collections.nCopies(graph.getNumberOfNodes(), -1));
    }

    // This is the solution
    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void printSolution() {
        int numberOfColors = -1;
        for (int node = 0; node < graph.getNumberOfNodes(); ++node)
            if (colors.get(node) > numberOfColors) numberOfColors = colors.get(node);
        System.out.println("Greedy Algorithm: (permutation: " + nodePermutation + ")");
        System.out.println("Number of colors used: " + (numberOfColors + 1));
        for (int node = 0; node < graph.getNumberOfNodes(); ++node)
            System.out.println("Node: " + node + ", color: " + colors.get(node));
    }

    public void generateNodePermutation() {
        Random rand = new Random();
        int nodeGen = 0;
        while (nodeGen < graph.getNumberOfNodes()) {
            int node = rand.nextInt(0, graph.getNumberOfNodes());
            if (!nodePermutation.contains(node)) {
                nodePermutation.add(node);
                nodeGen++;
            }
        }
    }

    public void greedyAlgorithm() {
        // We create a boolean list that tells us which colors from 0 to <numberOfNodes> - 1 are available. We initialize it with true values.
        ArrayList<Boolean> isColAvailable = new ArrayList<>(Collections.nCopies(graph.getNumberOfNodes(), true));
        // We set the color of the first node in our permutation to be color '0'
        colors.set(nodePermutation.get(0), 0);

        // We loop through the remaining nodes
        for (int index = 1; index < graph.getNumberOfNodes(); ++index) {
            int node = nodePermutation.get(index);
            // For every node in the current node's adjacency list we check whether we've set a color for it, so we'll know which colors are available
            for (int adjNode : graph.getAdjacencyListForNode(node)) {
                if (colors.get(adjNode) != -1)
                    isColAvailable.set(colors.get(adjNode), false);
            }

            // We find the first available color, and we set the current node's color
            int col;
            for (col = 0; col < graph.getNumberOfNodes(); ++col)
                if (isColAvailable.get(col))
                    break;
            colors.set(node, col);

            // We prepare the availability list for the next iteration
            for (col = 0; col < graph.getNumberOfNodes(); ++col)
                isColAvailable.set(col, true);
        }
    }
}
