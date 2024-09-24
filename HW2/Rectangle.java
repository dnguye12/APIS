import java.awt.*;

public class Rectangle {
    private Point startPoint;
    private Point endPoint;

    public Rectangle(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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
}
