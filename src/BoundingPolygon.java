import java.awt.Polygon;
//Tried to make a trapezoid, Scrapped
public class BoundingPolygon {
    private Polygon polygon;

    public BoundingPolygon(int[] xpoints, int[] ypoints) {
        polygon = new Polygon(xpoints, ypoints, xpoints.length);
    }

    public boolean contains(int x, int y) {
        return polygon.contains(x, y);
    }
}
