package ui;

import logic.TestType;
import logic.test.AKS;
import logic.test.MillerRabin;
import logic.test.Test;
import logic.test.TrialDivision.TDThreadHandler.TDThreadHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

import static java.awt.Color.*;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
// runnable?
public class UI implements UIEventManager{

    private final Font inputFont = new Font(Font.SANS_SERIF, Font.BOLD, 22);
    private final Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 22);
    final BigInteger longMax = new BigInteger("9223372036854775807");
    private Color backItem;
    private Color foreItem;
    private long runTime = 0;
    private boolean lightScheme = true;

    public JFrame primeTesterUI;
    private JPanel mainPanel;

    private JLabel inputLabel;
    private JTextField inputTextField;

    private JLabel testType;
    private JComboBox engineSelector;

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

    public UI() {
        if (lightScheme) {
            backItem = Color.lightGray;
            foreItem = Color.white;
        } else {
            backItem = Color.darkGray;
            foreItem = Color.lightGray;
        }

        primeTesterUI = new JFrame();
        primeTesterUI.setSize(750, 400);
        primeTesterUI.setLocationRelativeTo(null);
        primeTesterUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        primeTesterUI.setResizable(false);
        primeTesterUI.setTitle("Prime test");

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(backItem);
        primeTesterUI.add(mainPanel);

        inputLabel = new JLabel("Number:");
        inputLabel.setBounds(20, 20, 120, 40);
        inputLabel.setForeground(Color.black);
        inputLabel.setFont(buttonFont);
        inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputLabel.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(inputLabel);

        inputTextField = new JTextField();
        inputTextField.setBounds(180, 20, 470, 40);
        inputTextField.setBackground(foreItem);
        inputTextField.setForeground(black);
        inputTextField.setOpaque(true);
        inputTextField.setFont(inputFont);
        inputTextField.setBorder(null);
        inputTextField.setText("9223372036854775783");
        inputTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                isPrimeReset();
            }
            public void removeUpdate(DocumentEvent e) {
                isPrimeReset();
            }
            public void insertUpdate(DocumentEvent e) {
                isPrimeReset();
            }
        });
        mainPanel.add(inputTextField);

        testType = new JLabel("Test type:");
        testType.setBounds(20, 100, 120, 40);
        testType.setHorizontalAlignment(SwingConstants.CENTER);
        testType.setVerticalAlignment(SwingConstants.CENTER);
        testType.setForeground(Color.black);
        testType.setFont(buttonFont);
        mainPanel.add(testType);

        engineSelector = new JComboBox(TestType.values());
        engineSelector.setSelectedIndex(0);
        engineSelector.setBounds(180, 100, 200, 40);
        engineSelector.setOpaque(true);
        engineSelector.setFont(inputFont);
        engineSelector.setBackground(foreItem);
        engineSelector.setForeground(Color.black);
        engineSelector.addActionListener(e -> {
            isPrimeReset();
            onTDSelect();
        });
        mainPanel.add(engineSelector);

        numOfCores = new JLabel("# of Threads:");
        numOfCores.setBounds(450, 100, 180, 40);
        numOfCores.setFont(buttonFont);
        numOfCores.setVisible(false);
        numOfCores.setForeground(black);
        mainPanel.add(numOfCores);

        coreSelector = new JComboBox(NUMBER_OF_CORES);
        coreSelector.setBounds(620, 100, 60, 40);
        coreSelector.setVisible(false);
        coreSelector.setFont(inputFont);
        coreSelector.setBackground(foreItem);
        coreSelector.setForeground(Color.black);
        coreSelector.addActionListener(e -> isPrimeReset());
        mainPanel.add(coreSelector);

        isPrime = new JLabel("RESULT");
        isPrime.setBounds(20, 180, 200, 40);
        isPrime.setFont(buttonFont);
        isPrime.setHorizontalAlignment(SwingConstants.CENTER);
        isPrime.setVerticalAlignment(SwingConstants.CENTER);
        isPrime.setBackground(foreItem);
        isPrime.setForeground(Color.black);
        isPrime.setOpaque(true);
        mainPanel.add(isPrime);

        runTimeLabel = new JLabel("Runtime:");
        runTimeLabel.setBounds(260, 180, 120, 40);
        runTimeLabel.setFont(buttonFont);
        runTimeLabel.setForeground(Color.black);
        mainPanel.add(runTimeLabel);

        runTimeField = new JLabel(runTime + " ms");
        runTimeField.setBounds(400, 180, 200, 40);
        runTimeField.setForeground(Color.black);
        runTimeField.setFont(buttonFont);
        runTimeField.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(runTimeField);

        startButton = new JButton("TEST");
        startButton.setBounds(20, 250, 160, 60);
        startButton.setFont(buttonFont);
        startButton.setEnabled(true);
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.setVerticalAlignment(SwingConstants.CENTER);
        startButton.setBackground(foreItem);
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
        // Init:
        disableButton();
        isPrimeReset();
        BigInteger n = new BigInteger(inputTextField.getText());
        Enum testType = TestType.values()[engineSelector.getSelectedIndex()];

        // Test test:
        EventQueue.invokeLater(() ->{
            if (testType == TestType.TRIAL_DIVISION) {
                if (0 > n.compareTo(longMax)) {
                    startTest(new TDThreadHandler(n.longValue(), 0));
                }
                //TODO: Handle longer than long input.
            }

            if (testType == TestType.MILLER_RABIN) {
                startTest(new MillerRabin(n));
            }

            if (testType == TestType.CUSTOM) {
                if (0 > n.compareTo(longMax)) {
                    startTest(new TDThreadHandler(n.longValue(), coreSelector.getSelectedIndex() + 1));
                }
            }

            if (testType == TestType.AKS) {
                startTest(new AKS(n));
            }
        });

    }

    private void disableButton() {
            startButton.setEnabled(false);
    }

    private void enableButton() {
            startButton.setEnabled(true);
    }

    private void showResult(Test tester) {
        if (tester.isPrime()) {
            isPrime.setBackground(Color.green);
            isPrime.setText("IS A PRIME");
        } else {
            isPrime.setBackground(Color.red);
            isPrime.setText("NOT A PRIME");
        }
        runTime = System.currentTimeMillis() - runTime;
        runTimeField.setText(runTime + " ms");
    }

    private void startTest(Test tester) {
        runTime = System.currentTimeMillis();
        tester.test();
        showResult(tester);
        enableButton();
    }

    private void isPrimeReset() {
        isPrime.setBackground(foreItem);
        isPrime.setText("RESULT");
        runTime = 0;
        runTimeField.setText(runTime + " ms");
    }
}
