package brandon.calculator;

import brandon.universal.util.ColorUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.plaf.synth.SynthScrollBarUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Calculator {

    public static final Color WHITE = ColorUtil.hex("#ffffff");
    public static final Color ORANGE = ColorUtil.hex("#d17d00");
    public static final Color GREY = ColorUtil.hex("#636262");
    public static final Color DARK_GREY = ColorUtil.hex("#3d3d3d");
    public static final Color VERY_DARK_GREY = ColorUtil.hex("#242424");
    public static final Color BLACK = ColorUtil.hex("#000000");
    private static final int WIDTH = 612;
    private static final Map<String, JButton> buttons = new HashMap<>();
    private static final Map<String, String> aliases = Map.of(
        "*", "x"
    );
    private static CalculatorButtonAction calculatorButtonAction;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, 883);
            frame.setBackground(DARK_GREY);
            frame.setResizable(false);

            // NUM PAD FUNCTIONALITY
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(e -> {
                keyPressed(e);
                return false;
            });

            // MAIN PANEL
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(null);
            mainPanel.setBackground(VERY_DARK_GREY);
            mainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));


            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setOpaque(false);
//            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            scrollPane.setPreferredSize(new Dimension(200, 400));
            //motif, synth

            calculatorButtonAction = new CalculatorButtonAction(scrollPane);


            // BUTTON GRID
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            buttonPanel.setBackground(DARK_GREY);
            buttonPanel.setBorder(null);

            int NW = WIDTH / 9;
            buttonPanel.add(
                makeRow(
                    new String[]{ "DEC", "BIN", "HEX", "(", ")", "c", "+/-", "/" },
                    new int[]{ NW, NW, NW, NW, NW, 2 * NW, NW, NW },
                    new Color[]{DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, ORANGE},
                    scrollPane
                )
            );
            buttonPanel.add(
                makeRow(
                    new String[]{ "LSHFT", "RSHFT", "OR", "XOR", "AND", "7", "8", "9", "x" },
                    NW,
                    new Color[]{DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, GREY, GREY, GREY, ORANGE},
                    scrollPane
                )
            );
            buttonPanel.add(
                makeRow(
                    new String[]{ "√x", "x²", "xⁿ", "eⁿ", "10ⁿ", "4", "5", "6", "-" },
                    NW,
                    new Color[]{DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, GREY, GREY, GREY, ORANGE},
                    scrollPane
                )
            );
            buttonPanel.add(
                makeRow(
                    new String[]{  "x!", "ln", "log₁₀", "logₙ", "EE", "1", "2", "3", "+" },
                    NW,
                    new Color[]{DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, GREY, GREY, GREY, ORANGE},
                    scrollPane
                )
            );
            buttonPanel.add(
                makeRow(
                    new String[]{ "𝐆", "ℎ", "𝛟", "e", "𝞹", "0", ".", "="},
                    new int[]{ NW, NW, NW, NW, NW, 2 * NW, NW, NW },
                    new Color[]{DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, GREY, GREY, ORANGE},
                    scrollPane
                )
            );
            buttonPanel.add(
                makeRow(
                    new String[]{ "Rand", "Avo", "𝑐", "sin", "cos", "tan", "sinh", "cosh", "tanh"},
                    NW,
                    new Color[]{DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY},
                    scrollPane
                )
            );
            buttonPanel.add(
                makeRow(
                    new String[]{ "Rad", "Per", "%", "sin⁻¹", "cos⁻¹", "tan⁻¹", "sinh⁻¹", "cosh⁻¹", "tanh⁻¹"},
                    NW,
                    new Color[]{DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY, DARK_GREY},
                    scrollPane
                )
            );


            // RENDER
            mainPanel.add(scrollPane);
            mainPanel.add(buttonPanel);

            frame.add(mainPanel);
            frame.setVisible(true);

        });
    }


    private static JPanel makeRow(String[] labels, int width, Color[] colors, JScrollPane scrollPane) {

        int[] widths = new int[labels.length];
        Arrays.fill(widths, width);

        return makeRow(labels, widths, colors, scrollPane);
    }

    private static JPanel makeRow(String[] labels, int[] widths, Color[] colors, JScrollPane scrollPane) {

        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        for (int i = 0; i < labels.length; i++) {
            JButton button = new JButton(labels[i]);
            button.addActionListener(new CalculatorButtonAction(scrollPane));
            button.setBorder(new LineBorder(VERY_DARK_GREY, 1));
            button.setContentAreaFilled(false);
            button.setBackground(colors[i]);
            button.setForeground(WHITE);
            button.setOpaque(true);
            button.setMaximumSize(new Dimension(widths[i], Integer.MAX_VALUE));
            Font font = button.getFont();
            button.setFont(new Font(font.getName(), Font.BOLD, 18));
//            button.setSize(new Dimension(widths[i], Integer.MAX_VALUE));
            rowPanel.add(button);

            buttons.put(labels[i], button);
//            button.addComponentListener(new ComponentAdapter() {
//                @Override
//                public void componentResized(ComponentEvent e) {
//                    System.out.println(button.getSize());
//                }
//            });
        }

        return rowPanel;
    }


    public static void keyPressed(KeyEvent e) {

        if (e.getID() == KeyEvent.KEY_RELEASED) {
            String keyChar = String.valueOf(e.getKeyChar());

            keyChar = aliases.getOrDefault(keyChar, keyChar);

            JButton button = buttons.get(keyChar);
            if (button != null)
                button.doClick();



            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                buttons.get("=").doClick();
            }
        }
    }
}
