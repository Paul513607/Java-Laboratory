import algorithms.DijkstraAlgorithm;
import algorithms.SafestPathAlgorithm;
import network.*;
import nodes.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
            network.addLinkToNode("v1", "v2", 10, 0.3);
            network.addLinkToNode("v1", "v3", 50, 0.5);
            network.addLinkToNode("v2", "v3", 20, 0.1);
            network.addLinkToNode("v2", "v4", 20, 0.5);
            network.addLinkToNode("v2", "v5", 10, 0.7);
            network.addLinkToNode("v3", "v4", 20, 0.25);
            network.addLinkToNode("v4", "v5", 30, 0.54);
            network.addLinkToNode("v4", "v6", 10, 0.54);
            network.addLinkToNode("v5", "v6", 20, 0.12);
        }
        catch (IndexOutOfBoundsException | IllegalArgumentException err) {
            err.printStackTrace();
        }

        network.printNodeList();
        System.out.print("\n\n\n");

        network.printNetworkSchema();
        System.out.print("\n\n");

        BigDecimal storageValue = ((Computer) network.getNodeList().get(0)).getCapacityInUnit(Storage.BytesUnits.B);
        System.out.println("Storage capacity for v1: " + storageValue + " B (Bytes)");
        System.out.print("\n\n");

        network.displayIdentifiableNodes();
        System.out.print("\n\n\n");
        network.listShortestPathsIDNodes();

        SafestPathAlgorithm safestPathAlgorithm = new SafestPathAlgorithm(network);
        safestPathAlgorithm.getBestPath(network.getNodeList().get(0), network.getNodeList().get(4));
        safestPathAlgorithm.printSafestPath();
    }
}
