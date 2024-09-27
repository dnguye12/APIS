import controller.PhotoController;
import model.PhotoModel;
import model.PhotoSaveLoad;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Objects;

//Application from Lab1, integrated with Lab2
public class MainApp {
    //Dimension of the menu bar, used for size calculations
    private final Dimension MENUDIM = new Dimension(72, 23);
    //Dimension of left hand toolbar
    private final Dimension TOOLBARDIM = new Dimension(55, 103);
    //Padding cuz CSS > Java Swing
    private final int PADDING = 20;
    private JFrame frame;
    private JLabel status;
    private boolean pressedPeopleBtn, pressedPlacesBtn, pressedSchoolBtn;
    private PhotoController con;
    private JScrollPane scrollPane;

    public MainApp() {
        this.frame = new JFrame("Skeleton");
        this.frame.setPreferredSize(new Dimension(420, 420));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.setLayout(new BorderLayout());

        // Add Menu Bar
        this.frame.setJMenuBar(createMenuBar());

        // Main content area panel, containing the area and all the drawing
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

    //Set the size of the application depending on the size of the image
    //ALso add some paddings around
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

        JMenu fileMenu = this.createFileMenu();
        JMenu viewMenu = this.createViewMenu();
        JMenu shapeMenu = this.createShapeMenu();
        JMenu drawOptionsMenu = this.createDrawOptionsMenu();

        menu.add(fileMenu);
        menu.add(viewMenu);
        menu.add(shapeMenu);
        menu.add(drawOptionsMenu);

        return menu;
    }

