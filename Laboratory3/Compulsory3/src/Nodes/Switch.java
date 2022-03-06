package Nodes;

// A switch is a node
public class Switch extends Node{
    public Switch(String name, String macAddress, Location mapLocation) {
        super(name, macAddress, mapLocation);
    }

    @Override
    public String toString() {
        return "Switch{" +
                "name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", mapLocation=" + mapLocation +
                '}';
    }
}
