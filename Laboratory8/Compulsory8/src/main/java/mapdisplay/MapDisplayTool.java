package mapdisplay;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import dao.CityDao;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.City;

import java.util.List;

public class MapDisplayTool extends Application implements MapComponentInitializedListener {
    public static final double WINDOW_WIDTH = 1920.0;
    public static final double WINDOW_HEIGHT = 1020.0;
    public static final double SCALE_WIDTH = (WINDOW_WIDTH / 2.0) / 20037508.34;
    public static final double SCALE_HEIGHT = (WINDOW_HEIGHT / 2.0) / 34619289.37;

    public MapDisplayTool() {
    }

    public void startDisplay(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Map of cities of the world");

        CityDao cityDao = new CityDao();
        List<City> cityList = cityDao.findAll();

        Mercator mercator = new EllipticalMercator();
        Pane root = new Pane();
        root.setId("pane");

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        for (City city : cityList) {
            double xCoordinate = mercator.xAxisProjection(city.getLatitude());
            double yCoordinate = mercator.yAxisProjection(city.getLongitude());
            xCoordinate = ((int) (xCoordinate * SCALE_WIDTH));
            yCoordinate = ((int) (yCoordinate * SCALE_HEIGHT));

            if (xCoordinate < minX)
                minX = xCoordinate;
            if (yCoordinate < minY)
                minY = yCoordinate;
        }

        for (City city : cityList) {
            if (!city.isCapital())
                continue;

            double xCoordinatePrev = mercator.xAxisProjection(city.getLatitude());
            double yCoordinatePrev = mercator.yAxisProjection(city.getLongitude());

            double xCoordinate = xCoordinatePrev;
            double yCoordinate = yCoordinatePrev;

            xCoordinatePrev = ((int) (xCoordinatePrev * SCALE_WIDTH));
            yCoordinatePrev = ((int) (yCoordinatePrev * SCALE_HEIGHT));

            xCoordinate = ((int) (xCoordinate * SCALE_WIDTH)) + WINDOW_WIDTH / 2;
            yCoordinate = ((int) (yCoordinate * SCALE_HEIGHT)) + WINDOW_HEIGHT / 2;

            Text cityNameLabel = new Text(xCoordinate - 30, yCoordinate, city.getName());

            Circle cityCircle = new Circle();
            cityCircle.setFill(Color.BLUE);
            cityCircle.setRadius(5);
            cityCircle.setCenterX(xCoordinate);
            cityCircle.setCenterY(yCoordinate + 5);

            root.getChildren().addAll(cityNameLabel, cityCircle);

        }

        root.setOnMouseClicked(e -> System.out.println(e.getSceneX() + " " + e.getSceneY()));

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void mapInitialized() {

    }
}
