import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoModel {
    private Image image;
    private boolean isFlipped;
    private ArrayList<Stroke> strokes;
    private ArrayList<TextBlock> textBlocks;

    public PhotoModel(String path) {
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.isFlipped = false;
        this.strokes = new ArrayList<>();
        this.textBlocks = new ArrayList<>();
    }

    public Image getImage() {
        return this.image;
    }

    public Dimension getImageSize() {
        return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
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
    public ArrayList<TextBlock> getTextBlocks(){
        return this.textBlocks;
    }
    public void addTextBlock(TextBlock textBlock) {
        this.textBlocks.add(textBlock);
    }
}
