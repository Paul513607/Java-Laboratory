package mapdisplay;

public class MyMercator extends Mercator {
    @Override
    double yAxisProjection(double input) {
        double latRad = (input * Math.PI) / 180;
        double mercN = Math.log(Math.tan((Math.PI / 4) + (latRad / 2)));
        return (MapDisplayTool.WINDOW_HEIGHT / 2) - (MapDisplayTool.WINDOW_WIDTH * mercN / (2 * Math.PI));
    }

    @Override
    double xAxisProjection(double input) {
        return (input + 180) * (MapDisplayTool.WINDOW_WIDTH / 360);
    }
}
