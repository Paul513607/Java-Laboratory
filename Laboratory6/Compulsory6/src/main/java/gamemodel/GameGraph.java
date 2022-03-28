package gamemodel;

import application.DrawingPanel;
import application.MainFrame;

import java.util.HashSet;
import java.util.Set;

public class GameGraph {
    Set<GameNode> gameNodeSet = new HashSet<>();
    // TODO links set

    public GameGraph() {
    }

    public GameGraph(Set<GameNode> gameNodeSet) {
        this.gameNodeSet = gameNodeSet;
    }

    public Set<GameNode> getGameNodeSet() {
        return gameNodeSet;
    }

    public void setGameNodeSet(Set<GameNode> gameNodeSet) {
        this.gameNodeSet = gameNodeSet;
    }

    @Override
    public String toString() {
        return "GameGraph{" +
                "gameNodeSet=" + gameNodeSet +
                '}';
    }

    public void addGameNode(GameNode node) {
        gameNodeSet.add(node);
    }

    public boolean removeNodeById(String nodeId) {
        boolean remove = false;
        GameNode nodeToRemove = new GameNode();
        for (GameNode node : gameNodeSet)
            if (node.getId().equals(nodeId)) {
                nodeToRemove = node;
                remove = true;
                break;
            }
        if (remove)
            gameNodeSet.remove(nodeToRemove);
        return remove;
    }

    public GameNode getGameNodeAtCoords(double xCoord, double yCoord, PlayerName playerName, double gridWidth, double gridHeight) {
        xCoord = xCoord - (MainFrame.WINDOW_WIDTH - gridWidth) / 2.0;
        yCoord = yCoord - (MainFrame.WINDOW_HEIGHT - gridHeight) / 2.0;
        GameNode newNode = null;
        for (GameNode node : gameNodeSet) {
            if (Math.sqrt((xCoord - node.getxCoord() + DrawingPanel.CIRCLE_RADIUS) * (xCoord - node.getxCoord() + DrawingPanel.CIRCLE_RADIUS) +
                    (yCoord - node.getyCoord() + DrawingPanel.CIRCLE_RADIUS) * (yCoord - node.getyCoord() + DrawingPanel.CIRCLE_RADIUS)) <= DrawingPanel.CIRCLE_RADIUS) {
                if (node.isUsed())
                    return null;
                newNode = new GameNode(node.getId(), true, playerName, node.getxCoord(), node.getyCoord());
            }
        }
        if (newNode != null) {
            removeNodeById(newNode.getId());
            gameNodeSet.add(newNode);
        }
        return newNode;
    }
}
