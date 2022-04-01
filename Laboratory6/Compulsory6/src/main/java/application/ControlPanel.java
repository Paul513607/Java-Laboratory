package application;

import customexceptions.InvalidGameException;
import gamemodel.GameEdge;
import gamemodel.GameGraph;
import gamemodel.GameNode;
import gamemodel.PlayerName;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import lombok.Data;

/** Class for implementing control buttons such as PlayAgainstAI, ExportAsPNG, Save, Load, Help, Exit. */
@Data
public class ControlPanel {
    private final MainFrame mainFrame;
    private HBox controlLayout;

    private Button enableAIBtn;
    private Button disableAIBtn;

    public ControlPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        display();
    }

    private void display() {
        mainFrame.getWindow().setOnCloseRequest(e -> {
            e.consume();
            exitGame();
        });

        enableAIBtn = new Button();
        enableAIBtn.setText("Enable AI");
        enableAIBtn.setOnAction(e -> enableAI());

        disableAIBtn = new Button();
        disableAIBtn.setText("Disable AI");
        disableAIBtn.setOnAction(e -> disableAI());

        Button exportBtn = new Button();
        exportBtn.setText("Export as PNG");
        exportBtn.setOnAction(e -> exportAsPNG());

        Button loadBtn = new Button();
        loadBtn.setText("Load");
        loadBtn.setOnAction(e -> {
            try {
                loadGame();
            } catch (InvalidGameException ex) {
                ex.printStackTrace();
            }
        });

        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.setOnAction(e -> saveGame());

        Button helpBtn = new Button();
        helpBtn.setText("Help");
        helpBtn.setOnAction(e -> showHelp());

        Button exitBtn = new Button();
        exitBtn.setText("Exit");
        exitBtn.setOnAction(e -> exitGame());

        controlLayout = new HBox();
        controlLayout.paddingProperty().setValue(new Insets(10, 10, 30, 10));
        controlLayout.spacingProperty().setValue(10);
        controlLayout.setAlignment(Pos.CENTER);

        if (!mainFrame.isAiEnabled())
            controlLayout.getChildren().add(enableAIBtn);
        else
            controlLayout.getChildren().add(disableAIBtn);

        controlLayout.getChildren().addAll(exportBtn, loadBtn, saveBtn, helpBtn, exitBtn);
    }

    private void enableAI() {
        mainFrame.setControlPanel(this);
        controlLayout.getChildren().set(0, disableAIBtn);
        mainFrame.getRoot().setBottom(controlLayout);

        mainFrame.setAiEnabled(true);
        mainFrame.setMouseClickEvent();
    }

    private void disableAI() {
        controlLayout.getChildren().set(0, enableAIBtn);
        mainFrame.setControlPanel(this);
        mainFrame.getRoot().setBottom(controlLayout);

        mainFrame.setAiEnabled(false);
        mainFrame.setMouseClickEvent();
    }

    private void exportAsPNG() {
        FileManager pngExporter = new FileManager();
        pngExporter.captureAndSaveDisplay(mainFrame.getRoot());
    }

    private void loadGame() throws InvalidGameException {
        FileManager gameLoader = new FileManager();
        GameGraph<GameNode, GameEdge> tempGameGraph = gameLoader.loadGame();
        if (tempGameGraph == null) {
            AlertBox alertBox = new AlertBox();
            alertBox.display("Invalid Game File", "The file you chose is invalid.\n");
            throw new InvalidGameException("Invalid Game File");
        }

        mainFrame.getConfigPanel().setNoRows(tempGameGraph.getNoRows());
        mainFrame.getConfigPanel().setNoCols(tempGameGraph.getNoColumns());
        mainFrame.getConfigPanel().display();
        mainFrame.getRoot().setTop(mainFrame.getConfigPanel().getConfigLayout());

        PlayerName nextPlayer = mainFrame.getCanvas().swapPlayer(tempGameGraph.getPreviouslySelectedNode().getPlayerName());
        mainFrame.setCurrentPlayer(nextPlayer);
        mainFrame.remakeGameGraph(tempGameGraph);
        mainFrame.setGameOn(true);

        mainFrame.getCanvas().drawFromGameGraph(tempGameGraph);
        mainFrame.getRoot().setCenter(mainFrame.getCanvas().getCanvasLayout());

        System.out.println("Game size set to: " + tempGameGraph.getNoRows() + " rows, " +  tempGameGraph.getNoColumns() + " columns.");
    }

    private void saveGame() {
        FileManager gameSaver = new FileManager();
        gameSaver.saveCurrentGame(mainFrame.getGameGraph());
    }

    private void exitGame() {
        System.out.println("Exited the game!");
        mainFrame.getWindow().close();
    }

    private void showHelp() {
        AlertBox alertBox = new AlertBox();
        alertBox.display("Help", """
                In this game each player will place
                a stone on an empty circle, such that
                they always place a stone adjacent
                (with a stroked line in between)
                to the previously placed stone.
                The player who can't make a move
                loses!
                """);
    }
}
