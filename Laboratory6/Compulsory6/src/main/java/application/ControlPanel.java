package application;

import gamemodel.GameGraph;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlPanel {
    private final MainFrame mainFrame;
    private HBox controlLayout;
    private GameGraph gameGraph;

    public ControlPanel(MainFrame mainFrame, GameGraph gameGraph) {
        this.mainFrame = mainFrame;
        this.gameGraph = gameGraph;
        display();
    }

    public HBox getControlLayout() {
        return controlLayout;
    }

    private void display() {
        mainFrame.getWindow().setOnCloseRequest(e -> {
            e.consume();
            exitGame();
        });

        Button loadBtn = new Button();
        loadBtn.setText("Load");
        loadBtn.setOnAction(e -> loadGame());

        Button saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.setOnAction(e -> saveGame());

        Button exitBtn = new Button();
        exitBtn.setText("Exit");
        exitBtn.setOnAction(e -> exitGame());

        controlLayout = new HBox();
        controlLayout.paddingProperty().setValue(new Insets(10, 10, 30, 10));
        controlLayout.spacingProperty().setValue(10);
        controlLayout.setAlignment(Pos.CENTER);
        controlLayout.getChildren().addAll(loadBtn, saveBtn, exitBtn);
    }

    private void loadGame() { // TODO

    }

    private void saveGame() { // TODO

    }

    private void exitGame() {
        System.out.println("Exited the game!");
        mainFrame.getWindow().close();
    }
}
