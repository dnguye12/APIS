package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class PhotoModel implements Serializable {
    private static final long serialVersionUID = 1L; //for saving and loading purpose
    //transient doesn't get save or load when Serializable
    private transient Image image;
    private String imagePath;
    private String imageName;
    //if the photo is flipped or not
    private transient boolean isFlipped;
    //used for the current element being drawn
    private transient Shape currentShape;
    private transient Color currentColor;
    private transient Stroke currentStroke;
    private transient int currentStrokeSize;
    private transient int currentFontSize;
    private transient Font currentFont;
    //List of already existed elements
    private ArrayList<Stroke> strokes;
    private transient Rectangle currentRectangle;
    private ArrayList<Rectangle> rectangles;
    private transient Ellipse currentEllipse;
    private ArrayList<Ellipse> ellipses;
    private transient TextBlock currentTextBlock;
    private ArrayList<TextBlock> textBlocks;

    public PhotoModel(String path) {
        this.setImage(path);
        this.strokes = new ArrayList<>();
        this.rectangles = new ArrayList<>();
        this.ellipses = new ArrayList<>();
        this.textBlocks = new ArrayList<>();
        this.currentShape = Shape.STROKE;
        this.currentColor = Color.BLACK;
        this.currentStroke = null;
        this.currentStrokeSize = 2;
        this.currentFontSize = 14;
        this.currentFont = new Font("Serif", Font.PLAIN, 14);
        this.currentRectangle = null;
        this.currentEllipse = null;
        this.currentTextBlock = null;
    }

    public Image getImage() {
        return this.image;
    }

    //Check if the image path is correct before setting it
    public void setImage(String path) {
        if (!Objects.equals(path, "")) {
            try {
                File helper = new File(path);
                this.image = ImageIO.read(helper);
                this.imagePath = path;
                this.imageName = helper.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.image = null;
        }
        this.isFlipped = false;
    }

    //Return a default size if image is null
    public Dimension getImageSize() {
        if (this.image != null) {
            return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
        } else {
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

    public ArrayList<TextBlock> getTextBlocks() {
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

    public Shape getCurrentShape() {
        return this.currentShape;
    }

    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
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

    public Color getCurrentColor() {
        return this.currentColor;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public int getCurrentStrokeSize() {
        return this.currentStrokeSize;
    }

    public void setCurrentStrokeSize(int i) {
        this.currentStrokeSize = i;
    }

    public int getCurrentFontSize() {
        return this.currentFontSize;
    }

    public void setCurrentFontSize(int currentFontSize) {
        this.currentFontSize = currentFontSize;
    }

    public void setCurrentFont(Font font) {
        this.currentFont = font;
    }

    public Font getCurrentFont() {
        return currentFont;
    }

    public String getImagePath() {
        return imagePath;
    }

    //Serialize for saving and loading
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (this.imagePath != null && !this.imagePath.isEmpty()) {
            this.setImage(this.imagePath);
            this.isFlipped = false;
            this.currentShape = Shape.STROKE;
            this.currentColor = Color.BLACK;
            this.currentStroke = null;
            this.currentStrokeSize = 2;
            this.currentFontSize = 14;
            this.currentFont = new Font("Serif", Font.PLAIN, 14);
            this.currentRectangle = null;
            this.currentEllipse = null;
            this.currentTextBlock = null;
        }
    }

    //For shape names because using String is not very ideal
    public enum Shape {STROKE, RECTANGLE, ELLIPSE}
}
