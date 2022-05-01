package mapdisplay;

public class SphericalMercator extends Mercator {

    @Override
    double xAxisProjection(double input) {
        return Math.toRadians(input) * RADIUS_MAJOR;
    }

    @Override
    double yAxisProjection(double input) {
        return Math.log(Math.tan(Math.PI / 4.0 + Math.toRadians(input) / 2.0)) * RADIUS_MAJOR;
    }
}
