package application;

import gamemodel.GameGraph;
import gamemodel.GameNode;
import gamemodel.PlayerName;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;

import static javax.swing.SwingConstants.CENTER;

public class MainFrame extends Application {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH;

    private ConfigPanel configPanel;
    private ControlPanel controlPanel;
    private DrawingPanel canvas;

    private Stage window;
    private BorderPane root;

    private GameGraph gameGraph = new GameGraph();
    private PlayerName currentPlayer = PlayerName.PLAYER_ONE;

    public MainFrame() {
    }

    public void startMainFrame(String[] args) {
        launch(args);
    }

    public Stage getWindow() {
        return window;
    }

    public ConfigPanel getConfigPanel() {
        return configPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public DrawingPanel getCanvas() {
        return canvas;
    }

    public void setConfigPanel(ConfigPanel configPanel) {
        this.configPanel = configPanel;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public void setCanvas(DrawingPanel canvas) {
        this.canvas = canvas;
    }

    public BorderPane getBorderPane() {
        return root;
    }
    public void setBorderPane(BorderPane root) {
        this.root = root;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public GameGraph getGameGraph() {
        return gameGraph;
    }

    public void setGameGraph(GameGraph gameGraph) {
        this.gameGraph = gameGraph;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("My Test App");

        // Set up ConfigPanel layout
        configPanel = new ConfigPanel(this, gameGraph);
        HBox layoutTop = configPanel.getConfigLayout();

        // Set up DrawingPanel layout
        canvas = new DrawingPanel(this, gameGraph);
        GridPane layoutCenter = canvas.getCanvasLayout();

        // Set up ControlPanel layout
        controlPanel = new ControlPanel(this, gameGraph);
        HBox layoutBottom = controlPanel.getControlLayout();

        root = new BorderPane();
        root.setTop(layoutTop);
        root.setCenter(layoutCenter);
        root.setBottom(layoutBottom);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setScene(scene);
        window.show();

        root.setOnMouseClicked(e -> {
            double mouseXCoord = e.getSceneX();
            double mouseYCoord = e.getSceneY();
            GameNode tempGameNode = gameGraph.getGameNodeAtCoords(mouseXCoord, mouseYCoord, currentPlayer, canvas.getGridWidth(), canvas.getGridHeight());

            if (tempGameNode != null) {
                canvas.recolor(tempGameNode, currentPlayer);
                swapPlayer();
            }
        });
    }

    private void swapPlayer() {
        if (currentPlayer == PlayerName.PLAYER_ONE)
            currentPlayer = PlayerName.PLAYER_TWO;
        else
            currentPlayer = PlayerName.PLAYER_ONE;
    }
}
