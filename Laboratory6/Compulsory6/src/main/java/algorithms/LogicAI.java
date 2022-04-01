package algorithms;

import application.MainFrame;
import gamemodel.GameEdge;
import gamemodel.GameGraph;
import gamemodel.GameNode;
import gamemodel.PlayerName;
import lombok.Data;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** Class that implements the logic of an AI based on whether the Game Graph has a perfect matching and whether the AI is Player 1. */
@Data
public class LogicAI {
    private MainFrame mainFrame;
    private GameGraph<GameNode, GameEdge> gameGraph;
    private PlayerName aiPlayer;
    private Set<GameEdge> maxMatching = new HashSet<>();
    private Set<GameNode> maxMatchNodes = new HashSet<>();
    private boolean isStarting = false;

    public LogicAI(MainFrame mainFrame, PlayerName aiPlayer) {
        this.mainFrame = mainFrame;
        this.gameGraph = mainFrame.getGameGraph();
        this.aiPlayer = aiPlayer;
    }

    public void findMaxMatching() {
        maxMatching.clear();
        maxMatchNodes.clear();

        DenseEdmondsMaximumCardinalityMatchingAlgorithm algorithm = new DenseEdmondsMaximumCardinalityMatchingAlgorithm(gameGraph);
        algorithm.denseEdmondsMCMatchingRun();
        maxMatching = algorithm.getMaximumCardinalitySet();

        for (GameEdge edge : maxMatching) {
            maxMatchNodes.add(edge.getGameNode1());
            maxMatchNodes.add(edge.getGameNode2());
        }
    }

    /** use this logic in case the computer is not Player 1 or the Game Graph has a perfect matching */
    public GameNode notStartingOrPerfectMatchLogicChoice() {
        // Select the first node if the ai starts the game
        if (gameGraph.getPreviouslySelectedNode() == null) {
            Random random = new Random();
            int index = random.nextInt(0, maxMatching.size());
            int counter = 0;
            for (GameNode node : gameGraph.getGameNodeSet()) {
                if (counter == index) {

                    node.setUsed(true);
                    node.setPlayerName(aiPlayer);
                    gameGraph.setPreviouslySelectedNode(node);
                    gameGraph.setUsedInEdgesOf(node);

                    System.out.println(gameGraph.edgesOfNode(gameGraph.getPreviouslySelectedNode()).size());
                    return node;
                }
                counter++;
            }
        }

        // Keep selecting nodes based on the previous node
        GameNode previousNode = gameGraph.getPreviouslySelectedNode();
        GameNode bestChoiceNode = null;
        int minSetSize = 10; // 4 would be enough

        for (GameEdge edgeOfPrev : gameGraph.edgesOfNode(previousNode)) {
            GameNode nextNode = null;
            if (edgeOfPrev.getGameNode1().equals(previousNode)) {
                nextNode = edgeOfPrev.getGameNode2();
            }
            else if (edgeOfPrev.getGameNode2().equals(previousNode)) {
                nextNode = edgeOfPrev.getGameNode1();
            }

            if (nextNode == null)
                continue;

            if (gameGraph.edgesOfNode(nextNode).size() < minSetSize) {
                minSetSize = gameGraph.edgesOfNode(nextNode).size();
                bestChoiceNode = nextNode;
            }
        }

        if (bestChoiceNode == null)
            return null;

        bestChoiceNode.setUsed(true);
        bestChoiceNode.setPlayerName(aiPlayer);
        gameGraph.setUsedInEdgesOf(bestChoiceNode);
        gameGraph.setPreviouslySelectedNode(bestChoiceNode);
        return bestChoiceNode;
    }

    /** use this login in case the computer starts first and the Game Graph has a perfect matching */
    public GameNode startingAndPerfectMatchLogicChoice() {
        // Select the first node if the ai starts the game
        if (gameGraph.getPreviouslySelectedNode() == null) {
            Set<GameNode> remainingNodes = new HashSet<>(gameGraph.getGameNodeSet());
            remainingNodes.removeAll(maxMatchNodes);
            if (remainingNodes.isEmpty())
                return null;
            GameNode chosenNode = remainingNodes.iterator().next();
            chosenNode.setUsed(true);
            chosenNode.setPlayerName(aiPlayer);
            gameGraph.setPreviouslySelectedNode(chosenNode);
            gameGraph.setUsedInEdgesOf(chosenNode);
            return chosenNode;
        }


        // Keep selecting nodes based on the previous node and the perfect matching
        GameNode previousNode = gameGraph.getPreviouslySelectedNode();
        for (GameEdge gameEdge : maxMatching) {
            GameNode chosenNode = null;
            if (gameEdge.getGameNode1().equals(previousNode))
                chosenNode = gameEdge.getGameNode2();
            else if (gameEdge.getGameNode2().equals(previousNode))
                chosenNode = gameEdge.getGameNode1();

            if (chosenNode == null)
                continue;

            chosenNode.setUsed(true);
            chosenNode.setPlayerName(aiPlayer);
            gameGraph.setPreviouslySelectedNode(chosenNode);
            gameGraph.setUsedInEdgesOf(chosenNode);

            return chosenNode;
        }
        return null;
    }
}
