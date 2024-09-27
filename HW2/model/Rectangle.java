package model;

import java.awt.*;
import java.io.Serializable;

public class Rectangle implements Serializable {
    private static final long serialVersionUID = 1L; //Used for saving and loading
    //2 points to calculate top left point for drawing position
    //Java swing draws from top left of shape
    private Point startPoint;
    private Point endPoint;
    private Color color;
    private int strokeSize;

    public Rectangle(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.color = Color.BLACK;
        this.strokeSize = 2;
    }

    public Rectangle(Point startPoint, Point endPoint, Color color) {
        this(startPoint, endPoint);
        this.color = color;
    }

    public void setEndPoint(Point point) {
        this.endPoint = point;
    }

    public Point getTopLeft() {
        int x = Math.min(this.startPoint.x, this.endPoint.x);
        int y = Math.min(this.startPoint.y, this.endPoint.y);
        return new Point(x, y);
    }

    public Dimension getSize() {
        return new Dimension(Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
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
