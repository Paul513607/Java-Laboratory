package application;

import gamemodel.GameGraph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ConfigPanel {
    private final MainFrame mainFrame;
    private int noRows = 5;
    private int noCols = 5;
    private HBox configLayout;
    private GameGraph gameGraph;

    public ConfigPanel(MainFrame mainFrame, GameGraph gameGraph) {
        this.mainFrame = mainFrame;
        this.gameGraph = gameGraph;
        display();
    }

    public int getNoRows() {
        return noRows;
    }

    public int getNoCols() {
        return noCols;
    }

    public HBox getConfigLayout() {
        return configLayout;
    }

    public void display() {
        Label label = new Label();
        label.setText("Grid size: ");

        Spinner<Integer> rowSpinner = new Spinner<>(1, 10, 5);
        Spinner<Integer> colSpinner = new Spinner<>(1, 10, 5);

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

        DrawingPanel tempDrawingPanel = new DrawingPanel(mainFrame, noRows, noCols, gameGraph);
        mainFrame.setCanvas(tempDrawingPanel);
        mainFrame.getBorderPane().setCenter(tempDrawingPanel.getCanvasLayout());
        System.out.println(getNoRows() + " " +  getNoCols());
    }
}
