import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PhotoModel {
    private Image image;
    private boolean isFlipped;
    enum Shape {STROKE, RECTANGLE, ELLIPSE};
    private Shape currentShape;
    private Stroke currentStroke;
    private ArrayList<Stroke> strokes;
    private Rectangle currentRectangle;
    private ArrayList<Rectangle> rectangles;
    private Ellipse currentEllipse;
    private ArrayList<Ellipse> ellipses;
    private TextBlock currentTextBlock;
    private ArrayList<TextBlock> textBlocks;

    public PhotoModel(String path) {
        if(!Objects.equals(path, "")) {
            try {
                this.image = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.isFlipped = false;
        this.strokes = new ArrayList<>();
        this.rectangles = new ArrayList<>();
        this.ellipses = new ArrayList<>();
        this.textBlocks = new ArrayList<>();
        this.currentShape = Shape.STROKE;
        this.currentStroke = null;
        this.currentRectangle = null;
        this.currentEllipse = null;
        this.currentTextBlock = null;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(String path) {
        if(!Objects.equals(path, "")) {
            try {
                this.image = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            this.image = null;
        }
        this.isFlipped = false;
    }

    public Dimension getImageSize() {
        if(this.image != null) {
            return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
        }else {
            return new Dimension(420, 420);
        }
    }

    public boolean getIsFlipped() {
        return this.isFlipped;
    }

    public void toggleFlip() {
        this.isFlipped = !this.isFlipped;
    }

    public ArrayList<Stroke> getStrokes() {
        return this.strokes;
    }

    public void addStroke(Stroke stroke) {
        this.strokes.add(stroke);
    }

    public void clearStroke() {
        this.strokes.clear();
    }
    public Stroke getCurrentStroke() {
        return this.currentStroke;
    }
    public void setCurrentStroke(Stroke stroke) {
        this.currentStroke = stroke;
    }
    public void addPointStroke(Point point) {
        this.currentStroke.addPoint(point);
    }
    public ArrayList<TextBlock> getTextBlocks(){
        return this.textBlocks;
    }
    public void addTextBlock(TextBlock textBlock) {
        this.textBlocks.add(textBlock);
    }
    public TextBlock getCurrentTextBlock() {
        return currentTextBlock;
    }
    public void setCurrentTextBlock(TextBlock textBlock) {
        this.currentTextBlock = textBlock;
    }
    public void addCharacterCurrentTextBlock(char c) {
        this.currentTextBlock.addCharacter(c);
    }
    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
    }

    public Shape getCurrentShape() {
        return this.currentShape;
    }

    public Rectangle getCurrentRectangle() {
        return this.currentRectangle;
    }

    public void setCurrentRectangle(Rectangle rectangle) {
        this.currentRectangle = rectangle;
    }

    public void addRectangle(Rectangle rectangle) {
        this.rectangles.add(rectangle);
    }

    public ArrayList<Rectangle> getRectangles() {
        return this.rectangles;
    }

    public Ellipse getCurrentEllipse() {
        return this.currentEllipse;
    }

    public void setCurrentEllipse(Ellipse ellipse) {
        this.currentEllipse = ellipse;
    }

    public void addEllipse(Ellipse ellipse) {
        this.ellipses.add(ellipse);
    }

    public ArrayList<Ellipse> getEllipses() {
        return this.ellipses;
    }
}
