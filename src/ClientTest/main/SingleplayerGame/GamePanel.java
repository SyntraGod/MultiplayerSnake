package ClientTest.main.SingleplayerGame;

import ClientTest.inputs.KeyboardInputs;
import ClientTest.inputs.MouseInputs;
import ClientTest.ui.GameOver;
import directions.Directions;

import javax.swing.*;
import java.awt.*;

import static ClientTest.main.SingleplayerGame.Game.snake;

/**
 * Content of the game screen
 * Receive client.inputs from gamer
 */
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    private int lastKeyPressed;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);
        addKeyListener(keyboardInputs);
        lastKeyPressed = Directions.getRight();
    }

    public void receiveKeyPressed(int direction) {
        lastKeyPressed = direction;
    }

    public void updateSnakeDirection() {
        Game.snake.changeDirection(lastKeyPressed);
    }

    /**
     * Draw images on panel
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(!Game.snake.isGameOver()) {
            Game.snake.draw(g);
            //Draw food
            Game.food.draw(g);
            //Print score in GameWindow
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + snake.getScore(), 800, 30);
        }
        else {
            //Print string "Game Over" and point
            new GameOver(snake.getScore(), g);
        }
    }
}
