import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PhotoView extends JComponent {
    private final int PADDING = 20;
    private PhotoModel model;
    private Image image;

    public PhotoView(PhotoModel model) {
        this.model = model;
        this.image = model.getImage();
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.handleSize();
    }

    public void setImage(Image image) {
        this.image = image;
        this.handleSize();
        this.repaint();
    }

    private void handleSize() {
        int width = 420;
        int height = 420;
        if (this.image != null) {
            Dimension helper = this.model.getImageSize();
            width = (int) (helper.getWidth() + 4 * PADDING);
            height = (int) (helper.getHeight() + 4 * PADDING);
        }
        this.setPreferredSize(new Dimension(width, height));
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int frameW = this.getWidth();
        int frameH = this.getHeight();

        Stroke currentStroke = this.model.getCurrentStroke();
        Rectangle currentRectangle = this.model.getCurrentRectangle();
        Ellipse currentEllipse = this.model.getCurrentEllipse();
        TextBlock currentTextBlock = this.model.getCurrentTextBlock();;

        this.drawBackground(g2d, frameW, frameH);
        if (this.image != null) {
            this.drawContent(g2d, frameW, frameH);

            if (this.model.getIsFlipped()) {
                if (currentStroke != null) {
                    this.drawStroke(g2d, currentStroke);
                }
                ArrayList<Stroke> strokes = this.model.getStrokes();
                for (Stroke stroke : strokes) {
                    this.drawStroke(g2d, stroke);
                }

                if(currentRectangle != null) {
                    this.drawRectangle(g2d, currentRectangle);
                }
                ArrayList<Rectangle> rectangles = this.model.getRectangles();
                for(Rectangle rectangle : rectangles) {
                    this.drawRectangle(g2d, rectangle);
                }

                if(currentEllipse != null) {
                    this.drawEllipse(g2d, currentEllipse);
                }
                ArrayList<Ellipse> ellipses = this.model.getEllipses();
                for(Ellipse ellipse : ellipses) {
                    this.drawEllipse(g2d, ellipse);
                }

                if (currentTextBlock != null) {
                    this.drawTextBlock(g2d, currentTextBlock);
                }
                for (TextBlock textBlock : this.model.getTextBlocks()) {
                    this.drawTextBlock(g2d, textBlock);
                }
            }
        }
    }

    private void drawBackground(Graphics2D g2d, int frameW, int frameH) {
        g2d.setColor(Color.LIGHT_GRAY);

        for (int x = 0; x < frameW - PADDING; x += PADDING) {
            g2d.drawLine(x, PADDING, x, frameH - PADDING);
        }
        g2d.drawLine(frameW - PADDING, PADDING, frameW - PADDING, frameH - PADDING);
        for (int y = 0; y < frameH - PADDING; y += PADDING) {
            g2d.drawLine(PADDING, y, frameW - PADDING, y);
        }
        g2d.drawLine(PADDING, frameH - PADDING, frameW - PADDING, frameH - PADDING);
    }

    private void drawContent(Graphics2D g2d, int frameW, int frameH) {
        Dimension d = this.model.getImageSize();
        int helperX = (int) ((frameW - d.getWidth()) / 2);
        int helperY = (int) ((frameH - d.getHeight()) / 2);
        if (!this.model.getIsFlipped() && this.image != null) {
            g2d.drawImage(this.image, helperX, helperY, this);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(helperX, helperY, (int) d.getWidth(), (int) d.getHeight());
        }
    }

    private void drawStroke(Graphics2D g2d, Stroke stroke) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 1; i < stroke.getPoints().size(); i++) {
            Point p1 = stroke.getPoints().get(i - 1);
            Point p2 = stroke.getPoints().get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private void drawTextBlock(Graphics2D g2d, TextBlock textBlock) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("San Francisco", Font.PLAIN, 14));
        FontMetrics metrics = g2d.getFontMetrics();

        Point pos = textBlock.getPos();
        int x = pos.x;
        int y = pos.y;
        String text = textBlock.getText();

        String[] words = text.split(" ");
        for (String word : words) {
            int wordWidth = metrics.stringWidth(word + " ");

            if (x != pos.x && (x + wordWidth > (this.getWidth() - 4 * PADDING))) {
                x = pos.x;
                y += metrics.getAscent() + metrics.getDescent() + metrics.getLeading();
            }
            g2d.drawString(word, x, y);
            x += wordWidth;
        }
    }

    private void drawRectangle(Graphics2D g2d, Rectangle rectangle) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point startPoint = rectangle.getTopLeft();
        Dimension size = rectangle.getSize();
        g2d.drawRect(startPoint.x, startPoint.y, size.width, size.height);
    }

    private void drawEllipse(Graphics2D g2d, Ellipse ellipse) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point startPoint = ellipse.getTopLeft();
        Dimension size = ellipse.getSize();
        g2d.drawOval(startPoint.x, startPoint.y, size.width, size.height);
    }
}
