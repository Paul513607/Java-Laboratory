package network;

import nodes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {
    Network network = new Network();

    @Test
    public void shouldAddNodes() {
        network.addNode(new Computer("v1", "0.0.3.0", new Location(-20.0, 10.0), "0.0.3.0", 30));
        network.addNode(new Router("v2", "1.0.0.0", new Location(10.0, -10.0), "1.0.7.0"));
        network.addNode(new Switch("v3", "2.3.0.0", new Location(-300.0, 10.0)));
        network.addNode(new Switch("v4", "4.4.0.0", new Location(-240.0, 50.0)));
        network.addNode(new Router("v5", "1.2.0.0", new Location(22.0, -16.0), "1.2.0.0"));
        network.addNode(new Computer("v6", "0.0.0.0", new Location(-444.0, 123.0), "0.0.0.0", 70));

        Assertions.assertTrue(network.getNodeList().size() == 6);
    }

    @Test
    public void shouldAddLinks() {
        shouldAddNodes();
        network.addLinkToNode("v1", "v2", 10, 0.3);
        network.addLinkToNode("v1", "v3", 50, 0.5);
        network.addLinkToNode("v2", "v3", 20, 0.1);
        network.addLinkToNode("v2", "v4", 20, 0.5);
        network.addLinkToNode("v2", "v5", 10, 0.7);
        network.addLinkToNode("v3", "v4", 20, 0.25);
        network.addLinkToNode("v4", "v5", 30, 0.54);
        network.addLinkToNode("v4", "v6", 10, 0.54);
        network.addLinkToNode("v5", "v6", 20, 0.12);

        Node currNode = network.getNodeList().get(network.getNodeIdIndex("v1"));
        Assertions.assertTrue(currNode.getLinksTimeCosts().size() == 2);
        currNode = network.getNodeList().get(network.getNodeIdIndex("v5"));
        Assertions.assertTrue(currNode.getLinksFailProbabilities().size() == 3);
    }
}