package ClientTest.main.SingleplayerGame;

import directions.Directions;
import objects.Food;
import objects.Snake;
import player.Player;
import sqlconnection.SqlAccess;

import java.awt.*;


/**
 * Create
 */
public class Game implements Runnable {

    public static GameWindow gameWindow;
    public static GamePanel gamePanel;
    public static Thread gameThread;
    public Player player;
    private SqlAccess sqlAccess;
    public static Snake snake;
    public static Food food;

    public Game(Player player) {
        this.player = player;
        sqlAccess = new SqlAccess();
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        snake = new Snake(0, 0, Directions.getRight(), 1, Color.CYAN);
        player.setSnake(snake);
        food = new Food();

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void updatePanel() {
        player.getSnake().move(gameWindow.getWidth(), gameWindow.getHeight());
        player.getSnake().eatFood(food);
        gamePanel.updateSnakeDirection();
        while(!food.checkPos(player.getSnake())){
            food.setRandomPos();
        }
        gamePanel.repaint();
    }

    @Override
    public void run() {

        final int FPS = 5;

        // How long each frame lasts in nanoseconds
        double timePerFrame = 1000000000.0 / FPS;
        long lastFrame = System.nanoTime();

        while(!food.checkPos(player.getSnake())){
            food.setRandomPos();
        }

        while (!player.getSnake().isGameOver()) {
            long now = System.nanoTime();
            player.setScore(player.getSnake().getScore());
            // Check and reset last frame
            if (now - lastFrame >= timePerFrame) {
                updatePanel();
                lastFrame = System.nanoTime();
            }
        }

        if(player.getSnake().isGameOver()) {
            player.updateHighScore(player.getScore());
            sqlAccess.updatePlayerToDB(player);
            player.setScore(0);
        }

    }
}
