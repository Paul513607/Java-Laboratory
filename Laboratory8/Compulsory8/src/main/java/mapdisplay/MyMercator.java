package mapdisplay;

public class MyMercator extends Mercator {
    double scale;

    @Override
    double yAxisProjection(double input) {
        double latRad = (input * Math.PI) / 180;
        double mercN = Math.log(Math.tan((Math.PI / 4) + (latRad / 2)));
        // return (MapDisplayTool.WINDOW_HEIGHT / 2) - (MapDisplayTool.WINDOW_WIDTH * mercN / (2 * Math.PI));
        return 180.0 / Math.PI * Math.log(Math.tan(Math.PI / 4.0 + input * (Math.PI / 180.0) / 2.0)) * scale;
    }

    @Override
    double xAxisProjection(double input) {
        double x = RADIUS_MAJOR * Math.toRadians(input);
        scale = RADIUS_MAJOR * Math.PI / 180.0;
        return x;
        // return (input + 180) * (MapDisplayTool.WINDOW_WIDTH / 360);
    }
}
