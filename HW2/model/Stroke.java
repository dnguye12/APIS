package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Stroke implements Serializable {
    private static final long serialVersionUID = 1L; //Used for saving and loading
    //List of points
    private ArrayList<Point> points;
    private Color color;
    private int strokeSize;

    public Stroke() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.strokeSize = 2;
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

    public int getStrokeSize() {
        return this.strokeSize;
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }
}
