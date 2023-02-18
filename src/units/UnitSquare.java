package units;

import java.awt.*;

public class UnitSquare {
    protected static final int width = 40;
    protected int xAxis;
    protected int yAxis;

    public UnitSquare(){

    }
    public UnitSquare(int x, int y) {
        xAxis = x;
        yAxis = y;
    }

    public void setPos(int x, int y) {
        xAxis = x;
        yAxis = y;
    }

    public int getXAxis() {
        return xAxis;
    }

    public int getYAxis() {
        return yAxis;
    }

    public static int getWidth() {
        return width;
    }

    public void draw(Graphics g) {
        g.fillRect(xAxis, yAxis, width, width);
    }
}
