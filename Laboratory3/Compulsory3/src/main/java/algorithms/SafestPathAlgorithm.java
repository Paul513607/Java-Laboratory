package algorithms;

import network.Network;
import nodes.Node;

import java.util.*;

// As an instance for this algorithm we have a startNode, an endNode (and the network),
// and the solution will be a path from the startNode to the endNode in the network, such that the cumulative probability on that path is maximal
public class SafestPathAlgorithm {
    private double maxCumulatedProb;
    private List<Node> safestPath;
    private Network network;
    private Node startNode, endNode;

    public SafestPathAlgorithm(Network network) {
        this.maxCumulatedProb = 0;
        this.safestPath = new ArrayList<>();
        this.network = network;
    }

    public double getMaxCumulatedProb() {
        return maxCumulatedProb;
    }

    public List<Node> getSafestPath() {
        return safestPath;
    }

    public void printSafestPath() {
        System.out.println("Safest path between " + startNode.getName() + " and " + endNode.getName() + " is (cumulative probability: " + maxCumulatedProb + "):");
        safestPath.stream()
                .forEach(node -> System.out.println(node));
    }

    // This method initializes a visited map, which maps a node to either true/false (if the node has been visited or not) and adds the startNode to the solution
    public void getBestPath(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        Map<Node, Boolean> visitedNodes = new HashMap<>();
        for (Node node : network.getNodeList())
            visitedNodes.put(node, false);

        List<Node> pathsList = new ArrayList<>();

        pathsList.add(startNode);
        getPathsUtil(startNode, endNode, visitedNodes, pathsList);
    }

    // This method gets the cumulated probability for the current path
    private double getCumulatedProbOfPath(List<Node> pathsList) {
        if (pathsList.isEmpty() || pathsList.size() == 1) // failsafe
            return 0;

        double cumulatedProb = 1;
        Node prevNode = pathsList.get(0);
        for (int nodeIndex = 1; nodeIndex < pathsList.size(); ++nodeIndex) {
            Node currNode = pathsList.get(nodeIndex);
            cumulatedProb *= prevNode.getLinksFailProbabilities().get(currNode);
            prevNode = currNode;
        }
        return cumulatedProb;
    }

    // This method will go through all paths from startNode to endNode, saving the latest path if it's safer than the last one
    private void getPathsUtil(Node node1, Node node2, Map<Node, Boolean> visitedNodes, List<Node> pathsList) {
        // If we got to the last node, we calculate the cumulative probability of the current path and update the maximal one if needed
        if (node1.equals(node2)) {
            double cumulatedProb = getCumulatedProbOfPath(pathsList);
            if (cumulatedProb > maxCumulatedProb) {
                maxCumulatedProb = cumulatedProb;
                safestPath = (ArrayList<Node>) ((ArrayList<Node>) pathsList).clone();
            }
        }

        visitedNodes.put(node1, true);

        // We go through all the unvisited neighbors of the current node
        for (Node neighbor : node1.getLinksFailProbabilities().keySet()) {
            if (!visitedNodes.get(neighbor)) {
                pathsList.add(neighbor);
                getPathsUtil(neighbor, node2, visitedNodes, pathsList);
                // after the recursive call, we remove the node added previously since we might find another path with it
                pathsList.remove(neighbor);
            }
        }

        // after the recursive call, we un-visit the current node since we might find another path with it
        visitedNodes.put(node1, false);
    }
}