    //File menu with import, load, save, delete and quit.
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        //Import a new image to main panel. Create a new photo controller, model and view for this
        JMenuItem btnImport = new JMenuItem("Import");
        btnImport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("src/Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("jpg", "png", "gif", "jpeg"));
            int helper = fileChooser.showOpenDialog(frame);

            if (helper == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getPath();
                this.con = new PhotoController(filePath);
                this.con.getPhotoModel().setImage(filePath);
                this.scrollPane.setViewportView(this.con.getPhotoView());
                this.handleSize(true);
                this.handleSize(true);
                this.scrollPane.revalidate();
                this.scrollPane.repaint();
            }
        });

        //Save a photo to a .data file. Obsolete cause it is already auto save.
        JMenuItem btnSave = new JMenuItem("Save");
        btnSave.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("src/Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("data", "data"));
            int helper = fileChooser.showSaveDialog(frame);
            if (helper == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".data")) {
                    filePath += ".data";
                }

                PhotoSaveLoad.savePhotoModel(filePath, this.con.getPhotoModel());
            }
        });

        //Load a .data file into a photo.
        JMenuItem btnLoad = new JMenuItem("Load");
        btnLoad.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("src/Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("data", "data"));
            int helper = fileChooser.showOpenDialog(frame);
            if(helper == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String helper1 = selectedFile.getAbsolutePath();
                PhotoModel model = PhotoSaveLoad.loadPhotoModel(helper1);
                this.con = new PhotoController(model, helper1);
                this.scrollPane.setViewportView(this.con.getPhotoView());
                this.handleSize(true);
                this.scrollPane.revalidate();
                this.scrollPane.repaint();
            }
        });

        //Remove the photo for the application.
        JMenuItem btnDelete = new JMenuItem("Delete");
        btnDelete.addActionListener(e -> {
            this.con.getPhotoModel().setImage("");
            this.handleSize(true);
        });

        JMenuItem btnQuit = new JMenuItem("Quit");
        btnQuit.addActionListener(e -> System.exit(0));

        fileMenu.add(btnImport);
        fileMenu.add(btnSave);
        fileMenu.add(btnLoad);
        fileMenu.add(btnDelete);
        fileMenu.add(btnQuit);

        return fileMenu;
    }

    //Part 1
    private JMenu createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        JRadioButtonMenuItem photoViewerBtn = new JRadioButtonMenuItem("Photo Browser", true);
        JRadioButtonMenuItem browserBtn = new JRadioButtonMenuItem("Browser");

        ButtonGroup viewMenuBtnGroup = new ButtonGroup();
        viewMenuBtnGroup.add(photoViewerBtn);
        viewMenuBtnGroup.add(browserBtn);

        viewMenu.add(photoViewerBtn);
        viewMenu.add(browserBtn);
        return viewMenu;
    }

    //User can choose between 3 drawing shapes: Stroke, rectangles and ellipse
    private JMenu createShapeMenu() {
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
        return shapeMenu;
    }

    //Pen color, pen stroke size, font size and font family
    //Can combine font size and font family into one but i want to test openNumberSelector() for a more reusable solution.
    private JMenu createDrawOptionsMenu() {
        JMenu drawOptionsMenu = new JMenu("Draw Options");

        JMenuItem chooseColorItem = new JMenuItem("Choose Color");
        chooseColorItem.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(frame, "Choose a Color", Color.BLACK);
            if (selectedColor != null) {
                con.getPhotoModel().setCurrentColor(selectedColor);
            }
        });

        JMenuItem chooseStrokeSizeItem = new JMenuItem("Choose Stroke Size");
        chooseStrokeSizeItem.addActionListener(e -> {
            int strokeSize = this.openNumberSelector(frame, "stroke");
            //strokeSize == 0 <=> the user clicked on Cancel Button in openNumberSelector() dialog
            if (strokeSize != 0) {
                this.con.getPhotoModel().setCurrentStrokeSize(strokeSize);
            }
        });

        JMenuItem chooseFontSizeItem = new JMenuItem("Choose Font Size");
        chooseFontSizeItem.addActionListener(e -> {
            int fontSize = this.openNumberSelector(frame, "font");
            //fontSize == 0 <=> the user clicked on Cancel Button in openNumberSelector() dialog
            if (fontSize != 0) {
                con.getPhotoModel().setCurrentFontSize(fontSize);
            }
        });

        JMenuItem chooseFontFamilyItem = new JMenuItem("Choose Font Family");
        chooseFontFamilyItem.addActionListener(e -> {
            Font font = this.openFontSelector(frame);
            if (font != null) {
                con.getPhotoModel().setCurrentFont(font);
            }
        });

        drawOptionsMenu.add(chooseColorItem);
        drawOptionsMenu.add(chooseStrokeSizeItem);
        drawOptionsMenu.add(chooseFontSizeItem);
        drawOptionsMenu.add(chooseFontFamilyItem);

        return drawOptionsMenu;
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

    //Image section. User scroll pane for more responsiveness.
    private JPanel createMainPortion() {
        JPanel mainPortion = new JPanel();
        mainPortion.setLayout(new GridBagLayout());
        this.con = new PhotoController("");
        this.scrollPane = new JScrollPane(this.con.getPhotoView());
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

    //A dialog with a number slider and 2 buttons: Ok and cancel.
    //CLicking on OK returns the selected number
    //Cancel does nothing.
    private int openNumberSelector(JFrame frame, String mode) {
        //Have to an int array because variable used in lambda expression should be final or effectively final
        final int[] strokeSize = {2};
        JDialog dialog = new JDialog(frame, "Select Pen model.Stroke Size", true);
        JSlider strokeSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, strokeSize[0]);
        if (Objects.equals(mode, "font")) {
            strokeSize[0] = 14;
            dialog.setTitle("Select Text Font Size");
            strokeSizeSlider.setMinimum(11);
            strokeSizeSlider.setMaximum(31);
        } else {
            strokeSize[0] = 2;
        }
        dialog.setSize(400, 200);
        dialog.setLayout(new BorderLayout());

        strokeSizeSlider.setMajorTickSpacing(2);
        strokeSizeSlider.setMinorTickSpacing(1);
        strokeSizeSlider.setPaintTicks(true);
        strokeSizeSlider.setPaintLabels(true);

        dialog.add(strokeSizeSlider, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        //This is why im using array int
        okButton.addActionListener(e -> {
            strokeSize[0] = strokeSizeSlider.getValue(); //Can't get int directly
            dialog.dispose();
        });

        cancelButton.addActionListener(e ->
        {
            strokeSize[0] = 0;
            dialog.dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        return strokeSize[0];
    }

    //Font selector dialog
    private Font openFontSelector(JFrame frame) {
        //Get all the fon of your computer. YOINK!!
        String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        JDialog dialog = new JDialog(frame, "Select Font", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new BorderLayout());

        //Combo box supremacy
        JComboBox<String> fontFamilyComboBox = new JComboBox<>(fontFamilies);
        fontFamilyComboBox.setSelectedItem("Serif");

        // 2 checkboxes BOLD and ITALIC. If the user selects none of them then the style will be PLAIN
        JPanel stylePanel = new JPanel();
        JCheckBox boldCheckBox = new JCheckBox("Bold");
        JCheckBox italicCheckBox = new JCheckBox("Italic");
        stylePanel.add(boldCheckBox);
        stylePanel.add(italicCheckBox);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Font Family:"), BorderLayout.WEST);
        topPanel.add(fontFamilyComboBox, BorderLayout.CENTER);

        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(stylePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        //Array Font cause using lambda expression below
        final Font[] selectedFont = {null};

        okButton.addActionListener(e -> {
            String selectedFamily = (String) fontFamilyComboBox.getSelectedItem();
            int style = Font.PLAIN;
            if (boldCheckBox.isSelected()) {
                style = Font.BOLD;
            }
            if (italicCheckBox.isSelected()) {
                style = Font.ITALIC;
            }
            selectedFont[0] = new Font(selectedFamily, style, con.getPhotoModel().getCurrentFontSize());
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        return selectedFont[0];
    }
}
