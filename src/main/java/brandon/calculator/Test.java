package brandon.calculator;

import javax.swing.*;
import java.awt.*;

public class Test {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calculator Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextArea displayArea = new JTextArea();
            displayArea.setEditable(false);
            displayArea.setLineWrap(true);
            displayArea.setWrapStyleWord(true);
            displayArea.setBackground(Color.DARK_GRAY);
            displayArea.setForeground(Color.WHITE);

            JScrollPane scrollPane = new JScrollPane(displayArea);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            // Create a panel for the layout
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            frame.getContentPane().add(panel);
            frame.setSize(400, 300);
            frame.setVisible(true);

            // Example: Append some input and answers
            displayArea.append("Input: 10 + 5\n");
            displayArea.append("Answer: 15\n");

            displayArea.append("Input: 20 - 8\n");
            displayArea.append("Answer: 12\n");

            // ... continue appending as needed
        });
    }
}
