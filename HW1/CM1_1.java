import javax.swing.*;
import java.awt.*;

public class CM1_1 {
    private JFrame frame;

    public CM1_1() {
        this.frame = new JFrame("Touch ID");
        this.frame.setSize(750, 410);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(new BorderLayout());

        this.frame.add(this.createToolBar(), BorderLayout.NORTH);
        this.frame.add(new LeftSection(), BorderLayout.WEST);
        this.frame.add(new RightSection(), BorderLayout.CENTER);

        this.frame.setVisible(true);
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
        toolBar.setPreferredSize(new Dimension(5, 60));

        JButton backButton = this.iosButton("<", false);
        JButton forwardButton = this.iosButton(">", true);
        JButton extraButton = this.iosButton("☰", false);

        Dimension buttonSize = new Dimension(36, 36);
        backButton.setPreferredSize(buttonSize);
        backButton.setMinimumSize(buttonSize);
        backButton.setMaximumSize(buttonSize);

        forwardButton.setPreferredSize(buttonSize);
        forwardButton.setMinimumSize(buttonSize);
        forwardButton.setMaximumSize(buttonSize);

        extraButton.setPreferredSize(buttonSize);
        extraButton.setMinimumSize(buttonSize);
        extraButton.setMaximumSize(buttonSize);

        JTextField searchBar = new PlaceholderTextField("Rechercher");
        searchBar.setBorder(new RoundedBorder(10));
        searchBar.setFont(new Font("San Francisco", Font.PLAIN, 16));

        Dimension searchBarSize = new Dimension(320, 36);
        searchBar.setPreferredSize(searchBarSize);
        searchBar.setMinimumSize(searchBarSize);
        searchBar.setMaximumSize(searchBarSize);

        toolBar.add(Box.createRigidArea(new Dimension(12, 0)));
        toolBar.add(backButton);
        toolBar.add(Box.createRigidArea(new Dimension(2, 0)));
        toolBar.add(forwardButton);
        toolBar.add(Box.createRigidArea(new Dimension(12, 0)));
        toolBar.add(extraButton);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(searchBar);
        toolBar.add(Box.createRigidArea(new Dimension(12, 0)));

        return toolBar;
    }

    private JButton iosButton(String text, boolean disabled) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(54, 44));
        button.setBorder(new RoundedBorder(10));
        button.setFont(new Font("San Francisco", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(new Color(238, 238, 238));
        if(disabled) {
            button.setForeground(Color.LIGHT_GRAY);
        }else {
            button.setForeground(Color.DARK_GRAY);
        }
        return button;
    }


}
