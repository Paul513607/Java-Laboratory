package algorithms;

import network.*;
import nodes.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SafestPathAlgorithmTest {
    Network network;
    double maxCumulativeProb;
    List<Node> safestPathTest;

    @BeforeEach
    public void setNetwork() {
        network = new Network();
        maxCumulativeProb = 0;
        safestPathTest = new ArrayList<>();

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
    }

    @Test
    public void isPathMinimal1() {
        List<Node> safestList = new ArrayList<>();
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v1")));
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v2")));
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v5")));

        SafestPathAlgorithm safestPathAlgorithm = new SafestPathAlgorithm(network);
        safestPathAlgorithm.getBestPath(network.getNodeList().get(0), network.getNodeList().get(4));

        double currMaxValue = ((int) (safestPathAlgorithm.getMaxCumulatedProb() * 10000)) / 10000.0;
        Assertions.assertEquals(0.21, currMaxValue);
        Assertions.assertEquals(safestList, safestPathAlgorithm.getSafestPath());

        maxCumulativeProb = safestPathAlgorithm.getMaxCumulatedProb();
        safestPathTest = safestPathAlgorithm.getSafestPath();
    }

    @Test
    public void isPathMinimal2() {
        List<Node> safestList = new ArrayList<>();
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v6")));
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v4")));
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v3")));

        SafestPathAlgorithm safestPathAlgorithm = new SafestPathAlgorithm(network);
        safestPathAlgorithm.getBestPath(network.getNodeList().get(5), network.getNodeList().get(2));

        double currMaxValue = ((int) (safestPathAlgorithm.getMaxCumulatedProb() * 10000)) / 10000.0;
        Assertions.assertEquals(0.135, currMaxValue);
        Assertions.assertEquals(safestList, safestPathAlgorithm.getSafestPath());

        maxCumulativeProb = safestPathAlgorithm.getMaxCumulatedProb();
        safestPathTest = safestPathAlgorithm.getSafestPath();
    }

    @Test
    public void isPathMinimal3() {
        List<Node> safestList = new ArrayList<>();
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v2")));
        safestList.add(network.getNodeList().get(network.getNodeIdIndex("v4")));

        SafestPathAlgorithm safestPathAlgorithm = new SafestPathAlgorithm(network);
        safestPathAlgorithm.getBestPath(network.getNodeList().get(1), network.getNodeList().get(3));

        double currMaxValue = ((int) (safestPathAlgorithm.getMaxCumulatedProb() * 10000)) / 10000.0;
        Assertions.assertEquals(0.5, currMaxValue);
        Assertions.assertEquals(safestList, safestPathAlgorithm.getSafestPath());

        maxCumulativeProb = safestPathAlgorithm.getMaxCumulatedProb();
        safestPathTest = safestPathAlgorithm.getSafestPath();
    }

    @AfterEach
    public void printResult() {
        System.out.println("Maximum cumulative probability: " + maxCumulativeProb);
        safestPathTest.stream()
                .forEach(node -> System.out.println(node));
        System.out.println("\n\n");
    }
}