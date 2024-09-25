import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Part1 {
    private final Dimension MENUDIM = new Dimension(72, 23);
    private final Dimension TOOLBARDIM = new Dimension(55, 103);
    private final int PADDING = 20;
    private JFrame frame;
    private JLabel status;
    private boolean pressedPeopleBtn, pressedPlacesBtn, pressedSchoolBtn;
    private PhotoController con;
    private JScrollPane scrollPane;

    public Part1() {
        this.frame = new JFrame("Skeleton");
        this.frame.setPreferredSize(new Dimension(420, 420));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.setLayout(new BorderLayout());

        // Add Menu Bar
        this.frame.setJMenuBar(createMenuBar());

        // Main content area panel
        JPanel mainPortion = this.createMainPortion();
        this.frame.add(mainPortion, BorderLayout.CENTER);
        this.handleSize(true);

        // Status label to display button states
        this.status = new JLabel("Status");
        this.frame.add(this.status, BorderLayout.SOUTH);

        // Add toolbar to the frame
        this.frame.add(createToolBar(), BorderLayout.WEST);

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    private void handleSize(boolean changeImage) {
        if (changeImage) {
            this.con.getPhotoView().setImage(this.con.getPhotoModel().getImage());
            this.con.getPhotoView().revalidate();
            this.scrollPane.revalidate();
        }
        Dimension helper = this.con.getPhotoView().getPreferredSize();
        int width = (int) (helper.getWidth() + 4 * PADDING + this.TOOLBARDIM.width);
        int height = (int) (helper.getHeight() + 4 * PADDING + this.MENUDIM.height);

        this.frame.setPreferredSize(new Dimension(width, height));

        this.frame.revalidate();
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
    }

    // Encapsulate menu bar creation
    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem btnImport = new JMenuItem("Import");
        btnImport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("src/Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("jpg", "png", "gif", "jpeg"));
            int helper = fileChooser.showOpenDialog(frame);

            if (helper == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getPath();
                this.con.getPhotoModel().setImage(filePath);
                this.handleSize(true);
            }
        });

        JMenuItem btnDelete = new JMenuItem("Delete");
        btnDelete.addActionListener(e -> {

            this.con.getPhotoModel().setImage("");
            this.handleSize(true);

        });

        JMenuItem btnQuit = new JMenuItem("Quit");
        btnQuit.addActionListener(e -> System.exit(0));

        fileMenu.add(btnImport);
        fileMenu.add(btnDelete);
        fileMenu.add(btnQuit);

        JMenu viewMenu = new JMenu("View");
        JRadioButtonMenuItem photoViewerBtn = new JRadioButtonMenuItem("Photo Browser", true);
        JRadioButtonMenuItem browserBtn = new JRadioButtonMenuItem("Browser");

        ButtonGroup viewMenuBtnGroup = new ButtonGroup();
        viewMenuBtnGroup.add(photoViewerBtn);
        viewMenuBtnGroup.add(browserBtn);

        viewMenu.add(photoViewerBtn);
        viewMenu.add(browserBtn);

        JMenu shapeMenu = new JMenu("Shape");
        JRadioButtonMenuItem shapeStrokeBtn = new JRadioButtonMenuItem("Stroke", true);
        shapeStrokeBtn.addActionListener(e -> this.con.setPhotoModelShape(PhotoModel.Shape.STROKE));
        JRadioButtonMenuItem shapeRectangleBtn = new JRadioButtonMenuItem("Rectangle");
        shapeRectangleBtn.addActionListener(e -> this.con.setPhotoModelShape(PhotoModel.Shape.RECTANGLE));
        JRadioButtonMenuItem shapeEllipseBtn = new JRadioButtonMenuItem("Ellipse");
        shapeEllipseBtn.addActionListener(e -> this.con.setPhotoModelShape(PhotoModel.Shape.ELLIPSE));
        ButtonGroup shapeMenuBtnGroup = new ButtonGroup();
        shapeMenuBtnGroup.add(shapeStrokeBtn);
        shapeMenuBtnGroup.add(shapeRectangleBtn);
        shapeMenuBtnGroup.add(shapeEllipseBtn);
        shapeMenu.add(shapeStrokeBtn);
        shapeMenu.add(shapeRectangleBtn);
        shapeMenu.add(shapeEllipseBtn);

        JMenu colorMenu = new JMenu("Color");
        colorMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                Color selectedColor = JColorChooser.showDialog(frame, "Choose a Color", Color.BLACK);

                con.getPhotoModel().setCurrentColor(selectedColor);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        menu.add(fileMenu);
        menu.add(viewMenu);
        menu.add(shapeMenu);
        menu.add(colorMenu);

        return menu;
    }

    // Encapsulate toolbar creation
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);

        JToggleButton peopleBtn = new JToggleButton("People");
        peopleBtn.addActionListener(e -> {
            this.pressedPeopleBtn = !this.pressedPeopleBtn;
            updateStatus();
        });

        JToggleButton placesBtn = new JToggleButton("Places");
        placesBtn.addActionListener(e -> {
            this.pressedPlacesBtn = !this.pressedPlacesBtn;
            updateStatus();
        });

        JToggleButton schoolBtn = new JToggleButton("School");
        schoolBtn.addActionListener(e -> {
            this.pressedSchoolBtn = !this.pressedSchoolBtn;
            updateStatus();
        });

        toolBar.add(peopleBtn);
        toolBar.add(placesBtn);
        toolBar.add(schoolBtn);

        return toolBar;
    }

    // Update status label based on button states
    private void updateStatus() {
        StringBuilder helper = new StringBuilder("Status : ");
        if (pressedPeopleBtn) {
            helper.append(" People ");
        }
        if (pressedPlacesBtn) {
            helper.append(" Places ");
        }
        if (pressedSchoolBtn) {
            helper.append(" School ");
        }
        this.status.setText(helper.toString());
    }

    private JPanel createMainPortion() {
        JPanel mainPortion = new JPanel();
        mainPortion.setLayout(new GridBagLayout());
        this.con = new PhotoController("");
        this.scrollPane = new JScrollPane(con.getPhotoView());
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setFocusable(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPortion.add(this.scrollPane, gbc);
        return mainPortion;
    }
}
