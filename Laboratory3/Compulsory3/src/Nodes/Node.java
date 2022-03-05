package Nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// An abstract representation on a network node
public abstract class Node {
    protected final String name;
    protected String macAddress;
    protected String mapLocation;
    // A map which holds the links to other nodes for the current node, as well as the cost of each of those links
    Map<Node, Double> linksTimeCosts;

    public Node(String name, String macAddress, String mapLocation) {
        this.name = name;
        this.macAddress = macAddress;
        this.mapLocation = mapLocation;
        linksTimeCosts = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMapLocation() {
        return mapLocation;
    }

    public void setMapLocation(String mapLocation) {
        this.mapLocation = mapLocation;
    }

    public Map<Node, Double> getLinksTimeCosts() {
        return linksTimeCosts;
    }

    public void setLinksTimeCosts(Map<Node, Double> timeCosts) {
        this.linksTimeCosts = timeCosts;
    }

    @Override
    public String toString() {
        return "Node{" +
                "macAddress='" + macAddress + '\'' +
                ", mapLocation='" + mapLocation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, macAddress, mapLocation);
    }

    public void addLink(Node node, double timeCost) {
        if (timeCost < 0) return;
        linksTimeCosts.put(node, timeCost);
    }
}
