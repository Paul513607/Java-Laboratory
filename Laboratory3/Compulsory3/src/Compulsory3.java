import Algorithms.DijkstraAlgorithm;
import Network.*;
import Nodes.*;

import java.math.BigDecimal;

public class Compulsory3 {
    public static void main(String[] args) {
        Network network = new Network();

        try {
            network.addNode(new Computer("v1", "0.0.3.0", "N:-20 S:20", "0.0.3.0", 30));
            network.addNode(new Router("v2", "1.0.0.0", "N:10 S:-10", "1.0.7.0"));
            network.addNode(new Switch("v3", "2.3.0.0", "N:-300 S:10"));
            network.addNode(new Switch("v4", "4.4.0.0", "N:-240 S:50"));
            network.addNode(new Router("v5", "1.2.0.0", "N:22 S:-16", "1.2.0.0"));
            network.addNode(new Computer("v6", "0.0.0.0", "N:-444 S:123", "0.0.0.0", 70));
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
