import java.awt.*;

public class TextBlock {
    private Point pos;
    private StringBuilder text;
    private Color color;
    public TextBlock(Point pos) {
        this.pos = pos;
        this.text = new StringBuilder();
        this.color = Color.BLACK;
    }
    public TextBlock(Point pos, Color color) {
        this(pos);
        this.color = color;
    }

    public Point getPos() {
        return this.pos;
    }

    public void addCharacter(char c) {
        this.text.append(c);
    }

    public String getText() {
        return this.text.toString();
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
