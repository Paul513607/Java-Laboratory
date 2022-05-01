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

        Mercator mercator = new MyMercator();
        Pane root = new Pane();
        root.setId("pane");

        for (City city : cityList) {
            if (!city.isCapital())
                continue;

            double cityLatitude = city.getLatitude();
            double cityLongitude = city.getLongitude();

            double xCoordinate = mercator.xAxisProjection(cityLatitude);
            double yCoordinate = mercator.yAxisProjection(cityLongitude);

            Text cityNameLabel = new Text(xCoordinate - 30, yCoordinate, city.getName());
            cityNameLabel.setStyle("-fx-text-fill: white;");

            Circle cityCircle = new Circle();
            cityCircle.setFill(Color.ORANGE);
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

    public Point convertGeoToPixel(double latitude, double longitude,
                                  double mapWidth, // in pixels
                                  double mapHeight, // in pixels
                                  double mapLngLeft, // in degrees. the longitude of the left side of the map (i.e. the longitude of whatever is depicted on the left-most part of the map image)
                                  double mapLngRight, // in degrees. the longitude of the right side of the map
                                  double mapLatBottom) // in degrees.  the latitude of the bottom of the map
    {
        double mapLatBottomRad = mapLatBottom * Math.PI / 180;
        double latitudeRad = latitude * Math.PI / 180;
        double mapLngDelta = (mapLngRight - mapLngLeft);

        double worldMapWidth = ((mapWidth / mapLngDelta) * 360) / (2 * Math.PI);
        double mapOffsetY = (worldMapWidth / 2 * Math.log((1 + Math.sin(mapLatBottomRad)) / (1 - Math.sin(mapLatBottomRad))));

        double x = (longitude - mapLngLeft) * (mapWidth / mapLngDelta);
        double y = mapHeight - ((worldMapWidth / 2 * Math.log((1 + Math.sin(latitudeRad)) / (1 - Math.sin(latitudeRad)))) - mapOffsetY);

        return new Point(x, y); // the pixel x,y value of this point on the map image
    }

    @Override
    public void mapInitialized() {

    }
}
