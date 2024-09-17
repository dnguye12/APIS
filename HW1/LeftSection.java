import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LeftSection extends JPanel {
    private BufferedImage fingerPrint;

    public LeftSection() {
        try {
            fingerPrint = ImageIO.read(new File("src/img/fingerPrint.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(150, 300));
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int r = 110;
        int p = 20;

        Ellipse2D.Double circle = new Ellipse2D.Double(p, p, r,r);
        g2d.setColor(Color.WHITE);
        g2d.fill(circle);

        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(circle);

        if (this.fingerPrint != null) {
            int imageSize = 90; // Image size inside the circle
            int imageX = p + (r - imageSize) / 2;
            int imageY = p + (r - imageSize) / 2;
            g2d.drawImage(this.fingerPrint, imageX, imageY, imageSize, imageSize, this);
        }
    }
}
