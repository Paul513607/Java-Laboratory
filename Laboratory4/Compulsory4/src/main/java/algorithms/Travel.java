package algorithms;

import city.*;

import java.util.ArrayList;
import java.util.List;

// A class to represent the way the car can go through all the intersections (attribute "travel") as well as to keep in mind the "previousTravel"
// This class generates random instances of the travel, and the SimulatedAnnealingTSP class takes care of calculating their distance (their fitness)
public class Travel {
    private List<Intersection> travel = new ArrayList<>();
    private List<Intersection> previousTravel = new ArrayList<>();

    public Travel() {

    }

    public Travel(CityGraph cityGraph) {
        for (Object objInter :cityGraph.vertexSet()) {
            Intersection intersection = (Intersection) objInter;
            travel.add(intersection);
        }
    }

    public List<Intersection> getTravel() {
        return travel;
    }

    public List<Intersection> getPreviousTravel() {
        return previousTravel;
    }

    public int generateRandomIndex() {
        return (int) (Math.random() * travel.size());
    }

    public Intersection getIntersection(int index) {
        return travel.get(index);
    }

    public void swapIntersections() {
        int index1 = generateRandomIndex();
        int index2 = generateRandomIndex();
        previousTravel = new ArrayList<>(travel);

        Intersection inter1 = travel.get(index1);
        Intersection inter2 = travel.get(index2);

        travel.set(index1, inter2);
        travel.set(index2, inter1);
    }

    public void revertSwap() {
        travel = previousTravel;
    }

    public double getDistance(CityGraph cityGraph) {
        double distance = 0;
        for (int index = 0; index < travel.size(); ++index) {
            Intersection startInter = getIntersection(index);
            Intersection destInter;

            if (index + 1 < travel.size()) {
                destInter = getIntersection(index + 1);
            } else {
                destInter = getIntersection(0);
            }
            distance += cityGraph.getLengthBetween(startInter, destInter);
        }
        return distance;
    }
}
