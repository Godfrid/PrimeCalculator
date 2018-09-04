package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Ui extends JFrame {

    private JLabel resultBar;
    private JPanel root;
    private Dimension preferedSize = new Dimension(800, 600);
    private Dimension maxSize = new Dimension(1240, 900);
    private Dimension minSize = new Dimension(648, 480);

    public Ui() {

        setTitle("Prime tester");
        setPreferredSize(preferedSize);
        setMaximumSize(maxSize);
        setMinimumSize(minSize);
        setSize(preferedSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);


        root = new JPanel();
        root.setLayout(null);
        root.setBackground(Color.BLACK);
        root.setOpaque(true);
        this.add(root);

        resultBar = new JLabel();
        resultBar.setText("kek");
        resultBar.setBounds(0, 440, 250, 20);
        resultBar.setBackground(Color.RED);
        resultBar.setOpaque(true);
        root.add(resultBar);

        setVisible(true);
    }

    private void resultBar() {
        JLabel resultBar = new JLabel();
        resultBar.setText("kek");
        resultBar.setLocation(480, 10);
        resultBar.setSize(50, 10);
        resultBar.setBackground(Color.RED);
        resultBar.setOpaque(true);
        this.add(resultBar);
    }

}
