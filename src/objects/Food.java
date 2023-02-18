package objects;

import units.UnitSquare;

import java.awt.*;

public class Food extends UnitSquare {

    public Food(){

    }
    public Food(int x, int y) {
        super(x, y);
    }

    public boolean checkPos(Snake s){
        for(int i = 0; i< s.getLength(); ++i){
            if(this.xAxis == s.getBodyUnit(i).getXAxis() && this.yAxis == s.getBodyUnit(i).getYAxis())
                return false;
        }
        return true;
    }

    public void setRandomPos(){
        double randX = Math.random() * 24;
        double randY  = Math.random() * 14;
        this.xAxis = (int) randX*40;
        this.yAxis = (int) randY*40;
    }

    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(xAxis, yAxis, width, width);
    }
}
