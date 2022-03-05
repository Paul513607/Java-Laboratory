package Nodes;

import java.util.Objects;

public abstract class Node {
    protected final String name;
    protected String macAddress;
    protected String mapLocation;

    public Node(String name, String macAddress, String mapLocation) {
        this.name = name;
        this.macAddress = macAddress;
        this.mapLocation = mapLocation;
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
}
