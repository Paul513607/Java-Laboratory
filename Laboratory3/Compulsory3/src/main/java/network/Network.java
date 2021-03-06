package network;

import algorithms.DijkstraAlgorithm;
import nodes.Identifiable;
import nodes.Node;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

// A network contains a list of nodes which are identified by their IDs (names)
public class Network {
    private List<Node> nodeList;

    public Network() {
        this.nodeList = new ArrayList<>();
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodeList=" + nodeList +
                '}';
    }

    public int getNumberOfNodes() {
        return nodeList.size();
    }

    public void printNodeList() {
        System.out.println("The network's node list is:");
        for (Node node : nodeList)
            System.out.println(node);
    }

    public boolean existsNodeId(String nodeId) {
        for (Node node : nodeList)
            if (nodeId.equals(node.getName()))
                return true;
        return false;
    }

    public int getNodeIdIndex(String nodeId) {
        if (existsNodeId(nodeId)) {
            int index = 0;
            for (Node node : nodeList) {
                if (nodeId.equals(node.getName()))
                    return index;
                index++;
            }
        }
        return -1;
    }

    public void addNode(Node node) throws IllegalArgumentException {
        if (existsNodeId(node.getName()))
            throw new IllegalArgumentException("The node " + node + " already exists in the list.");
        else
            nodeList.add(node);
    }

    public void removeNode(Node node) {
        if (nodeList.contains(node))
            nodeList.remove(node);
    }

    public void addLinkToNode(String nodeId1, String nodeId2, double timeCost, double failProbability) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (timeCost < 0)
            throw new IllegalArgumentException("Time cost of link must be a positive number!");
        if (failProbability < 0 || failProbability > 1)
            throw new IllegalArgumentException("The probability of failure on a link must be a number in [0, 1]");

        int index1 = getNodeIdIndex(nodeId1);
        if (index1 == -1)
            throw new IndexOutOfBoundsException("Node with id: " + nodeId1 + " is not in the nodeList.");
        int index2 = getNodeIdIndex(nodeId2);
        if (index2 == -1)
            throw new IndexOutOfBoundsException("Node with id: " + nodeId2 + " is not in the nodeList.");
        Node node1 = nodeList.get(index1);
        Node node2 = nodeList.get(index2);
        // Because a link goes both ways, we add nodes to both of the parameter nodes
        node1.addLink(node2, timeCost, failProbability);
        node2.addLink(node1, timeCost, failProbability);
    }

    public void printNetworkSchema() {
        System.out.println("The network schema is:");
        for (Node node : nodeList) {
            System.out.print("Node: ");
            System.out.print(node);
            System.out.print(", with links:\n");
            node.getLinksTimeCosts().entrySet().stream()
                    .forEach(pair -> System.out.println("\t" + node.getName() + " -> " + pair.getKey().getName() +
                            " timeCost: " + pair.getValue() + ", failProbability: " + node.getLinksFailProbabilities().get(pair.getKey())));
        }
    }

    public List<Node> displayIdentifiableNodes() {
        List<Node> identifiableNodes = new ArrayList<>();
        for (Node node : nodeList) {
            if (node instanceof Identifiable)
                identifiableNodes.add(node);
        }
        identifiableNodes.sort(Comparator.comparing(o -> ((Identifiable) o).getIpAddress()));
        System.out.println("The identifiable nodes list:");
        for (Node node : identifiableNodes)
            System.out.println(node);
        return identifiableNodes;
    }

    public List<Node> getIdentifiableNodes() {
        List<Node> identifiableNodes = new ArrayList<>();
        for (Node node : nodeList) {
            if (node instanceof Identifiable)
                identifiableNodes.add(node);
        }
        identifiableNodes.sort(Comparator.comparing(o -> ((Identifiable) o).getIpAddress()));
        return identifiableNodes;
    }


    public void listShortestPathsIDNodes() {
        List<Node> identifiableNodes = new ArrayList<>();
        identifiableNodes = this.getIdentifiableNodes();
        System.out.print("\n\n\n");

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(this);
        // For each node in the identifiableNodes list, apply Dijkstra's shortest path algorithm
        for (Node node : identifiableNodes) {
            dijkstraAlgorithm.dijkstraAlgorithm(node);
            dijkstraAlgorithm.printMinCostPaths();
            System.out.print("\n\n");
        }
        System.out.print("\n\n\n");
    }
}
