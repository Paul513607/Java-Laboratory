package application;

import algorithms.DenseEdmondsMaximumCardinalityMatchingAlgorithm;
import algorithms.LogicAI;
import gamemodel.GameEdge;
import gamemodel.GameGraph;
import gamemodel.GameNode;
import gamemodel.PlayerName;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Data;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.util.Random;

import static javax.swing.SwingConstants.CENTER;

/** The class that starts the application and processes the config, control and drawing. */
@Data
public class MainFrame extends Application {
    public static final double WINDOW_WIDTH = 1000;
    public static final double WINDOW_HEIGHT = 1000;

    private ConfigPanel configPanel;
    private ControlPanel controlPanel;
    private DrawingPanel canvas;

    private LogicAI gameAI;

    private String[] args;
    private Stage window;
    private BorderPane root;

    private GameGraph<GameNode, GameEdge> gameGraph;
    private PlayerName currentPlayer = PlayerName.PLAYER_ONE;
    private boolean gameOn = true;
    private boolean aiEnabled = false;
    private boolean isPerfectMatching = false;

    public void startMainFrame(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameGraph = new GameGraph<>(5, 5);
        System.out.println("Game size set to: 5 rows, 5 columns.");

        window = primaryStage;
        window.setTitle("Positional Game");

        // Set up ConfigPanel layout
        configPanel = new ConfigPanel(this);
        HBox layoutTop = configPanel.getConfigLayout();

        // Set up DrawingPanel layout
        canvas = new DrawingPanel(this);
        StackPane layoutCenter = canvas.getCanvasLayout();

        // Set up ControlPanel layout
        controlPanel = new ControlPanel(this);
        HBox layoutBottom = controlPanel.getControlLayout();

        root = new BorderPane();
        root.setTop(layoutTop);
        root.setCenter(layoutCenter);
        root.setBottom(layoutBottom);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setScene(scene);
        window.show();

        gameAI = new LogicAI(this, currentPlayer);
        gameAI.setStarting(false);
        gameAI.findMaxMatching();
        testPerfectMatchingOfGameGraph(true);

        if (gameOn) {
            setMouseClickEvent();
        }
    }

    public void setMouseClickEvent() {
        root.setOnMouseClicked(e -> {
            if (!gameOn)
                return;
            double mouseXCoord = e.getSceneX();
            double mouseYCoord = e.getSceneY();
            GameNode tempGameNode = gameGraph.getUnusedGameNodeAtCoords(mouseXCoord, mouseYCoord, currentPlayer, canvas.getGridWidth(), canvas.getGridHeight());
            makeMove(tempGameNode);

            if (aiEnabled && gameOn && tempGameNode != null) {
                gameAI.setAiPlayer(currentPlayer);
                GameNode aiNodeChoice;
                if (isPerfectMatching || !gameAI.isStarting())
                    aiNodeChoice = gameAI.notStartingOrPerfectMatchLogicChoice();
                else
                    aiNodeChoice = gameAI.startingAndPerfectMatchLogicChoice();

                makeMove(aiNodeChoice);
            }
        });
    }

    public void makeMove(GameNode gameNode) {
        if (gameNode != null) {
            boolean isWin = checkWin();
            canvas.recolor(gameNode, currentPlayer, isWin);
            root.setCenter(canvas.getCanvasLayout());
            if (isWin) {
                displayAlertWin();
                gameAI.setAiPlayer(null);
            }
            swapPlayer();
        }
    }

    public void swapPlayer() {
        if (currentPlayer == PlayerName.PLAYER_ONE)
            currentPlayer = PlayerName.PLAYER_TWO;
        else if (currentPlayer == PlayerName.PLAYER_TWO)
            currentPlayer = PlayerName.PLAYER_ONE;
    }

    private boolean checkWin() {
        if (gameGraph.getPreviouslySelectedNode() != null && gameGraph.edgesOfNode(gameGraph.getPreviouslySelectedNode()).isEmpty()) {
            root.setOnMouseClicked(e -> {});
            gameOn = false;
            return true;
        }
        return false;
    }

    public void remakeGameGraph() {
        gameOn = true;
        gameGraph = new GameGraph<>();
        gameGraph.obtainGameNodesFromEdges();
        setMouseClickEvent();
    }

    public void remakeGameGraph(GameGraph<GameNode, GameEdge> newGameGraph) {
        gameOn = true;
        gameGraph = newGameGraph;
        gameGraph.obtainGameNodesFromEdges();
        setMouseClickEvent();
    }

    private void displayAlertWin() {
        String playerWinMessage = "";
        switch (currentPlayer) {
            case PLAYER_ONE -> playerWinMessage = "Player 1 wins!";
            case PLAYER_TWO -> playerWinMessage = "Player 2 wins!";
        }

        if (aiEnabled && currentPlayer == gameAI.getAiPlayer()) {
            playerWinMessage = "AI wins!";
        }

        AlertBox alertBox = new AlertBox();
        alertBox.display("Win!", playerWinMessage);
    }

    public void testPerfectMatchingOfGameGraph(boolean displayText) {
        DenseEdmondsMaximumCardinalityMatchingAlgorithm algorithm = new DenseEdmondsMaximumCardinalityMatchingAlgorithm(gameGraph);
        algorithm.denseEdmondsMCMatchingRun();
        AlertBox alertBox = new AlertBox();
        String matchingMessage = "";
        if (!algorithm.isPerfectMatching()) {
            isPerfectMatching = false;
            System.out.println("The Game Graph does not have a perfect matching.\nPlayer 1 has a strategy to win all the time on this graph!");
            matchingMessage = "The Game Graph does not \nhave a perfect matching.\nPlayer 1 has a strategy to win \nall the time on this graph!";

        } else {
            isPerfectMatching = true;
            System.out.println("The Game Graph has a perfect matching.\nEach player has a fair chance to win.");
            matchingMessage = "The Game Graph has \na perfect matching.\nEach player has \na fair chance to win.";
        }
        if (displayText)
            alertBox.display("Graph matching message", matchingMessage);
    }
}
