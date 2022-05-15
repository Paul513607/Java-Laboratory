package algorithms;

import model.User;
import model.UserFriendship;
import model.UserGraph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/** Class for calculating the maximum flow between two users. */
public class MaximumFlowAlgorithm {
    private UserGraph graph;
    private User source;
    private User dest;

    private double maximumFlow;

    public MaximumFlowAlgorithm(UserGraph graph, User source, User dest) {
        this.graph = graph;
        this.source = source;
        this.dest = dest;
    }

    public void runAlgorithm() {
        EdmondsKarpMFImpl<User, UserFriendship> edmondsKarpMF =
                new EdmondsKarpMFImpl<>(graph);

        maximumFlow = edmondsKarpMF.calculateMaximumFlow(source, dest);
    }

    public void printSolution() {
        System.out.println("Maximum from from " + source.getName() + " to " + dest.getName() + " is: " + maximumFlow);
    }
}
