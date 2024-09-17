import javax.swing.*;
import java.awt.*;

public class RightSection extends JPanel {
    private final int PADDING = 10;

    public RightSection() {
        this.setPreferredSize(new Dimension(600, 300));
        this.setLayout(new BorderLayout());

        JTextArea text1 = new JTextArea(
                "Touch ID vous permet d'utiliser votre empreinte digitale pour déverrouiller votre Mac et effectuer des achats avec Apple Pay, l'iTunes Store, l'App Store et l'Apple Books.");
        text1.setLineWrap(true);
        text1.setWrapStyleWord(true);
        text1.setEditable(false);
        text1.setBackground(Color.LIGHT_GRAY);
        text1.setFont(new Font("San Francisco", Font.PLAIN, 14));
        text1.setBorder(BorderFactory.createEmptyBorder(this.PADDING, this.PADDING, this.PADDING, this.PADDING));
        text1.setBackground(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(text1);
        scrollPane.setPreferredSize(new Dimension(560, 60));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel iconsHolder = new JPanel();
        iconsHolder.setPreferredSize(new Dimension(560, 120));
        iconsHolder.setLayout(new FlowLayout(FlowLayout.LEFT, this.PADDING * 4, this.PADDING));
        iconsHolder.setBackground(Color.LIGHT_GRAY);

        ImageIcon icon1 = new ImageIcon(new ImageIcon("src/img/fingerPrintBlack.png").getImage());
        JLabel imageLabel1 = new JLabel(icon1);
        JLabel textLabel1 = new JLabel("Doight 1");
        textLabel1.setFont(new Font("San Francisco", Font.PLAIN, 14));
        JPanel imageContainer1 = new JPanel();
        imageContainer1.setLayout(new BoxLayout(imageContainer1, BoxLayout.Y_AXIS));
        imageLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        textLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageContainer1.add(imageLabel1);
        imageContainer1.add(Box.createVerticalStrut(this.PADDING));
        imageContainer1.add(textLabel1);
        imageContainer1.setBackground(Color.LIGHT_GRAY);

        ImageIcon icon2 = new ImageIcon(new ImageIcon("src/img/fingerPrintAdd.png").getImage());
        JLabel imageLabel2 = new JLabel(icon2);
        JLabel textLabel2 = new JLabel("Ajouter une empreinte");
        textLabel2.setFont(new Font("San Francisco", Font.PLAIN, 14));
        JPanel imageContainer2 = new JPanel();
        imageContainer2.setLayout(new BoxLayout(imageContainer2, BoxLayout.Y_AXIS));
        imageLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        textLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageContainer2.add(imageLabel2);
        imageContainer2.add(Box.createVerticalStrut(this.PADDING));
        imageContainer2.add(textLabel2);
        imageContainer2.setBackground(Color.LIGHT_GRAY);

        iconsHolder.add(imageContainer1);
        iconsHolder.add(imageContainer2);

        JPanel listHolder = new JPanel();
        listHolder.setPreferredSize(new Dimension(560, 120));
        listHolder.setLayout(new BoxLayout(listHolder ,BoxLayout.Y_AXIS));
        listHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        listHolder.setBorder(BorderFactory.createEmptyBorder(0, this.PADDING, this.PADDING, this.PADDING));
        listHolder.setBackground(Color.LIGHT_GRAY);

        JLabel listText = new JLabel("Utiliser Touch ID pour : ");
        listText.setAlignmentX(Component.LEFT_ALIGNMENT);
        listHolder.add(listText);
        String[] options = {
                "Déverrouiller votre Mac",
                "Apple Pay",
                "iTunes Store, App Store et Apple Books",
                "Remplissage automatique des mots de passe"
        };
        ButtonGroup buttonGroup = new ButtonGroup();
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setSelected(true);
            buttonGroup.add(radioButton);
            radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            radioButton.setBackground(Color.LIGHT_GRAY);
            listHolder.add(radioButton);
        }

        JPanel center = new JPanel();
        center.add(iconsHolder);
        center.add(listHolder);
        center.setBackground(Color.LIGHT_GRAY);

        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(scrollPane, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
    }
}
