package gamemodel;

import application.AlertBox;
import application.DrawingPanel;
import application.MainFrame;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import java.io.Serializable;
import java.util.*;
import java.util.function.Supplier;

/** Class for modeling the game (as a graph). */
@Data
@JsonIgnoreProperties({"type", "vertexSupplier", "edgeSupplier"})
public class GameGraph<V extends GameNode, E extends GameEdge> implements Serializable, Graph<V, E> {
    private int noRows;
    private int noColumns;
    private Set<GameNode> gameNodeSet = new HashSet<>();
    private Set<GameEdge> gameEdgeSet = new HashSet<>();
    private GameNode previouslySelectedNode = null;

    public GameGraph() {
        previouslySelectedNode = null;
    }

    public GameGraph(int noRows, int noColumns) {
        this.noRows = noRows;
        this.noColumns = noColumns;
    }

    public GameGraph(Set<GameNode> gameNodeSet, int noRows, int noColumns) {
        this.noRows = noRows;
        this.noColumns = noColumns;
        this.gameNodeSet = gameNodeSet;
    }

    public GameGraph(GameGraph<GameNode, GameEdge> gameGraph) {
        this.noRows = gameGraph.noRows;
        this.noColumns = gameGraph.noColumns;
        this.gameNodeSet = new HashSet<>(gameGraph.gameNodeSet);
        this.gameEdgeSet = new HashSet<>(gameGraph.gameEdgeSet);
        this.previouslySelectedNode = new GameNode(gameGraph.previouslySelectedNode);
    }

    public void addGameNode(GameNode node) {
        for (GameNode node1 : gameNodeSet)
            if (node1.equals(node))
                return;
        gameNodeSet.add(node);
    }

    public void addGameEdge(GameEdge gameEdge) {
        for (GameEdge gameEdge1 : gameEdgeSet)
            if (gameEdge1.equals(gameEdge))
                return;
        gameEdgeSet.add(gameEdge);
    }

    public void addGameEdge(GameNode gameNode1, GameNode gameNode2) {
        GameEdge gameEdge = new GameEdge(gameNode1, gameNode2);
        for (GameEdge gameEdge1 : gameEdgeSet)
            if (gameEdge1.equals(gameEdge))
                return;
        gameEdgeSet.add(gameEdge);
    }

    public void addGameEdge(double node1Xcoord, double node1Ycoord, double node2Xcoord, double node2Ycoord, double gridWidth, double gridHeigth) {
        GameNode node1 = getGameNoteAtCoords(node1Xcoord, node1Ycoord, gridWidth, gridHeigth);
        GameNode node2 = getGameNoteAtCoords(node2Xcoord, node2Ycoord, gridWidth, gridHeigth);
        if (node1 == null || node2 == null)
            return;
        GameEdge gameEdge = new GameEdge(node1, node2);
        for (GameEdge gameEdge1 : gameEdgeSet)
            if (gameEdge1.equals(gameEdge))
                return;
        gameEdgeSet.add(gameEdge);
    }

    public void obtainGameNodesFromEdges() {
        Set<GameNode> tempNodeSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet) {
            tempNodeSet.add(edge.getGameNode1());
            tempNodeSet.add(edge.getGameNode2());
        }
        gameNodeSet.clear();
        gameNodeSet = new HashSet<>(tempNodeSet);
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

    public GameNode getNodeById(String nodeId) {
        for (GameNode node : gameNodeSet)
            if (node.getId().equals(nodeId)) {
                return node;
            }
        return null;
    }

    public GameNode getGameNoteAtCoords(double xCoord, double yCoord, double gridWidth, double gridHeight) {
        xCoord = xCoord - (MainFrame.WINDOW_WIDTH - gridWidth) / 2.0;
        yCoord = yCoord - (MainFrame.WINDOW_HEIGHT - gridHeight) / 2.0;
        for (GameNode node : gameNodeSet) {
            if (Math.sqrt((xCoord - node.getXCoord() + DrawingPanel.CIRCLE_RADIUS) * (xCoord - node.getXCoord() + DrawingPanel.CIRCLE_RADIUS) +
                    (yCoord - node.getYCoord() + DrawingPanel.CIRCLE_RADIUS) * (yCoord - node.getYCoord() + DrawingPanel.CIRCLE_RADIUS)) <= DrawingPanel.CIRCLE_RADIUS) {
                return node;
            }
        }
        return null;
    }

