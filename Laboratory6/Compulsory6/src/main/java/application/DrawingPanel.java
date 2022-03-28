package application;


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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingPanel {
    public static final int RECTANGLE_SIZE = 60;
    public static final int CIRCLE_RADIUS = 20;

    private final int gridWidth;
    private final int gridHeight;

    private final MainFrame mainFrame;
    private Canvas canvas;
    private GridPane canvasLayout;
    private int noRows = 5;
    private int noColumns = 5;
    private GameGraph gameGraph;


    public DrawingPanel(MainFrame mainFrame, GameGraph gameGraph) {
        this.mainFrame = mainFrame;
        this.gameGraph = gameGraph;
        setArgs();
        gridWidth = RECTANGLE_SIZE * mainFrame.getConfigPanel().getNoRows();
        gridHeight = RECTANGLE_SIZE * mainFrame.getConfigPanel().getNoCols();
        display();
    }

    public DrawingPanel(MainFrame mainFrame, int noRows, int noColumns, GameGraph gameGraph) {
        this.mainFrame = mainFrame;
        this.gameGraph = gameGraph;
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

    public GridPane getCanvasLayout() {
        return canvasLayout;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void display() {
        Group root = new Group();
        canvas = new Canvas(gridWidth + 2 * CIRCLE_RADIUS, gridHeight + 2 * CIRCLE_RADIUS);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        root.getChildren().add(canvas);

        canvasLayout = new GridPane();
        canvasLayout.setAlignment(Pos.CENTER);
        canvasLayout.getChildren().add(root);
    }

    private void drawShapes(GraphicsContext gc) {
        int xCoordGridStart = CIRCLE_RADIUS;
        int yCoordGridStart = CIRCLE_RADIUS;

        int gridWidthXCoord = xCoordGridStart + gridWidth;
        int gridHeightYCoord = yCoordGridStart + gridHeight;

        // Build the canvas shapes
        createRectangles(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord);
        createLineStrokes(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord);
        createCircles(gc, xCoordGridStart, yCoordGridStart, gridWidthXCoord, gridHeightYCoord);
    }

    private void createRectangles(GraphicsContext gc, int xCoordGridStart, int yCoordGridStart, int gridX, int gridY) {
        int xCoordGridCurr = xCoordGridStart;
        int yCoordGridCurr = yCoordGridStart;
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                gc.fillRect(xCoordGridCurr, yCoordGridCurr, RECTANGLE_SIZE, RECTANGLE_SIZE);
                gc.strokeRect(xCoordGridCurr, yCoordGridCurr, RECTANGLE_SIZE, RECTANGLE_SIZE);

                xCoordGridCurr += RECTANGLE_SIZE;
            }
            if (xCoordGridCurr >= gridX) {
                xCoordGridCurr = xCoordGridStart;
                yCoordGridCurr += RECTANGLE_SIZE;
            }
        }
    }

    private void createLineStrokes(GraphicsContext gc, int xCoordGridStart, int yCoordGridStart, int gridX, int gridY) {
        int xCoordGridCurr = xCoordGridStart;
        int yCoordGridCurr = yCoordGridStart;
        gc.setStroke(Color.BLACK);

        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                strokeRectangle(gc, xCoordGridCurr, yCoordGridCurr);

                xCoordGridCurr += RECTANGLE_SIZE;
            }
            if (xCoordGridCurr >= gridX) {
                xCoordGridCurr = xCoordGridStart;
                yCoordGridCurr += RECTANGLE_SIZE;
            }
        }
    }

    private void createCircles(GraphicsContext gc, int xCoordGridStart, int yCoordGridStart, int gridX, int gridY) {
        int circleId = 0;
        int xCoordGridCurr = xCoordGridStart;
        int yCoordGridCurr = yCoordGridStart;
        gc.setStroke(Color.GREY);

        String gameNodeId;
        for (int rowNum = 0; rowNum < noRows; ++rowNum) {
            for (int colNum = 0; colNum < noColumns; ++colNum) {
                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gameNodeId = "c" + circleId;
                circleId++;
                gameGraph.addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0));

                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gameNodeId = "c" + circleId;
                circleId++;
                gameGraph.addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0));


                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gameNodeId = "c" + circleId;
                circleId++;
                gameGraph.addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE));


                gc.fillOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gc.strokeOval(xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE, CIRCLE_RADIUS, CIRCLE_RADIUS);
                gameNodeId = "c" + circleId;
                circleId++;
                gameGraph.addGameNode(new GameNode(gameNodeId, xCoordGridCurr - CIRCLE_RADIUS / 2.0, yCoordGridCurr - CIRCLE_RADIUS / 2.0 + RECTANGLE_SIZE));

                xCoordGridCurr += RECTANGLE_SIZE;
            }
            if (xCoordGridCurr >= gridX) {
                xCoordGridCurr = xCoordGridStart;
                yCoordGridCurr += RECTANGLE_SIZE;
            }
        }
    }

    private void strokeRectangle(GraphicsContext gc, int xCoordGridCurr, int yCoordGridCurr) {
        Random random = new Random();
        List<Boolean> chanceBool = new ArrayList<>(4);

        for (int chanceIndex = 0; chanceIndex < 4; ++chanceIndex){
            chanceBool.add(random.nextBoolean());
        }

        gc.setLineWidth(8);
        if (chanceBool.get(0))
            gc.strokeLine(xCoordGridCurr, yCoordGridCurr, xCoordGridCurr + RECTANGLE_SIZE, yCoordGridCurr);
        if (chanceBool.get(1))
            gc.strokeLine(xCoordGridCurr + RECTANGLE_SIZE, yCoordGridCurr, xCoordGridCurr + RECTANGLE_SIZE, yCoordGridCurr + RECTANGLE_SIZE);
        if (chanceBool.get(2))
            gc.strokeLine(xCoordGridCurr + RECTANGLE_SIZE, yCoordGridCurr + RECTANGLE_SIZE, xCoordGridCurr, yCoordGridCurr + RECTANGLE_SIZE);
        if (chanceBool.get(3))
            gc.strokeLine(xCoordGridCurr, yCoordGridCurr + RECTANGLE_SIZE, xCoordGridCurr, yCoordGridCurr);

        gc.setLineWidth(2);
    }

    @Deprecated
    public void display2() {
        Group groupLayout = new Group();

        int xCoordGridStart = MainFrame.WINDOW_HEIGHT / 2 - gridHeight / 2;
        int yCoordGridStart = MainFrame.WINDOW_WIDTH / 2 - gridWidth / 2;
        int xCoordGridCurr = xCoordGridStart;
        int yCoordGridCurr = yCoordGridStart;

        int gridWidthXCoord = xCoordGridStart + gridWidth;
        int gridHeightYCoord = yCoordGridStart + gridHeight;

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
            }
            if (xCoordGridCurr >= gridWidthXCoord) {
                xCoordGridCurr = xCoordGridStart;
                yCoordGridCurr += RECTANGLE_SIZE;
            }
        }

        canvasLayout = new GridPane();
        canvasLayout.setAlignment(Pos.CENTER);
        canvasLayout.getChildren().add(groupLayout);
    }

    public void recolor(GameNode node, PlayerName playerName) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (playerName == PlayerName.PLAYER_ONE)
            gc.setFill(Color.BLUE);
        else
            gc.setFill(Color.RED);
        double xCoord = node.getxCoord();
        double yCoord = node.getyCoord();

        gc.fillOval(xCoord, yCoord, CIRCLE_RADIUS, CIRCLE_RADIUS);

        gc.setFill(Color.BLACK);
    }
}