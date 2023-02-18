package objects;

import directions.Directions;
import units.UnitSquare;

import java.awt.*;

import static java.awt.event.KeyEvent.*;

public class BodyUnit extends UnitSquare {
    private int index;
    private int curDirection;
    private int lastDirection;

    public BodyUnit() {

    }

    public BodyUnit(int x, int y, int index, int curDirection) {
        super(x, y);
        this.index = index;
        this.curDirection = curDirection;
        this.lastDirection = curDirection;
    }

    /**
     * initialize direction of body unit
     */

    public void setIndex(int index){
        this.index = index;
    }
    public void setCurDirection(int curDirection) {
        this.curDirection = curDirection;
    }

    public void setLastDirection(int lastDirection) {
        this.lastDirection = lastDirection;
    }

    /**
     * change direction when user press key
     */
    public void changeDirection(int direction) {
        if (Directions.getAxis(this.curDirection).equals(Directions.getAxis(direction)))
            return;
        this.curDirection = direction;
    }

    /**
     * change position when change direction
     */
    public void changePositionForDirection() {
        int right = Directions.getRight();
        int left = Directions.getLeft();
        int up = Directions.getUp();
        int down = Directions.getDown();

        if (lastDirection == right || lastDirection == left) {
            if (curDirection == down) {
                yAxis += 40;
            } else {
                yAxis -= 40;
            }
        } else if (lastDirection == down || lastDirection == up) {
            if (curDirection == right) {
                xAxis += 40;
            } else {
                xAxis -= 40;
            }
        }
    }

    public int getCurDirection() {
        return curDirection;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public void copy(BodyUnit part) {
        this.curDirection = part.getCurDirection();
        this.lastDirection = part.getLastDirection();
        this.xAxis = part.getXAxis();
        this.yAxis = part.getYAxis();
    }

    public BodyUnit deepClone() {
        BodyUnit clone = new BodyUnit();

        clone.setPos(xAxis, yAxis);
        clone.setCurDirection(curDirection);
        clone.setLastDirection(lastDirection);

        return clone;
    }

    public void draw(Graphics g) {
        g.fillRect(xAxis, yAxis, width, width);
    }

    public void move(int velocity) {
        if (lastDirection != curDirection) {
            changePositionForDirection();
            lastDirection = curDirection;
            return;
        }

        try {
            int xDelta = 0,yDelta =0;
            if(curDirection == Directions.getUp()){
                xDelta = 0 * velocity;
                yDelta = -40 * velocity;
            } else if( curDirection == Directions.getDown()){
                xDelta = 0 * velocity;
                yDelta = 40 * velocity;
            } else if( curDirection == Directions.getLeft()){
                xDelta = -40 * velocity;
                yDelta = 0 * velocity;
            } else if( curDirection == Directions.getRight()){
                xDelta = 40 * velocity;
                yDelta = 0 * velocity;
            }
            setPos(xAxis + xDelta, yAxis + yDelta);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
