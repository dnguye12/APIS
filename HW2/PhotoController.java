import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PhotoController {
    private PhotoModel photoModel;
    private PhotoView photoView;
    private JFrame frame;

    public PhotoController(String path) {
        this.photoModel = new PhotoModel(path);
        this.photoView = new PhotoView(this.photoModel);

        JScrollPane scrollPane = new JScrollPane(this.photoView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.frame = new JFrame("Photo display");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(scrollPane);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }
}
