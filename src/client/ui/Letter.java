package client.ui;

import java.awt.*;

public class Letter {
    protected Color color;
    protected String name;
    protected int style;
    protected int size;
    protected String string;
    protected int xAxis;
    protected int yAxis;

    public Letter(Color color, String name, int style, int size, String string, int xAxis, int yAxis) {
        this.color = color;
        this.name = name;
        this.style = style;
        this.size = size;
        this.string = string;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public void draw (Graphics g) {
        g.setColor(color);
        Font font = new Font(name, style, size);
        g.setFont(font);
        g.drawString(string, xAxis, yAxis);
    }
}
