import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PhotoView extends JComponent {
    private final int PADDING = 20;
    private PhotoModel model;
    private Image image;
    private Stroke currentStroke;
    private TextBlock currentTextBlock;

    public PhotoView(PhotoModel model) {
        this.model = model;
        this.image = model.getImage();
        this.currentStroke = null;
        this.currentTextBlock = null;
        this.setFocusable(true);
        this.requestFocusInWindow();

        int width = 420;
        int height = 420;
        if (this.image != null) {
            Dimension helper = this.model.getImageSize();
            width = (int) (helper.getWidth() + 4 * PADDING);
            height = (int) (helper.getHeight() + 4 * PADDING);
        }
        this.setPreferredSize(new Dimension(width, height));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    model.toggleFlip();
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (model.getIsFlipped()) {
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        if (currentStroke != null) {
                            model.addStroke(currentStroke);
                        }
                        currentStroke = new Stroke();
                        currentStroke.addPoint(e.getPoint());
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        if(currentTextBlock != null) {
                            model.addTextBlock(currentTextBlock);
                        }
                        currentTextBlock = new TextBlock(e.getPoint());
                    }
                }
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (model.getIsFlipped() && currentStroke != null) {
                    currentStroke.addPoint(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (model.getIsFlipped() && currentStroke != null) {
                    model.addStroke(currentStroke);
                    currentStroke = null;
                    repaint();
                }
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(model.getIsFlipped() && currentTextBlock != null) {
                    currentTextBlock.addCharacter(e.getKeyChar());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int frameW = this.getWidth();
        int frameH = this.getHeight();

        this.drawBackground(g2d, frameW, frameH);
        this.drawContent(g2d, frameW, frameH);

        if (this.model.getIsFlipped()) {
            if (this.currentStroke != null) {
                this.drawStroke(g2d, this.currentStroke);
            }
            ArrayList<Stroke> strokes = this.model.getStrokes();
            for (Stroke stroke : strokes) {
                this.drawStroke(g2d, stroke);
            }

            if(this.currentTextBlock != null) {
                this.drawTextBlock(g2d, this.currentTextBlock);
            }
            for(TextBlock textBlock : this.model.getTextBlocks()) {
                this.drawTextBlock(g2d, textBlock);
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
        for(String word : words) {
            int wordWidth = metrics.stringWidth(word + " ");

            if (x != pos.x && (x + wordWidth > this.getWidth())) {
                x = pos.x;
                y += metrics.getAscent() + metrics.getDescent() + metrics.getLeading();
            }
            g2d.drawString(word, x, y);
            x += wordWidth;
        }
    }
}
