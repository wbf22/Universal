package brandon.universal.util;

import java.awt.*;

public class ColorUtil {

    public static Color hex(String hex) {
        hex = hex.replace("#", "");

        // Convert hex to RGB
        int rgb = Integer.parseInt(hex, 16);

        // Create a Color object
        return new Color(rgb);
    }
}
