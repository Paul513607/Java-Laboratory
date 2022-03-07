package algorithms;

import network.Network;
import nodes.Node;

import java.util.*;

// As an instance for this algorithm we have a startNode (and the network),
// and the solution will be a list of minimal paths from the startNode to the others in the network
public class DijkstraAlgorithm {
    static public final double INFINITY = Double.MAX_VALUE;
    private Node startNode;
    private Map<Node, Double> minCostPaths;

    public DijkstraAlgorithm(Node node) {
        this.startNode = node;
        this.minCostPaths = new TreeMap<>(Comparator.comparing(Node::getName));
    }

    public Map<Node, Double> getMinCostPaths() {
        return minCostPaths;
    }

    public void printMinCostPaths() {
        System.out.println("The minimum cost paths for node " + startNode.getName() + ": ");
        minCostPaths.entrySet().stream()
                .forEach(pair -> System.out.println(pair.getKey() + ": " + pair.getValue()));
    }

    // This function returns the minimum distance cost from all unvisited nodes
    public Map.Entry<Node, Double> getLowestUnvisitedDistance(Map<Node, Boolean> visited) {
        double min = Integer.MAX_VALUE;
        Node minNode = null;
        for (Node node : minCostPaths.keySet())
            if (!visited.get(node) && minCostPaths.get(node) < min) {
                min = minCostPaths.get(node);
                minNode = node;
            }
        return new AbstractMap.SimpleEntry<>(minNode, min);
    }

    // We use this algorithm to find the minimum path from the starting node to the other nodes in the network
    public void dijkstraAlgorithm(Network network) {
        // Initially all the nodes are not visited
        Map<Node, Boolean> visited = new HashMap<>();
        for (Node node : network.getNodeList())
            visited.put(node, false);

        // We initialize the paths to the nodes connected to the first node
        for (Node node : network.getNodeList()) {
            if (startNode.getLinksTimeCosts().containsKey(node))
                minCostPaths.put(node, startNode.getLinksTimeCosts().get(node));
            else
                minCostPaths.put(node, INFINITY);
        }

        // We initialize the path from the first node to the first node, and we visit it
        minCostPaths.put(startNode, 0.0);
        visited.put(startNode, true);

        // While there are still not visited nodes
        while (visited.values().contains(false)) {
            // We get the unvisited node with the minimum path cost to it as well as that cost, and we visit it
            Map.Entry<Node, Double> pair = getLowestUnvisitedDistance(visited);
            Node minNode = pair.getKey();
            double minCost = pair.getValue();
            visited.put(minNode, true);

            // For all the neighbors of the node we got earlier,
            // if they are not visited, we update the cost if we find a smaller one
            for (Node node : minNode.getLinksTimeCosts().keySet())
                if (!visited.get(node))
                    if (minCost + minNode.getLinksTimeCosts().get(node) < minCostPaths.get(node)) {
                        minCostPaths.put(node, minCost  + minNode.getLinksTimeCosts().get(node));
                    }
        }
        // In the end we will have the solution in the 'minCostPaths' attribute
    }
}
