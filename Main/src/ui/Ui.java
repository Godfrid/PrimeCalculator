package ui;

import logic.TestType;
import logic.test.AKS;
import logic.test.MillerRabin;
import logic.test.Test;
import logic.test.TrialDivision.TDThreadHandler.TDThreadHandler;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

import static java.awt.Color.*;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
// runnable?
public class Ui implements UIEventManager{

    private final Font inputFont = new Font(Font.SANS_SERIF, Font.BOLD, 13);
    private final Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
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

    public Ui() {
        if (lightScheme) {
            backItem = Color.lightGray;
            foreItem = Color.white;
        } else {
            backItem = Color.darkGray;
            foreItem = Color.lightGray;
        }

        primeTesterUI = new JFrame();
        primeTesterUI.setSize(800, 600);
        primeTesterUI.setLocationRelativeTo(null);
        primeTesterUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        primeTesterUI.setResizable(false);
        primeTesterUI.setTitle("Prime test");

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(backItem);
        primeTesterUI.add(mainPanel);

        inputLabel = new JLabel("Number:");
        inputLabel.setBounds(20, 20, 90, 20);
        inputLabel.setForeground(Color.black);
        inputLabel.setFont(buttonFont);
        mainPanel.add(inputLabel);

        inputTextField = new JTextField();
        inputTextField.setBounds(120, 20, 600, 20);
        inputTextField.setBackground(foreItem);
        inputTextField.setOpaque(true);
        inputTextField.setFont(inputFont);
        inputTextField.setBorder(null);
        inputTextField.setText("8223331132123323353");
/*        inputTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                isPrime.setText("RESULT");
                isPrime.setBackground(foreItem);
            }
        });*/
        mainPanel.add(inputTextField);

        testType = new JLabel("Test type:");
        testType.setBounds(20, 50, 90, 20);
        testType.setForeground(Color.black);
        testType.setFont(buttonFont);
        mainPanel.add(testType);

        engineSelector = new JComboBox(TestType.values());
        engineSelector.setSelectedIndex(0);
        engineSelector.setBounds(120, 50, 120, 20);
        engineSelector.setOpaque(true);
        engineSelector.setFont(inputFont);
        engineSelector.setBackground(foreItem);
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
        coreSelector.setBackground(foreItem);
        coreSelector.setForeground(Color.black);
        mainPanel.add(coreSelector);

        isPrime = new JLabel("RESULT");
        isPrime.setBounds(20, 100, 120, 20);
        isPrime.setFont(buttonFont);
        isPrime.setHorizontalAlignment(SwingConstants.CENTER);
        isPrime.setVerticalAlignment(SwingConstants.CENTER);
        isPrime.setBackground(foreItem);
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
        //TODO: UI Repainter or methode caller thread is needed. For some actions the UI have to wait for a result, joins are needed for sync.
        // Init:
        disableButton();
        BigInteger n = new BigInteger(inputTextField.getText());
        Enum testType = TestType.values()[engineSelector.getSelectedIndex()];
/*        primeTesterUI.repaint();
        primeTesterUI.revalidate();*/
        // Test test:
        EventQueue.invokeLater(() ->{

            if (testType == TestType.TRIAL_DIVISION) {
                if (0 > n.compareTo(new BigInteger("9223372036854775807"))) {
                    startTest(new TDThreadHandler(n.longValue(), 0));
                }
            }

            if (testType == TestType.MILLER_RABIN) {
                startTest(new MillerRabin(n));

            }

            if (testType == TestType.CUSTOM) {
                if (0 > n.compareTo(new BigInteger("9223372036854775807"))) {
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
            primeTesterUI.revalidate();
            primeTesterUI.repaint();
            System.out.println("UI onStart begin thread: " + currentThread().getName());
    }

    private void enableButton() {
            System.out.println("UI This FINISHES:" + Thread.currentThread().getName());
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


    @Override
    public void reset() {

    }

    @Override
    public void stop() {

    }
}
