package application;


import gamemodel.GameEdge;
import gamemodel.GameGraph;
import gamemodel.GameNode;
import gamemodel.PlayerName;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** Class for drawing all the shapes of the grid in a direct way (we draw then on a Canvas and load the Canvas) and for recoloring based on user input. */
@Data
public class DrawingPanel {
    public static final double RECTANGLE_SIZE = 50;
    public static final double CIRCLE_RADIUS = 20;

    private double gridWidth;
    private double gridHeight;

    private final MainFrame mainFrame;
    private Canvas canvas;
    private StackPane canvasLayout;
    private int noRows = 5;
    private int noColumns = 5;

    public DrawingPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setArgs();
        gridWidth = RECTANGLE_SIZE * mainFrame.getConfigPanel().getNoRows();
        gridHeight = RECTANGLE_SIZE * mainFrame.getConfigPanel().getNoCols();
        display();
    }

    public DrawingPanel(MainFrame mainFrame, int noRows, int noColumns) {
        this.mainFrame = mainFrame;
        this.noRows = noRows;
        this.noColumns = noColumns;
        gridWidth = RECTANGLE_SIZE * noRows;
        gridHeight = RECTANGLE_SIZE * noColumns;
        display();
    }

    public void setArgs() {
        noRows = mainFrame.getConfigPanel().getNoRows();
        noColumns = mainFrame.getConfigPanel().getNoCols();
    }

    private HBox drawPlayerText(PlayerName playerName, String playerText) {
        HBox playerAttributes = new HBox();
        playerAttributes.paddingProperty().setValue(new Insets(10, 10, 10, 10));
        playerAttributes.spacingProperty().setValue(10);
        playerAttributes.setAlignment(Pos.TOP_CENTER);

        Circle playerColor = new Circle(CIRCLE_RADIUS / 2.0);
        Label playerLabel = new Label();
        if (playerName == PlayerName.PLAYER_ONE) {
            playerColor.setFill(Color.BLUE);
            playerLabel.setText(playerText);
        }
        else if (playerName == PlayerName.PLAYER_TWO) {
            playerColor.setFill(Color.RED);
            playerLabel.setText(playerText);
        }

        playerAttributes.getChildren().addAll(playerColor, playerLabel);
        return playerAttributes;
    }

    public void display() {
        Group root = new Group();
        canvas = new Canvas(gridWidth + 2 * CIRCLE_RADIUS, gridHeight + 2 * CIRCLE_RADIUS);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        String playerText = "Player 1's turn";
        if (mainFrame.isAiEnabled() && PlayerName.PLAYER_ONE == mainFrame.getGameAI().getAiPlayer())
            playerText = "AI's turn";

        HBox playerAttributes = drawPlayerText(PlayerName.PLAYER_ONE, playerText);

        root.getChildren().addAll(canvas);

        canvasLayout = new StackPane();
        StackPane.setAlignment(playerAttributes, Pos.TOP_CENTER);
        canvasLayout.getChildren().add(playerAttributes);
        canvasLayout.setAlignment(Pos.CENTER);
        canvasLayout.getChildren().add(root);
    }

    private void drawShapes(GraphicsContext gc) {
        double xCoordGridStart = CIRCLE_RADIUS;
        double yCoordGridStart = CIRCLE_RADIUS;

        double gridWidthXCoord = xCoordGridStart + gridWidth;
        double gridHeightYCoord = yCoordGridStart + gridHeight;

        // Build the canvas shapes
        createRectangles(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord);
        createCircles(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord);
        createLineStrokes(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord);
        mainFrame.getGameGraph().obtainGameNodesFromEdges();
    }

    private void createRectangles(GraphicsContext gc, double xCoordGridStart, double yCoordGridStart, double gridX, double gridY) {
        double xCoordGridCurr = xCoordGridStart;
        double yCoordGridCurr = yCoordGridStart;
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                gc.fillRect(xCoordGridCurr, yCoordGridCurr, RECTANGLE_SIZE, RECTANGLE_SIZE);
                gc.strokeRect(xCoordGridCurr, yCoordGridCurr, RECTANGLE_SIZE, RECTANGLE_SIZE);

                xCoordGridCurr += RECTANGLE_SIZE;
                if (xCoordGridCurr >= gridX) {
                    xCoordGridCurr = xCoordGridStart;
                    yCoordGridCurr += RECTANGLE_SIZE;
                }
            }
        }
    }

    private void createLineStrokes(GraphicsContext gc, double xCoordGridStart, double yCoordGridStart, double gridX, double gridY) {
        double xCoordGridCurr = xCoordGridStart;
        double yCoordGridCurr = yCoordGridStart;
        gc.setStroke(Color.BLACK);

        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                strokeRectangle(gc, xCoordGridCurr, yCoordGridCurr);

                xCoordGridCurr += RECTANGLE_SIZE;
                if (xCoordGridCurr >= gridX) {
                    xCoordGridCurr = xCoordGridStart;
                    yCoordGridCurr += RECTANGLE_SIZE;
                }
            }
        }
    }

    private void createCircles(GraphicsContext gc, double xCoordGridStart, double yCoordGridStart, double gridX, double gridY) {
        int circleId = 0;
        double xCoordGridCurr = xCoordGridStart;
        double yCoordGridCurr = yCoordGridStart;
        double xCoordGlobalCurr = xCoordGridCurr + (MainFrame.WINDOW_WIDTH - gridWidth) / 2.0;
        double yCoordGlobalCurr = yCoordGridCurr + (MainFrame.WINDOW_HEIGHT - gridHeight) / 2.0;

        gc.setStroke(Color.GREY);

        String gameNodeId;
        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                if (mainFrame.getGameGraph().getGameNoteAtCoords(xCoordGlobalCurr, yCoordGlobalCurr, gridWidth, gridHeight) == null) {
                    gameNodeId = "c" + circleId;
                    circleId++;
                    mainFrame.getGameGraph().addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0));
                }

                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                if (mainFrame.getGameGraph().getGameNoteAtCoords(xCoordGlobalCurr + RECTANGLE_SIZE, yCoordGlobalCurr, gridWidth, gridHeight) == null) {
                    gameNodeId = "c" + circleId;
                    circleId++;
                    mainFrame.getGameGraph().addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0));
                }

                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                if (mainFrame.getGameGraph().getGameNoteAtCoords(xCoordGlobalCurr + RECTANGLE_SIZE, yCoordGlobalCurr + RECTANGLE_SIZE, gridWidth, gridHeight) == null) {
                    gameNodeId = "c" + circleId;
                    circleId++;
                    mainFrame.getGameGraph().addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE));
                }

                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                if (mainFrame.getGameGraph().getGameNoteAtCoords(xCoordGlobalCurr, yCoordGlobalCurr + RECTANGLE_SIZE, gridWidth, gridHeight) == null) {
                    gameNodeId = "c" + circleId;
                    circleId++;
                    mainFrame.getGameGraph().addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE));
                }

                xCoordGridCurr += RECTANGLE_SIZE;
                if (xCoordGridCurr >= gridX) {
                    xCoordGridCurr = xCoordGridStart;
                    yCoordGridCurr += RECTANGLE_SIZE;
                }
            }
        }
    }

    private void strokeRectangle(GraphicsContext gc, double xCoordGridCurr, double yCoordGridCurr) {
        Random random = new Random();
        List<Boolean> chanceBool = new ArrayList<>(4);

        for (int chanceIndex = 0; chanceIndex < 4; ++chanceIndex){
            chanceBool.add(random.nextBoolean());
        }

        double xCoordGlobalCurr = xCoordGridCurr + (MainFrame.WINDOW_WIDTH - gridWidth) / 2.0 - CIRCLE_RADIUS * 1.5;
        double yCoordGlobalCurr = yCoordGridCurr + (MainFrame.WINDOW_HEIGHT - gridHeight) / 2.0 - CIRCLE_RADIUS * 1.5;

        gc.setLineWidth(8);

        if (chanceBool.get(0)) {
            gc.strokeLine(xCoordGridCurr + CIRCLE_RADIUS / 2.0 + 4, yCoordGridCurr, xCoordGridCurr + RECTANGLE_SIZE - CIRCLE_RADIUS / 2.0 - 4, yCoordGridCurr);
            mainFrame.getGameGraph().addGameEdge(xCoordGlobalCurr, yCoordGlobalCurr, xCoordGlobalCurr + RECTANGLE_SIZE, yCoordGlobalCurr, gridWidth, gridHeight);
        }
        if (chanceBool.get(1)) {
            gc.strokeLine(xCoordGridCurr + RECTANGLE_SIZE, yCoordGridCurr + CIRCLE_RADIUS / 2.0 + 4, xCoordGridCurr + RECTANGLE_SIZE, yCoordGridCurr + RECTANGLE_SIZE - CIRCLE_RADIUS / 2.0 - 4);
            mainFrame.getGameGraph().addGameEdge(xCoordGlobalCurr + RECTANGLE_SIZE, yCoordGlobalCurr, xCoordGlobalCurr + RECTANGLE_SIZE, yCoordGlobalCurr + RECTANGLE_SIZE, gridWidth, gridHeight);
        }
        if (chanceBool.get(2)) {
            gc.strokeLine(xCoordGridCurr + RECTANGLE_SIZE - CIRCLE_RADIUS / 2.0 - 4, yCoordGridCurr + RECTANGLE_SIZE, xCoordGridCurr + CIRCLE_RADIUS / 2.0 + 4, yCoordGridCurr + RECTANGLE_SIZE);
            mainFrame.getGameGraph().addGameEdge(xCoordGlobalCurr + RECTANGLE_SIZE, yCoordGlobalCurr + RECTANGLE_SIZE, xCoordGlobalCurr, yCoordGlobalCurr + RECTANGLE_SIZE, gridWidth, gridHeight);
        }
        if (chanceBool.get(3)) {
            gc.strokeLine(xCoordGridCurr, yCoordGridCurr + RECTANGLE_SIZE - CIRCLE_RADIUS / 2.0 - 4, xCoordGridCurr, yCoordGridCurr + CIRCLE_RADIUS / 2.0 + 4);
            mainFrame.getGameGraph().addGameEdge(xCoordGlobalCurr, yCoordGlobalCurr + RECTANGLE_SIZE, xCoordGlobalCurr, yCoordGlobalCurr, gridWidth, gridHeight);
        }

        gc.setLineWidth(2);
    }

    @Deprecated
    public void display2() {
        Group groupLayout = new Group();

        double xCoordGridStart = MainFrame.WINDOW_HEIGHT / 2 - gridHeight / 2;
        double yCoordGridStart = MainFrame.WINDOW_WIDTH / 2 - gridWidth / 2;
        double xCoordGridCurr = xCoordGridStart;
        double yCoordGridCurr = yCoordGridStart;

        double gridWidthXCoord = xCoordGridStart + gridWidth;
        double gridHeightYCoord = yCoordGridStart + gridHeight;

        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                Rectangle rectangle = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE);
                rectangle.setX(xCoordGridCurr);
                rectangle.setY(yCoordGridCurr);
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.GREY);

                Circle circle1 = new Circle();
                circle1.setCenterX(xCoordGridCurr);
                circle1.setCenterY(yCoordGridCurr);
                circle1.setRadius(CIRCLE_RADIUS);
                circle1.setFill(Color.WHITE);
                circle1.setStroke(Color.LIGHTGREY);

                Circle circle2 = new Circle();
                circle2.setCenterX(xCoordGridCurr + rectangle.getWidth());
                circle2.setCenterY(yCoordGridCurr);
                circle2.setRadius(CIRCLE_RADIUS);
                circle2.setFill(Color.WHITE);
                circle2.setStroke(Color.LIGHTGREY);

                Circle circle3 = new Circle();
                circle3.setCenterX(xCoordGridCurr + rectangle.getWidth());
                circle3.setCenterY(yCoordGridCurr + rectangle.getHeight());
                circle3.setRadius(CIRCLE_RADIUS);
                circle3.setFill(Color.WHITE);
                circle3.setStroke(Color.LIGHTGREY);

                Circle circle4 = new Circle();
                circle4.setCenterX(xCoordGridCurr);
                circle4.setCenterY(yCoordGridCurr + rectangle.getHeight());
                circle4.setRadius(CIRCLE_RADIUS);
                circle4.setFill(Color.WHITE);
                circle4.setStroke(Color.LIGHTGREY);

                Group shapes = new Group(rectangle, circle1, circle2, circle3, circle4);
                GridPane.setConstraints(shapes, rowNum, colNum);
                groupLayout.getChildren().add(shapes);

                xCoordGridCurr += rectangle.getWidth();
                if (xCoordGridCurr >= gridWidthXCoord) {
                    xCoordGridCurr = xCoordGridStart;
                    yCoordGridCurr += RECTANGLE_SIZE;
                }
            }
        }

        canvasLayout = new StackPane();
        canvasLayout.setAlignment(Pos.CENTER);
        canvasLayout.getChildren().add(groupLayout);
    }

    public void recolor(GameNode node, PlayerName playerName, boolean isWin) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (playerName == PlayerName.PLAYER_ONE)
            gc.setFill(Color.BLUE);
        else if (playerName == PlayerName.PLAYER_TWO)
            gc.setFill(Color.RED);
        double xCoord = node.getXCoord();
        double yCoord = node.getYCoord();

        gc.fillOval(xCoord, yCoord, CIRCLE_RADIUS, CIRCLE_RADIUS);

        gc.setFill(Color.BLACK);

        String playerText = "";
        if (isWin) {
            switch (playerName) {
                case PLAYER_ONE -> playerText = "Player 1 wins!";
                case PLAYER_TWO -> playerText = "Player 2 wins!";
            }

            if (mainFrame.isAiEnabled() && playerName == mainFrame.getGameAI().getAiPlayer())
                playerText = "AI wins!";
        }
        else {
            playerName = swapPlayer(playerName);
            switch (playerName) {
                case PLAYER_ONE -> playerText = "Player 1's turn";
                case PLAYER_TWO -> playerText = "Player 2's turn";
            }

            if (mainFrame.isAiEnabled() && playerName == mainFrame.getGameAI().getAiPlayer())
                playerText = "AI's turn";
        }

        HBox playerAttributes = drawPlayerText(playerName, playerText);
        canvasLayout.getChildren().set(0, playerAttributes);
    }

    public PlayerName swapPlayer(PlayerName playerName) {
        if (playerName == PlayerName.PLAYER_ONE)
            playerName = PlayerName.PLAYER_TWO;
        else
            playerName = PlayerName.PLAYER_ONE;
        return playerName;
    }

    public void drawFromGameGraph(GameGraph<GameNode, GameEdge> inputGameGraph) {
        noRows = inputGameGraph.getNoRows();
        noColumns = inputGameGraph.getNoColumns();

        gridWidth = RECTANGLE_SIZE * noRows;
        gridHeight = RECTANGLE_SIZE * noColumns;

        Group root = new Group();
        canvas = new Canvas(gridWidth + 2 * CIRCLE_RADIUS, gridHeight + 2 * CIRCLE_RADIUS);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapesFromGraph(gc, inputGameGraph);

        PlayerName nextPlayer = swapPlayer(inputGameGraph.getPreviouslySelectedNode().getPlayerName());


        String playerText = "";
        switch (nextPlayer) {
            case PLAYER_ONE -> playerText = "Player 1's turn";
            case PLAYER_TWO -> playerText = "Player 2's turn";
        }

        if (mainFrame.isAiEnabled() && nextPlayer == mainFrame.getGameAI().getAiPlayer())
            playerText = "AI's turn";

        HBox playerAttributes = drawPlayerText(nextPlayer, playerText);
        root.getChildren().addAll(canvas);

        canvasLayout = new StackPane();
        StackPane.setAlignment(playerAttributes, Pos.TOP_CENTER);
        canvasLayout.getChildren().add(playerAttributes);
        canvasLayout.setAlignment(Pos.CENTER);
        canvasLayout.getChildren().add(root);
    }

    private void drawShapesFromGraph(GraphicsContext gc, GameGraph<GameNode, GameEdge> inputGameGraph) {
        double xCoordGridStart = CIRCLE_RADIUS;
        double yCoordGridStart = CIRCLE_RADIUS;

        double gridWidthXCoord = xCoordGridStart + gridWidth;
        double gridHeightYCoord = yCoordGridStart + gridHeight;

        // Build the canvas shapes
        createRectangles(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord);
        strokeEdges(gc, inputGameGraph);
        createCirclesFromGraph(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord, inputGameGraph);
    }

    private void createCirclesFromGraph(GraphicsContext gc, double xCoordGridStart, double yCoordGridStart, double gridX, double gridY, GameGraph<GameNode, GameEdge> inputGameGraph) {
        double xCoordGridCurr = xCoordGridStart;
        double yCoordGridCurr = yCoordGridStart;

        gc.setStroke(Color.GREY);

        String gameNodeId;
        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);

                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);

                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);

                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);

                xCoordGridCurr += RECTANGLE_SIZE;
                if (xCoordGridCurr >= gridX) {
                    xCoordGridCurr = xCoordGridStart;
                    yCoordGridCurr += RECTANGLE_SIZE;
                }
            }
        }

        for (GameNode node : ((Set<GameNode>) inputGameGraph.getGameNodeSet())) {
            if (node.getPlayerName() == PlayerName.PLAYER_ONE)
                gc.setFill(Color.BLUE);
            else if (node.getPlayerName() == PlayerName.PLAYER_TWO)
                gc.setFill(Color.RED);
            if (node.isUsed())
                gc.fillOval(node.getXCoord(), node.getYCoord(), CIRCLE_RADIUS, CIRCLE_RADIUS);
        }
        gc.setFill(Color.BLACK);
    }

    private void strokeEdges(GraphicsContext gc, GameGraph<GameNode, GameEdge> inputGameGraph) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(8);

        for (GameEdge edge : inputGameGraph.getGameEdgeSet()) {
            gc.strokeLine(edge.getGameNode1().getXCoord() + CIRCLE_RADIUS / 2.0, edge.getGameNode1().getYCoord() + CIRCLE_RADIUS / 2.0,
                    edge.getGameNode2().getXCoord() + CIRCLE_RADIUS / 2.0, edge.getGameNode2().getYCoord() + CIRCLE_RADIUS / 2.0);
        }

        gc.setLineWidth(2);
    }
}