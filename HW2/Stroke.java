import java.awt.*;
import java.util.ArrayList;

public class Stroke {
    private ArrayList<Point> points;

    public Stroke() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }
}
