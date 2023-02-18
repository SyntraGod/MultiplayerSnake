package objects;

import directions.Directions;
import units.UnitSquare;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    Color color;
    private List<BodyUnit> body = new ArrayList<>();
    private int length = 2;
    private int direction;
    private int velocity;
    private int score = 0;

    public Snake(int x, int y, int direction, int velocity, Color color) {
        if(direction == Directions.getRight()) {
            for (int i = 0; i < length; i++) {
                body.add(new BodyUnit(x + (length - i - 1) * 40, y, i, Directions.getRight()));
            }
        } else if(direction == Directions.getLeft()){
            for (int i = 0; i < length; i++) {
                body.add(new BodyUnit(x + i * 40, y, i,Directions.getLeft()));
            }
        }
        this.color = color;
        this.direction = direction;
        this.velocity = velocity;
    }

    public BodyUnit head() {
        return body.get(0);
    }

    public int getDirection() {
        return direction;
    }

    public BodyUnit getBodyUnit(int index){
        return body.get(index);
    }

    public List<BodyUnit> getBody() {
        return body;
    }

    public int getLength() {
        return length;
    }

    public int getXAxis() {
        return head().getXAxis();
    }

    public int getYAxis() {
        return head().getYAxis();
    }
    public int getScore() {
        return score;
    }



    /**
     * snake changes direction when key pressed
     * @param direction
     */
    public void changeDirection(int direction) {
            if (Directions.getAxis(this.direction).equals(Directions.getAxis(direction)))
                return;
            this.direction = direction;
            this.head().changeDirection(direction);
    }


    /**
     * snake moving
     */
    public void move(int windowWidth, int windowHeight) {
        BodyUnit clone = head().deepClone();
        BodyUnit nextClone = new BodyUnit();
        head().move(velocity);
        for (int i = 1 ; i < length; i++) {
            nextClone.copy(body.get(i));
            body.get(i).copy(clone);
            clone.copy(nextClone);
        }

        resetPos(windowWidth, windowHeight);
    }

    /**
     * reset body part position if it's out of bound
     * @param windowWidth game window width
     * @param windowHeight game window height
     */
    public void resetPos(int windowWidth, int windowHeight) {
        BodyUnit bodyPart = head();
        int curX = bodyPart.getXAxis();
        int curY = bodyPart.getYAxis();
        int unitWidth = UnitSquare.getWidth();

        if (curX >= windowWidth) bodyPart.setPos(0, curY);
        if (curX < 0) bodyPart.setPos(windowWidth + curX, curY);
        if (curY + unitWidth >= windowHeight) bodyPart.setPos(curX, 0);
        if (curY < 0) bodyPart.setPos(curX, windowHeight + curY - unitWidth);
    }

    /**
     *
     * @param g
     */
    public void draw(Graphics g) {
        for (int i = 0; i < length; i++) {
            if(i==0) {
                g.setColor(Color.GRAY);
            }
            else {
                g.setColor(color);
            }
            body.get(i).draw(g);
        }
    }

    /**
     * the length of snake add 1 when snake eat a food
     */
    public void updateSnake(){
        BodyUnit newBody = new BodyUnit();
        newBody.copy(this.getBodyUnit(length-1));
        newBody.setIndex(length);
        this.length++;
        body.add(newBody);
        score += UnitSquare.getWidth();
    }

    public void eatFood(Food food) {
        //snake eat a food
        if(this.getXAxis() == food.getXAxis() && this.getYAxis()== food.getYAxis()){
            updateSnake();
        }
        else return;
    }

    /**
     * game over
     */
    public boolean isGameOver(){
        for(int i = 1; i<length;++i) {
            if (this.getXAxis() == body.get(i).getXAxis() && this.getYAxis() == body.get(i).getYAxis()) {
                return true;
            }
        }
            return false;
    }

    public boolean isMultiplayerOver(Snake snake){
        for(int i = 1; i<length;++i) {
            if (this.getXAxis() == body.get(i).getXAxis() && this.getYAxis() == body.get(i).getYAxis()) {
                return true;
            }
        }
        for(int i = 1; i < snake.getLength(); ++i){
            if (this.getXAxis() == snake.getBodyUnit(i).getXAxis() && this.getYAxis() == snake.getBodyUnit(i).getYAxis()) {
                return true;
            }
        }
        return false;
    }
}
