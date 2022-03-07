package coloringalgorithms;

import graph.UnorderedGraph;

import java.util.*;

public class DSaturColoring {
    UnorderedGraph graph;
    ArrayList<Integer> saturationDegree;
    ArrayList<Integer> colors;

    public DSaturColoring(UnorderedGraph graph) {
        this.graph = graph;
        // The saturationDegree list tells us for a node at <index> how many adjacent nodes are colored. We initialize it with 0's.
        this.saturationDegree = new ArrayList<>(Collections.nCopies(graph.getNumberOfNodes(), 0));
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
        System.out.println("DSatur Algorithm: ");
        System.out.println("Number of colors used: " + (numberOfColors + 1));
        for (int node = 0; node < graph.getNumberOfNodes(); ++node)
            System.out.println("Node: " + node + ", color: " + colors.get(node));
    }

    public int getMaxUncoloredSaturation() {
        int max = -1;
        for (int index = 0; index < graph.getNumberOfNodes(); ++index)
            if (colors.get(index) == -1 && saturationDegree.get(index) > max) max = saturationDegree.get(index);
        return max;
    }

    public void dSaturAlgorithm() {
        // We create a boolean list that tells us which colors from 0 to <numberOfNodes> - 1 are available. We initialize it with true values.
        ArrayList<Boolean> isColAvailable = new ArrayList<>(Collections.nCopies(graph.getNumberOfNodes(), true));

        // We pair the nodes with their degrees and sort the list of pairs in descending order of the degrees
        ArrayList<Map.Entry<Integer, Integer>> nodesToDegrees = new ArrayList<>(graph.getNumberOfNodes());
        for (int node = 0; node < graph.getNumberOfNodes(); ++node)
            nodesToDegrees.add(new AbstractMap.SimpleEntry<>(node, graph.getAdjacencyListForNode(node).size()));
        Collections.sort(nodesToDegrees, (o1, o2) -> (-1) * o1.getValue().compareTo(o2.getValue()));
        System.out.println(nodesToDegrees);

        // We initialize the highest degree node with the first color, and we increment it's adjacent nodes saturation.
        int firstNode = nodesToDegrees.get(0).getKey();
        colors.set(firstNode, 0);
        for (int adjNode : graph.getAdjacencyListForNode(firstNode))
            saturationDegree.set(adjNode, saturationDegree.get(adjNode) + 1);
        // We iterate <numberOfNodes> times
        for (int count = 1; count < graph.getNumberOfNodes(); ++count) {
            int node = 0;
            int maxSaturation = getMaxUncoloredSaturation();
            /* We select the node with the biggest saturation that hasn't been colored. In case there's more than one with maxSaturation,
               iterating through the list of pairs created and sorted earlier gives us the one with the highest degree. */
            for (int index = 1; index < graph.getNumberOfNodes(); ++index) {
                node = nodesToDegrees.get(index).getKey();
                if (saturationDegree.get(node) == maxSaturation && colors.get(node) == -1)
                    break;
            }

            // For every node in the current selected node's adjacency list we check whether we've set a color for it, so we'll know which colors are available
            // Also while traversing the current node's adjacency list, we can increment their saturation
            for (int adjNode : graph.getAdjacencyListForNode(node)) {
                saturationDegree.set(adjNode, saturationDegree.get(adjNode) + 1);
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
