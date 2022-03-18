package algorithms;

import city.*;

import java.util.Map;
import java.util.Set;

// We apply the Simulated Annealing Algorithm for trying to optimize (minimize) the distance in the TSP problem
// We consider that, if in the cityGraph two intersections do not have a street between them, that path will be of length maxLengthOfStreets + 1.0
public class SimulatedAnnealingTSP {
    private CityGraph cityGraph;
    private Travel travel;
    private Travel bestSolutionTotal;
    private double bestDistanceTotal;

    public SimulatedAnnealingTSP(CityGraph cityGraph) {
        this.cityGraph = cityGraph;
        this.travel = new Travel(cityGraph);
        this.bestSolutionTotal = new Travel();
        this.bestDistanceTotal = Double.MAX_VALUE;
    }

    // The algorithm takes as parameters a numberOfIterations for the algorithm, a "startingTemperature" and it's coolingRate
    // The temperature progressively decreases from an initial positive value to zero. At each time step, the algorithm randomly selects a solution close
    // to the current one, measures its quality and moves to it according to the temperature-dependent probabilities of selecting better or worse solutions
    public double simulatedAnnealing(int numberOfIterations, double startingTemperature, double coolingRate) {
        System.out.println("Starting SA with numberOfIterations: " + numberOfIterations + ", startingTemperature: " + startingTemperature + ", coolingRate: " + coolingRate);
        double bestDistance = travel.getDistance(cityGraph);
        System.out.println("Initial best distance of travel: " + bestDistance);
        Travel bestSolution = travel;
        Travel currentSolution = bestSolution;

        for (int iteration = 0; iteration < numberOfIterations; ++iteration) {
            if (startingTemperature > 0.1) {
                // for the current solution we swap two intersection to get a new travel and test it
                currentSolution.swapIntersections();
                double currentBestDistance = currentSolution.getDistance(cityGraph);
                if (currentBestDistance < bestDistance) {
                    bestSolution = currentSolution;
                    bestDistance = currentBestDistance;
                    // calculate the acceptance probability function, and check if that value if very small, we revert the swap
                } else if (Math.exp((bestDistance - currentBestDistance) / startingTemperature) < Math.random()) {
                    currentSolution.revertSwap();
                }
                startingTemperature *= coolingRate;
            }
            if (iteration % 100 == 0) {
                System.out.println("Iteration number: " + iteration);
            }
        }
        bestSolutionTotal = bestSolution;
        bestDistanceTotal = bestDistance;
        return bestDistance;
    }

    public void printSolution() {
        System.out.println("Best path for the car to take is (with a total length of: " + bestDistanceTotal + "):");
        bestSolutionTotal.getTravel().stream()
                .forEach(intersection -> System.out.println("\t" + intersection));
    }
}
