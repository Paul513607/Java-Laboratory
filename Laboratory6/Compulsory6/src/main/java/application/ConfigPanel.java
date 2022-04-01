package application;

import gamemodel.GameGraph;
import gamemodel.GameNode;
import gamemodel.PlayerName;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Data;

import java.util.Random;

/** Class for configuring the number of rows and columns of the game grid. */
@Data
public class ConfigPanel {
    private final MainFrame mainFrame;
    private int noRows = 5;
    private int noCols = 5;
    private HBox configLayout;

    public ConfigPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        display();
    }

    public void display() {
        Label label = new Label();
        label.setText("Grid size: ");

        Spinner<Integer> rowSpinner = new Spinner<>(3, 16, noRows);
        Spinner<Integer> colSpinner = new Spinner<>(3, 16, noCols);

        Button createButton = new Button();
        createButton.setText("Create");
        createButton.setOnAction(e -> changeMatrixDimensions(rowSpinner, colSpinner));

        configLayout = new HBox();
        configLayout.paddingProperty().setValue(new Insets(10, 10, 10, 10));
        configLayout.spacingProperty().setValue(10);
        configLayout.setAlignment(Pos.CENTER);
        configLayout.getChildren().addAll(label, rowSpinner, colSpinner, createButton);
    }

    private void changeMatrixDimensions(Spinner<Integer> rowSpinner, Spinner<Integer> colSpinner) {
        noRows = rowSpinner.getValue();
        noCols = colSpinner.getValue();

        mainFrame.remakeGameGraph();
        mainFrame.getGameGraph().setNoRows(noRows);
        mainFrame.getGameGraph().setNoColumns(noCols);
        mainFrame.setCurrentPlayer(PlayerName.PLAYER_ONE);

        boolean aiStarts = (new Random()).nextBoolean();
        if (mainFrame.isAiEnabled()) {
            if (aiStarts) {
                mainFrame.getGameAI().setStarting(true);
                mainFrame.getGameAI().setAiPlayer(PlayerName.PLAYER_ONE);
            }
            else {
                mainFrame.getGameAI().setStarting(false);
                mainFrame.getGameAI().setAiPlayer(PlayerName.PLAYER_TWO);
            }
        }

        DrawingPanel tempDrawingPanel = new DrawingPanel(mainFrame, noRows, noCols);
        mainFrame.setCanvas(tempDrawingPanel);
        mainFrame.getRoot().setCenter(tempDrawingPanel.getCanvasLayout());
        System.out.println("Game size set to: " + getNoRows() + " rows, " +  getNoCols() + " columns.");

        mainFrame.testPerfectMatchingOfGameGraph(false);

        if (mainFrame.isAiEnabled()) {
            mainFrame.getGameAI().setMainFrame(mainFrame);
            mainFrame.getGameAI().setGameGraph(mainFrame.getGameGraph());
            mainFrame.getGameAI().findMaxMatching();

            if (aiStarts) {
                GameNode aiNodeChoice;
                if (mainFrame.isPerfectMatching() || !mainFrame.getGameAI().isStarting())
                    aiNodeChoice = mainFrame.getGameAI().notStartingOrPerfectMatchLogicChoice();
                else
                    aiNodeChoice = mainFrame.getGameAI().startingAndPerfectMatchLogicChoice();

                mainFrame.makeMove(aiNodeChoice);
            }
        }
    }
}
