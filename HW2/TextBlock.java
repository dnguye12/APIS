import java.awt.*;

public class TextBlock {
    private Point pos;
    private StringBuilder text;

    public TextBlock(Point pos) {
        this.pos = pos;
        this.text = new StringBuilder();
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
}
