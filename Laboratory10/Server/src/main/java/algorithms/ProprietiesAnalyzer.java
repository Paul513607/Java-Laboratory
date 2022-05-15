package algorithms;

/** Returns all the cliques (groups of friends with each other) of the social network. */

import model.User;
import model.UserFriendship;
import model.UserGraph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/** The class that applies the BronKerboschCliqueFinder algorithm on a given CityRelations graph. */
public class ProprietiesAnalyzer {
    private UserGraph graph;
    private List<Set<User>> solution = new ArrayList<>();

    public ProprietiesAnalyzer(UserGraph graph) {
        this.graph = graph;
    }

    public void runAlgorithm() {
        BronKerboschCliqueFinder<User, UserFriendship> bronKerboschCliqueFinder =
                new BronKerboschCliqueFinder<>(graph, 60, TimeUnit.SECONDS);

        for (Set<User> currSet : bronKerboschCliqueFinder) {
            if (currSet.size() >= 2)
                solution.add(currSet);
        }
    }

    public String getSolution() {
        StringBuilder solutionBuilder = new StringBuilder();
        solution.forEach(item -> {
            solutionBuilder.append("Solution of size ").append(item.size()).append(": ");
            solutionBuilder.append(item.toString()).append("|");
        });
        return solutionBuilder.toString();
    }

    public void printSolution() {
        System.out.println("Solution:");
        solution.forEach(System.out::println);
        System.out.println("Solution size: " + solution.size());
    }
}
