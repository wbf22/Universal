package brandon.calculator;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class Test {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom ScrollBar Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            JTextArea textArea = new JTextArea(5, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

            frame.add(scrollPane);
            frame.setVisible(true);
        });
    }

    static class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            thumbColor = Color.BLUE;
            trackColor = Color.BLACK;

        }
    }
}