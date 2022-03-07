import algorithms.DijkstraAlgorithm;
import network.*;
import nodes.*;

import java.math.BigDecimal;

public class Compulsory3 {
    public static void main(String[] args) {
        Network network = new Network();

        try {
            network.addNode(new Computer("v1", "0.0.3.0", new Location(-20.0, 10.0), "0.0.3.0", 30));
            network.addNode(new Router("v2", "1.0.0.0", new Location(10.0, -10.0), "1.0.7.0"));
            network.addNode(new Switch("v3", "2.3.0.0", new Location(-300.0, 10.0)));
            network.addNode(new Switch("v4", "4.4.0.0", new Location(-240.0, 50.0)));
            network.addNode(new Router("v5", "1.2.0.0", new Location(22.0, -16.0), "1.2.0.0"));
            network.addNode(new Computer("v6", "0.0.0.0", new Location(-444.0, 123.0), "0.0.0.0", 70));
        }
        catch (IllegalArgumentException err) {
            err.printStackTrace();
        }

        try {
            network.addLinkToNode("v1", "v2", 10);
            network.addLinkToNode("v1", "v3", 50);
            network.addLinkToNode("v2", "v3", 20);
            network.addLinkToNode("v2", "v4", 20);
            network.addLinkToNode("v2", "v5", 10);
            network.addLinkToNode("v3", "v4", 20);
            network.addLinkToNode("v4", "v5", 30);
            network.addLinkToNode("v4", "v6", 10);
            network.addLinkToNode("v5", "v6", 20);
        }
        catch (IndexOutOfBoundsException err) {
            err.printStackTrace();
        }

        network.printNodeList();
        System.out.print("\n\n\n");

        network.printNetworkSchema();
        System.out.print("\n\n\n");

        BigDecimal storageValue = ((Computer) network.getNodeList().get(0)).getCapacityInUnit(Storage.BytesUnits.B);
        System.out.println("Storage capacity for v1: " + storageValue + " B (Bytes)");
        System.out.print("\n\n\n");

        network.displayIdentifiableNodes();
        System.out.print("\n\n\n");

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(network.getNodeList().get(0));
        dijkstraAlgorithm.dijkstraAlgorithm(network);
        dijkstraAlgorithm.printMinCostPaths();
    }
}
