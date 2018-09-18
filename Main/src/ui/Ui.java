package ui;

import logic.test.MillerRabin;
import logic.test.TrialDivision.TDThreadHandler.TDThreadHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.awt.Color.*;
import static java.lang.Thread.sleep;

public class Ui implements UIEventManager{
    private final Font inputFont = new Font(Font.SANS_SERIF, Font.BOLD, 13);
    private final Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private long runTime = 0;
    public AtomicBoolean buttonIsEnabled = new AtomicBoolean(true);

    private JFrame primeTesterUI;
    private JPanel mainPanel;

    private JLabel inputLabel;
    private JTextField inputTextField;

    private JLabel testType;
    private JComboBox engineSelector;
    private final String[] TEST_TYPES = {"Trial division", "Miller-Rabin", "Costume TD", "AKS"};

    private JLabel numOfCores;
    private JComboBox coreSelector;
    private final String[] NUMBER_OF_CORES = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    private JLabel isPrime;
    private JLabel runTimeLabel;
    private JLabel runTimeField;

    // private JProgressBar progressBar;
    // private JLabel LOG

    private JButton startButton;
    // private JButton stopButton;

    public Ui() {
        primeTesterUI = new JFrame();
        primeTesterUI.setSize(800, 600);
        primeTesterUI.setLocationRelativeTo(null);
        primeTesterUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        primeTesterUI.setResizable(false);
        primeTesterUI.setTitle("Prime test");

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.darkGray);
        primeTesterUI.add(mainPanel);

        inputLabel = new JLabel("Number:");
        inputLabel.setBounds(20, 20, 90, 20);
        inputLabel.setForeground(Color.black);
        inputLabel.setFont(buttonFont);
        mainPanel.add(inputLabel);

        inputTextField = new JTextField();
        inputTextField.setBounds(115, 20, 600, 20);
        inputTextField.setBackground(Color.lightGray);
        inputTextField.setOpaque(true);
        inputTextField.setFont(inputFont);
        inputTextField.setBorder(null);
        mainPanel.add(inputTextField);

        testType = new JLabel("Test type:");
        testType.setBounds(20, 50, 90, 20);
        testType.setForeground(Color.black);
        testType.setFont(buttonFont);
        mainPanel.add(testType);

        engineSelector = new JComboBox(TEST_TYPES);
        engineSelector.setSelectedIndex(1);
        engineSelector.setBounds(115, 50, 120, 20);
        engineSelector.setOpaque(true);
        engineSelector.setFont(inputFont);
        engineSelector.setBackground(Color.lightGray);
        engineSelector.setForeground(Color.black);
        engineSelector.addActionListener(e -> {
            onTDSelect();
        });
        mainPanel.add(engineSelector);

        numOfCores = new JLabel("# of Threads:");
        numOfCores.setBounds(260, 50, 120, 20);
        numOfCores.setFont(buttonFont);
        numOfCores.setVisible(false);
        numOfCores.setForeground(black);
        mainPanel.add(numOfCores);

        coreSelector = new JComboBox(NUMBER_OF_CORES);
        coreSelector.setBounds(385, 50, 40, 20);
        coreSelector.setVisible(false);
        coreSelector.setFont(inputFont);
        coreSelector.setBackground(Color.lightGray);
        coreSelector.setForeground(Color.black);
        mainPanel.add(coreSelector);

        isPrime = new JLabel("RESULT");
        isPrime.setBounds(20, 100, 120, 20);
        isPrime.setFont(buttonFont);
        isPrime.setHorizontalAlignment(SwingConstants.CENTER);
        isPrime.setVerticalAlignment(SwingConstants.CENTER);
        isPrime.setBackground(Color.lightGray);
        isPrime.setForeground(Color.black);
        isPrime.setOpaque(true);
        mainPanel.add(isPrime);

        runTimeLabel = new JLabel("Runtime:");
        runTimeLabel.setBounds(150, 100, 80, 20);
        runTimeLabel.setFont(buttonFont);
        runTimeLabel.setForeground(Color.black);
        mainPanel.add(runTimeLabel);

        runTimeField = new JLabel(runTime + " ms");
        runTimeField.setBounds(235, 100, 100, 20);
        runTimeField.setForeground(Color.black);
        runTimeField.setFont(buttonFont);
        runTimeField.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(runTimeField);

        //

        startButton = new JButton("TEST");
        startButton.setBounds(20, 500, 80, 40);
        startButton.setFont(buttonFont);
        startButton.setEnabled(true);
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.setVerticalAlignment(SwingConstants.CENTER);
        startButton.setBackground(lightGray);
        startButton.setForeground(black);
        startButton.addActionListener(e -> {
            onStart();
        });
        mainPanel.add(startButton);

        primeTesterUI.setVisible(true);
    }


    private void onTDSelect() {
        if (engineSelector.getSelectedIndex() == 2) {
            coreSelector.setVisible(true);
            numOfCores.setVisible(true);
        } else {
            coreSelector.setVisible(false);
            numOfCores.setVisible(false);
        }
    }

    @Override
    public void onStart() {
        synchronized (this) {
            // Init:
            startButton.setEnabled(false);
            BigInteger n = new BigInteger(inputTextField.getText());
            int engine = engineSelector.getSelectedIndex();
/*            primeTesterUI.revalidate();
            primeTesterUI.repaint();*/
            // Test start:
            if (engine == 0) {
                TDThreadHandler tester = new TDThreadHandler(n.longValue(), 0);
                runTime = System.currentTimeMillis();
                tester.start();

                while (!tester.isFinished()) {
                }

                if (tester.isPrime()) {
                    isPrime.setBackground(Color.green);
                    isPrime.setText("IS A PRIME");
                } else {
                    isPrime.setBackground(Color.red);
                    isPrime.setText("NOT A PRIME");
                }
            }

            if (engine == 1) {
                MillerRabin tester = new MillerRabin(n);
                runTime = System.currentTimeMillis();
                tester.test();

                while (!tester.isFinished()) {
                }

                if (tester.isPrime()) {
                    isPrime.setBackground(Color.green);
                    isPrime.setText("IS A PRIME");
                } else {
                    isPrime.setBackground(Color.red);
                    isPrime.setText("NOT A PRIME");
                }
            }

            // On finish:
            runTime = System.currentTimeMillis() - runTime;
            runTimeField.setText(runTime + " ms");
            startButton.setEnabled(true);
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void stop() {

    }
}
