package units;

public class UnitLine {
    protected int width = 1;
    protected int length = 40;
    protected int xAxis;
    protected int yAxis;

    public UnitLine() {

    }

    public UnitLine(int x, int y) {
        xAxis = x;
        yAxis = y;
    }

    public void setPos(int x, int y) {
        xAxis = x;
        yAxis = y;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getXAxis() {
        return xAxis;
    }

    public int getYAxis() {
        return yAxis;
    }
}
