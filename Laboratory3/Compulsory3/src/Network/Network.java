package Network;

import Nodes.Node;

import java.util.ArrayList;

public class Network {
    private ArrayList<Node> nodeList = new ArrayList<>();
    private ArrayList<NetworkEdge> networkEdges = new ArrayList<>();

    public Network() {
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public ArrayList<NetworkEdge> getNetworkEdges() {
        return networkEdges;
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodeList=" + nodeList +
                ", networkEdges=" + networkEdges +
                '}';
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

    public void addNode(Node node) throws IllegalArgumentException {
        if (existsNodeId(node.getName()))
            throw new IllegalArgumentException("The node " + node + " already exists in the list.");
        else
            nodeList.add(node);
    }

    public void addEdge(NetworkEdge edge) throws IllegalArgumentException {
        if (!existsNodeId(edge.getSrcNode()) || !existsNodeId(edge.getDestNode()))
            throw new IllegalArgumentException("Either the src or dest node does not exist in the nodeList.");
        else
            networkEdges.add(edge);
    }

    public void removeNode(Node node) {
        if (nodeList.contains(node))
            nodeList.remove(node);
        for (NetworkEdge edge : networkEdges)
            if (edge.getSrcNode().equals(node)  || edge.getDestNode().equals(node))
                networkEdges.remove(edge);
    }

    public void removeNetworkEdge(NetworkEdge edge) {
        if (networkEdges.contains(edge))
            networkEdges.remove(edge);
    }
}
