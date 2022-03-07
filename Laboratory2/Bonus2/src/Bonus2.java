import coloringalgorithms.DSaturColoring;
import coloringalgorithms.GreedyColoring;
import graph.Edge;
import graph.UnorderedGraph;
import utilities.EdgeListGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Bonus2 {
    public static void main(String[] args) {
        EdgeListGenerator edgeListGenerator = new EdgeListGenerator();
        ArrayList<Edge> edges = new ArrayList<>();
        edges = edgeListGenerator.inputEdgesList();
        // edges = edgeListGenerator.generateRandomEdgesList();
        UnorderedGraph graph = new UnorderedGraph(edges, EdgeListGenerator.NUMBER_OF_NODES);

        graph.printGraph();
        System.out.println();

        GreedyColoring greedyColoring = new GreedyColoring(graph);
        greedyColoring.greedyAlgorithm();
        greedyColoring.printSolution();
        System.out.println();

        DSaturColoring dSaturColoring = new DSaturColoring(graph);
        dSaturColoring.dSaturAlgorithm();
        dSaturColoring.printSolution();
    }
}
