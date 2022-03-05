import Network.*;
import Nodes.*;

import java.util.ArrayList;

public class Compulsory3 {
    public static void main(String[] args) {
        Network network = new Network();

        try {
            network.addNode(new Computer("v1", "0.0.0.0", "N:-20 S:20", "0.0.0.0", 30));
            network.addNode(new Router("v2", "0.0.0.0", "N:10 S:-10", "0.0.0.0"));
            network.addNode(new Switch("v3", "0.0.0.0", "N:-300 S:10"));
            network.addNode(new Switch("v4", "0.0.0.0", "N:-240 S:50"));
            network.addNode(new Router("v5", "0.0.0.0", "N:22 S:-16", "0.0.0.0"));
            network.addNode(new Computer("v6", "0.0.0.0", "N:-444 S:123", "0.0.0.0", 70));
        }
        catch (IllegalArgumentException err) {
            err.printStackTrace();
        }

        try {
            network.addEdge(new NetworkEdge("v1", "v2", 10));
            network.addEdge(new NetworkEdge("v1", "v3", 50));
            network.addEdge(new NetworkEdge("v2", "v3", 20));
            network.addEdge(new NetworkEdge("v2", "v4", 20));
            network.addEdge(new NetworkEdge("v2", "v5", 10));
            network.addEdge(new NetworkEdge("v3", "v4", 20));
            network.addEdge(new NetworkEdge("v4", "v5", 30));
            network.addEdge(new NetworkEdge("v4", "v6", 10));
            network.addEdge(new NetworkEdge("v5", "v6", 20));
        }
        catch (IllegalArgumentException err) {
            err.printStackTrace();
        }

        network.printNodeList();
    }
}