    public GameNode getUnusedGameNodeAtCoords(double xCoord, double yCoord, PlayerName playerName, double gridWidth, double gridHeight) {
        xCoord = xCoord - (MainFrame.WINDOW_WIDTH - gridWidth) / 2.0;
        yCoord = yCoord - (MainFrame.WINDOW_HEIGHT - gridHeight) / 2.0;
        for (GameNode node : gameNodeSet) {
            if (Math.sqrt((xCoord - node.getXCoord() + DrawingPanel.CIRCLE_RADIUS) * (xCoord - node.getXCoord() + DrawingPanel.CIRCLE_RADIUS) +
                    (yCoord - node.getYCoord() + DrawingPanel.CIRCLE_RADIUS) * (yCoord - node.getYCoord() + DrawingPanel.CIRCLE_RADIUS)) <= DrawingPanel.CIRCLE_RADIUS) {
                if (node.isUsed()) {
                    AlertBox alertBox = new AlertBox();
                    alertBox.display("Node in use!", "This node is already used!");
                    System.out.println("Node in use!");
                    return null;
                }
                if (!isNodeLegalInEdge(node)) {
                    AlertBox alertBox = new AlertBox();
                    alertBox.display("Illegal move!", "You need to place a stone adjacent\nto where the previous one was\nplaced!");
                    System.out.println("Illegal move!");
                    return null;
                }
                node.setUsed(true);
                node.setPlayerName(playerName);
                setUsedInEdgesOf(node);
                return node;
            }
        }
        return null;
    }

