package brandon.calculator;

import brandon.calculator.engine.CalculatorEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import static brandon.calculator.Calculator.DARK_GREY;

public class CalculatorButtonAction implements ActionListener {
    private JTextArea display;
    private final JScrollPane scrollPane;
    private final CalculatorEngine calculatorEngine = new CalculatorEngine();

    public CalculatorButtonAction(JScrollPane scrollPane) {


        // DISPLAY
        display = new JTextArea();
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setBackground(DARK_GREY);
        display.setOpaque(true);
        display.setBorder(null);
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display.setForeground(Color.WHITE);
        Font font = display.getFont();
        display.setFont(new Font(font.getName(), font.getStyle(), 40));


        this.scrollPane = scrollPane;
        this.scrollPane.add(display);
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
            case "=" -> calculateResult();
            case "c" -> display.setText("");
            default -> display.setText(display.getText() + buttonText);
        }
    }

    private void calculateResult() {
        try {
            String[] lines = display.getText().split("\n");
            String expression = lines[lines.length -1];
            BigDecimal result = evaluateExpression(expression);

            display.setText(display.getText() + "\n" + result + "\n");
            SwingUtilities.invokeLater(() -> {
                scrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE); // Scroll to the bottom
            });

//            display.setText(result.toString());
//            display.setText("this is a test\nthis is a test\nthis is a test\nthis is a test\n");
        } catch (ArithmeticException | NumberFormatException ex) {
            display.setText("Error");
        }
    }

    private BigDecimal evaluateExpression(String expression) {
        return calculatorEngine.evaluate(expression);
    }

}
