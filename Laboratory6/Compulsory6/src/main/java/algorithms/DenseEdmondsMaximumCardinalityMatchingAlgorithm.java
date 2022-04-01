package algorithms;

import gamemodel.GameEdge;
import gamemodel.GameGraph;
import gamemodel.GameNode;
import lombok.Data;
import org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching;

import java.util.HashSet;
import java.util.Set;

/** Class that applies Edmonds Maximum Cardinality Matching Algorithm on a given Game Graph
 *  and returns the Maximum Cardinality Set And whether the Graph has a perfect matching. */
@Data
public class DenseEdmondsMaximumCardinalityMatchingAlgorithm {
    private GameGraph<GameNode, GameEdge> gameGraph;
    private Set<GameEdge> maximumCardinalitySet = new HashSet<>();
    private boolean isPerfectMatching;

    public DenseEdmondsMaximumCardinalityMatchingAlgorithm (GameGraph<GameNode, GameEdge> gameGraph) {
        this.gameGraph = gameGraph;
        isPerfectMatching = false;
    }

    public void denseEdmondsMCMatchingRun() {
        DenseEdmondsMaximumCardinalityMatching<GameNode, GameEdge> denseEdmondsMaximumCardinalityMatching = new DenseEdmondsMaximumCardinalityMatching<>(gameGraph);
        maximumCardinalitySet = denseEdmondsMaximumCardinalityMatching.getMatching().getEdges();
        isPerfectMatching = denseEdmondsMaximumCardinalityMatching.getMatching().isPerfect();
    }
}
