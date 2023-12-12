package brandon.calculator;

import brandon.calculator.engine.CalculatorEngine;

import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.synth.SynthScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import static brandon.calculator.Calculator.*;

public class Display implements ActionListener {

    private JPanel mainPanel;
    private JTextArea currentLine;
    private JPanel scrollList;
    public JScrollPane scrollPane;
    private final CalculatorEngine calculatorEngine = new CalculatorEngine();

    public Display(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JScrollPane init() {

        // LIST PANEL
        scrollList = new JPanel();
        scrollList.setLayout(new BoxLayout(scrollList, BoxLayout.Y_AXIS));
        scrollList.setBackground(DARK_GREY);

        // FIRST DISPLAY LINE
        currentLine = makeNewLine("", ComponentOrientation.RIGHT_TO_LEFT);

        // SCROLL PANE
        scrollPane = new JScrollPane(scrollList);
        scrollPane.setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 400));

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.trackColor = DARK_GREY;
                this.thumbColor = GREY;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        return scrollPane;
    }


    /*
        "DEC",   "BIN",   "HEX",   "(",      ")",     "c",     "+/-",    "/"
        "LSHFT", "RSHFT", "OR",    "XOR",    "AND",   "7",     "8",      "9",      "x"
        "√x",    "x²",    "xⁿ",    "eⁿ",     "10ⁿ",   "4",     "5",      "6",      "-"
        "x!",    "ln",    "log₁₀", "logₙ",    "EE",    "1",     "2",     "3",       "+"
        "𝐆",     "ℎ",     "𝛟",     "e",      "𝞹",     "0",     ".",      "="
        "Rand",  "Avo",   "𝑐",      "sin",    "cos",   "tan",   "sinh",   "cosh",   "tanh"
        "Rad",   "Per",   "%",     "sin⁻¹",  "cos⁻¹", "tan⁻¹", "sinh⁻¹", "cosh⁻¹", "tanh⁻¹"
     */


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();

        switch (buttonText) {
            case "=" -> {
                BigDecimal result = calculateResult();
                if (result != null) {
                    makeNewLine(result.toString(), ComponentOrientation.LEFT_TO_RIGHT);
                    currentLine = makeNewLine("", ComponentOrientation.RIGHT_TO_LEFT);
                    SwingUtilities.invokeLater(() -> {
                        scrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE); // Scroll to the bottom
                    });
                }
                else {
                    currentLine.setText("Error");
                }

            }
            case "c" -> {
                Component[] components = scrollList.getComponents();
                while (components.length > 0) {
                    scrollList.remove(components[components.length - 1]);
                    scrollList.revalidate();
                    scrollList.repaint();
                    components = scrollList.getComponents();
                }
                currentLine = makeNewLine("", ComponentOrientation.RIGHT_TO_LEFT);

                // return this to the calculator
            }
            default -> currentLine.setText(currentLine.getText() + buttonText);
        }
    }

    private BigDecimal calculateResult() {
        try {
            String[] lines = currentLine.getText().split("\n");
            String expression = lines[lines.length -1];
            return evaluateExpression(expression);



//            display.setText(result.toString());
//            display.setText("this is a test\nthis is a test\nthis is a test\nthis is a test\n");
        } catch (ArithmeticException | NumberFormatException ex) {
            return null;
        }
    }

    private JTextArea makeNewLine(String text, ComponentOrientation orientation) {
        JTextArea line = new JTextArea();
        line.setMaximumSize(new Dimension(580, 50));
        line.setEditable(false);
        line.setBackground(DARK_GREY);
        line.setOpaque(true);
        line.setBorder(null);
        line.setComponentOrientation(orientation);
        line.setForeground(WHITE);
        Font font = line.getFont();
        line.setFont(new Font(font.getName(), font.getStyle(), 40));
        line.getCaret().deinstall(line);

        line.setText(text);


        scrollList.add(line);
        scrollList.revalidate();
        scrollList.repaint();

        return line;
    }

    private BigDecimal evaluateExpression(String expression) {
        return calculatorEngine.evaluate(expression);
    }

}