    public Set<GameEdge> edgesOfNode(GameNode node) {
        Set<GameEdge> tempEdgeSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet) {
            if (edge.getGameNode1().equals(node) && !edge.getGameNode2().isUsed())
                tempEdgeSet.add(edge);
            else if (edge.getGameNode2().equals(node) && !edge.getGameNode1().isUsed())
                tempEdgeSet.add(edge);
        }
        return tempEdgeSet;
    }

    public void setUsedInEdgesOf(GameNode node) {
        for (GameEdge edge : gameEdgeSet) {
            if (edge.getGameNode1().equals(node)) {
                edge.setGameNode1Data(node.isUsed(), node.getPlayerName());
            } else if (edge.getGameNode2().equals(node)) {
                edge.setGameNode2Data(node.isUsed(), node.getPlayerName());
            }
        }
    }

    public boolean isNodeLegalInEdge(GameNode newNode) {
        if (previouslySelectedNode == null) {
            previouslySelectedNode = newNode;
            return true;
        }

        Set<GameEdge> prevEdgeSet = edgesOfNode(previouslySelectedNode);
        for (GameEdge edge : prevEdgeSet) {
            if (edge.containsNode(newNode)) {
                previouslySelectedNode = newNode;
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<E> getAllEdges(V v, V v1) {
        Set<E> tempEdgeSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v) && edge.containsNode(v1))
                tempEdgeSet.add((E) edge);
        return tempEdgeSet;
    }

    @Override
    public E getEdge(V v, V v1) {
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v) && edge.containsNode(v1))
                return (E) edge;
        return null;
    }

    @Override
    public Supplier<V> getVertexSupplier() {
        return null;
    }

    @Override
    public Supplier<E> getEdgeSupplier() {
        return null;
    }

    @Override
    public E addEdge(V v, V v1) {
        if (!gameNodeSet.contains(v) || !gameNodeSet.contains(v1))
            return null;
        E gameEdge = (E) new GameEdge(v, v1);
        gameEdgeSet.add(gameEdge);
        return gameEdge;
    }

    @Override
    public boolean addEdge(V v, V v1, E e) {
        if (!gameNodeSet.contains(v) || !gameNodeSet.contains(v1))
            return false;
        e.setGameNode1(v);
        e.setGameNode2(v1);
        gameEdgeSet.add(e);
        return true;
    }

    @Override
    public V addVertex() {
        return null;
    }

    @Override
    public boolean addVertex(V v) {
        double gridXStart = (MainFrame.WINDOW_WIDTH - DrawingPanel.RECTANGLE_SIZE * noRows) / 2.0;
        double gridYStart = (MainFrame.WINDOW_HEIGHT - DrawingPanel.RECTANGLE_SIZE * noColumns) / 2.0;
        double xCoordGlobalCurr = v.getXCoord() + gridXStart - DrawingPanel.CIRCLE_RADIUS * 1.5;
        double yCoordGlobalCurr = v.getYCoord()+ gridYStart - DrawingPanel.CIRCLE_RADIUS * 1.5;

        if (xCoordGlobalCurr >= gridXStart && xCoordGlobalCurr <= gridXStart + DrawingPanel.RECTANGLE_SIZE * noRows / 2.0 &&
                yCoordGlobalCurr >= gridYStart && yCoordGlobalCurr <= gridYStart + DrawingPanel.RECTANGLE_SIZE * noRows / 2.0) {
            gameNodeSet.add(v);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsEdge(V v, V v1) {
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v) && edge.containsNode(v1))
                return true;
        return false;
    }

    @Override
    public boolean containsEdge(E e) {
        for (GameEdge edge : gameEdgeSet)
            if (edge.equals(e))
                return true;
        return false;
    }

    @Override
    public boolean containsVertex(V v) {
        for (GameNode node : gameNodeSet)
            if (node.equals(v))
                return true;
        return false;
    }

    @Override
    public Set<E> edgeSet() {
        return (Set<E>) gameEdgeSet;
    }

    @Override
    public int degreeOf(V v) {
        return 0;
    }

    @Override
    public Set<E> edgesOf(V v) {
        Set<E> tempEdgeSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v))
                tempEdgeSet.add((E) edge);
        return tempEdgeSet;
    }

    @Override
    public int inDegreeOf(V v) {
        return 0;
    }

    @Override
    public Set<E> incomingEdgesOf(V v) {
        Set<E> tempEdgeSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v))
                tempEdgeSet.add((E) edge);
        return tempEdgeSet;
    }

    @Override
    public int outDegreeOf(V v) {
        return 0;
    }

    @Override
    public Set<E> outgoingEdgesOf(V v) {
        Set<E> tempEdgeSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v))
                tempEdgeSet.add((E) edge);
        return tempEdgeSet;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> collection) {
        if (gameEdgeSet.containsAll(collection)) {
            gameEdgeSet.removeAll(collection);
            return true;
        }
        return false;
    }

    @Override
    public Set<E> removeAllEdges(V v, V v1) {
        Set<E> toRemoveEdgeSet = new HashSet<>();
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v) && edge.containsNode(v1))
                toRemoveEdgeSet.add((E) edge);
        gameEdgeSet.removeAll(toRemoveEdgeSet);
        return toRemoveEdgeSet;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> collection) {
        if (gameNodeSet.containsAll(collection)) {
            gameNodeSet.removeAll(collection);
            return true;
        }
        return false;
    }

    @Override
    public E removeEdge(V v, V v1) {
        E toRemoveEdge = null;
        for (GameEdge edge : gameEdgeSet)
            if (edge.containsNode(v) && edge.containsNode(v1)) {
                toRemoveEdge = (E) edge;
                break;
            }
        if (toRemoveEdge != null)
            gameEdgeSet.remove(toRemoveEdge);
        return toRemoveEdge;
    }

    @Override
    public boolean removeEdge(E e) {
        if (gameEdgeSet.contains(e)) {
            gameEdgeSet.remove(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeVertex(V v) {
        if (gameNodeSet.contains(v)) {
            gameNodeSet.remove(v);
            return true;
        }
        return false;
    }

    @Override
    public Set<V> vertexSet() {
        return (Set<V>) gameNodeSet;
    }

    @Override
    public V getEdgeSource(E e) {
        return (V) e.getGameNode1();
    }

    @Override
    public V getEdgeTarget(E e) {
        return (V) e.getGameNode2();
    }

    @Override
    public GraphType getType() {
        return new GraphType() {
            @Override
            public boolean isDirected() {
                return false;
            }

            @Override
            public boolean isUndirected() {
                return true;
            }

            @Override
            public boolean isMixed() {
                return false;
            }

            @Override
            public boolean isAllowingMultipleEdges() {
                return false;
            }

            @Override
            public boolean isAllowingSelfLoops() {
                return false;
            }

            @Override
            public boolean isAllowingCycles() {
                return true;
            }

            @Override
            public boolean isWeighted() {
                return false;
            }

            @Override
            public boolean isSimple() {
                return true;
            }

            @Override
            public boolean isPseudograph() {
                return false;
            }

            @Override
            public boolean isMultigraph() {
                return false;
            }

            @Override
            public boolean isModifiable() {
                return false;
            }

            @Override
            public GraphType asDirected() {
                return null;
            }

            @Override
            public GraphType asUndirected() {
                return null;
            }

            @Override
            public GraphType asMixed() {
                return null;
            }

            @Override
            public GraphType asUnweighted() {
                return null;
            }

            @Override
            public GraphType asWeighted() {
                return null;
            }

            @Override
            public GraphType asModifiable() {
                return null;
            }

            @Override
            public GraphType asUnmodifiable() {
                return null;
            }
        };
    }

    @Override
    public double getEdgeWeight(E e) {
        return 0;
    }

    @Override
    public void setEdgeWeight(E e, double v) {}
}
