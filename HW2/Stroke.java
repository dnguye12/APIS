import java.awt.*;
import java.util.ArrayList;

public class Stroke {
    private ArrayList<Point> points;
    private Color color;

    public Stroke() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
    }

    public Stroke(Color color) {
        this();
        this.color = color;
    }
    public void addPoint(Point point) {
        this.points.add(point);
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
