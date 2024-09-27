package model;

import java.awt.*;

public class TextBlock {
    //Top left pos
    private Point pos;
    //Use StringBuilder for better performance than pure string
    private StringBuilder text;
    private Color color;
    private Font font;
    public TextBlock(Point pos) {
        this.pos = pos;
        this.text = new StringBuilder();
        this.color = Color.BLACK;
        this.font =  new Font("Serif", Font.PLAIN, 14);
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

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
