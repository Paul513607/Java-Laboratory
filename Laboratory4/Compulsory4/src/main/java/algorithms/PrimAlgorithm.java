package algorithms;

import city.*;

import java.util.*;

// A class which applies Prim's MST Algorithm on a given graph (given as instance in the constructor)
// The solution we get is a MST of the graph (city)
// We can use this algorithm on the given problem because we know the graph (city) does not have negative cost edges
public class PrimAlgorithm {
    CityGraph<Intersection, Street> cityGraph;
    Set<Street> solutionStreets;
    double mstTotalCost;

    public PrimAlgorithm(CityGraph<Intersection, Street> cityGraph) {
        this.cityGraph = cityGraph;
        this.solutionStreets = new HashSet<>();
        mstTotalCost = 0;
    }

    public CityGraph<Intersection, Street> getCityGraph() {
        return cityGraph;
    }

    public Set<Street> getSolutionStreets() {
        return solutionStreets;
    }

    public double getMstTotalCost() {
        return mstTotalCost;
    }

    // returns the minimum weighted edge from the nodes that are currently in the MST
    // (that have the source/target visited and the target/source not visited respectively)
    private Street minimumEdge(Map<Intersection, Boolean> visitedInter) {
        double minCurrent = Double.MAX_VALUE;
        Street minCurrStreet = new Street();

        for (Intersection intersection : visitedInter.keySet()) {
            if (visitedInter.get(intersection)) {
                for (Object objStreet : cityGraph.edgesOf(intersection)) {
                    Street street = (Street) objStreet;
                    Intersection interSource = (Intersection) cityGraph.getEdgeSource(street);
                    Intersection interTarget = (Intersection) cityGraph.getEdgeTarget(street);

                    if (interSource.equals(intersection) && !visitedInter.get(interTarget) ||
                            interTarget.equals(intersection) && !visitedInter.get(interSource)) {
                        if (street.getLength() < minCurrent) {
                            minCurrent = street.getLength();
                            minCurrStreet = street;
                        }
                    }
                }
            }
        }
        return minCurrStreet;
    }

    // find the MST for the CityGraph given in the constructor starting from startNode
    public void primMSTAlgorithm(Intersection startNode) {
        Map<Intersection, Boolean> visitedInter = new HashMap<>();
        cityGraph.vertexSet().stream()
                .forEach(intersection -> visitedInter.put((Intersection) intersection, false));

        if (!cityGraph.vertexSet().contains(startNode))
            return;

        visitedInter.put(startNode, true);
        // We repeat the process cityGraph.vertexSet().size() times, in order to visit all nodes and select the optimal edges
        for (int count = 1; count < cityGraph.vertexSet().size(); ++count) {
            Street minStreet = minimumEdge(visitedInter);

            Intersection interSource = (Intersection) cityGraph.getEdgeSource(minStreet);
            Intersection interTarget = (Intersection) cityGraph.getEdgeTarget(minStreet);

            // We visit the unvisited node from the currently found minimal length street
            if (visitedInter.get(interSource) && !visitedInter.get(interTarget))
                visitedInter.put(interTarget, true);
            else if (!visitedInter.get(interSource) && visitedInter.get(interTarget))
                    visitedInter.put(interSource, true);

            // Add the found street, and it's length to the solution
            solutionStreets.add(minStreet);
            mstTotalCost += minStreet.getLength();
        }
    }

    public void printSolution() {
        System.out.println("The optimal streets to put data cables on (obtained with Prim's MST Algorithm) are (with a total cost of: " + mstTotalCost + "):");
        solutionStreets.stream()
                .forEach(street -> System.out.println(street));
    }
}
