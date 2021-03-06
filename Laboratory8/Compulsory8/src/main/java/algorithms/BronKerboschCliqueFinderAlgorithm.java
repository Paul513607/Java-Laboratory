package algorithms;

import model.City;
import model.CityRelationsGraph;
import model.CitySisterRelation;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/** The class that applies the BronKerboschCliqueFinder algorithm on a given CityRelations graph. */
public class BronKerboschCliqueFinderAlgorithm {
    CityRelationsGraph<City, CitySisterRelation> graph;
    List<Set<City>> solution = new ArrayList<>();

    public BronKerboschCliqueFinderAlgorithm(CityRelationsGraph<City, CitySisterRelation> graph) {
        this.graph = graph;
    }

    public void runAlgorithm() {
        BronKerboschCliqueFinder<City, CitySisterRelation> bronKerboschCliqueFinder =
                new BronKerboschCliqueFinder<>(graph, 60, TimeUnit.SECONDS);

        for (Set<City> currSet : bronKerboschCliqueFinder) {
            if (currSet.size() >= 3)
                solution.add(currSet);
        }
    }

    public void printSolution() {
        System.out.println("Solution:");
        solution.forEach(System.out::println);
        System.out.println("Solution size: " + solution.size());
    }
}
