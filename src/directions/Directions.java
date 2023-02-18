package directions;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;

public class Directions {
    private static final int up = VK_W;
    private static final  int down = VK_S ;
    private static final int left = VK_A;
    private static final int right = VK_D;

    public static int getUp() {
        return VK_W;
    }

    public static int getDown() {
        return VK_S;
    }

    public static int getLeft() {
        return VK_A;
    }

    public static int getRight() {
        return VK_D;
    }

    public static String getAxis(int direction) {
        if (direction == up || direction == down)
            return "vertical";

        if (direction == left || direction == right)
            return "horizontal";
        return "-1";
    }

}
