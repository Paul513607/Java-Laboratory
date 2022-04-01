package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import gamemodel.GameGraph;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/** Class for managing file work in the app. Used for implementing Control commands such ad ExportAsPNG, Save, Load. */
public class FileManager {
    public void captureAndSaveDisplay(Node node){
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        // Set starting directory
        fileChooser.setInitialDirectory(new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory6/Compulsory6/src/main/resources"));

        // Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                // Pad the capture area
                WritableImage writableImage = new WritableImage((int) MainFrame.WINDOW_WIDTH + 20,
                        (int) MainFrame.WINDOW_HEIGHT + 20);

                node.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                // Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveCurrentGame(GameGraph gameGraph) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("json files (*.json)", "*.json"));

        // Set starting directory
        fileChooser.setInitialDirectory(new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory6/Compulsory6/src/main/resources"));

        // Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(file, gameGraph);
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }

    public GameGraph loadGame() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("json files (*.json)", "*.json"));

        // Set starting directory
        fileChooser.setInitialDirectory(new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Java-Laboratory/Laboratory6/Compulsory6/src/main/resources"));

        // Prompt user to select a file
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            GameGraph gameGraph = null;
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                gameGraph = objectMapper.readValue(file, GameGraph.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gameGraph;
        }
        return null;
    }
}
