package client.main.MultiplayerGame;

import client.Client;
import client.RunClient;

import directions.Directions;
import objects.Food;
import objects.Snake;
import player.Player;
import sqlconnection.SqlAccess;

import java.awt.*;
import java.io.IOException;

public class MultiplayerGame implements Runnable{

    int i=0;
    public static Snake snake;
    public static Snake enemySnake;
    public static Food food;
    private  Player player;
    private SqlAccess sqlAccess;
    public static MultiplayerGameWindow multiplayerGameWindow;
    public static MultiplayerGamePanel multiplayerGamePanel;
    public static Thread gameThread;


    public MultiplayerGame(Player player) throws IOException {
        this.player = player;
        sqlAccess = new SqlAccess();

        multiplayerGamePanel = new MultiplayerGamePanel();
        multiplayerGameWindow = new MultiplayerGameWindow(multiplayerGamePanel);
        multiplayerGamePanel.requestFocus();

        String initMap = RunClient.client.getInputStream();
        String[] Package = initMap.split(":");
        String snakeId = Package[1];

        int xFood = Integer.parseInt(Package[2]), yFood = Integer.parseInt(Package[3]);

        if(snakeId.equals("1")){
            //player snake
            snake = new Snake(0, 0, Directions.getRight(), 1, Color.CYAN);
            player.setSnake(snake);
            //enemy snake
            enemySnake = new Snake(920,520,Directions.getLeft(),1,Color.GREEN);
        } else {
            //enemy snake
            enemySnake = new Snake(0, 0, Directions.getRight(), 1, Color.CYAN);
            //player snake
            snake = new Snake(920,520,Directions.getLeft(),1,Color.GREEN);
            player.setSnake(snake);
        }


        //food
        food = new Food(xFood, yFood);

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void updatePanel() throws IOException {
        //Send player movement to enemy
        String nameKey = "move:";
        if(snake.getDirection() == Directions.getLeft()) {
            nameKey += "left";
        } else if (snake.getDirection() == Directions.getRight()) {
            nameKey += "right";
        }else if(snake.getDirection()== Directions.getUp() ) {
            nameKey += "up";
        }else if(snake.getDirection() == Directions.getDown()) {
            nameKey += "down";
        }
        RunClient.client.flushOutputStream(nameKey);

        // Update enemy movement
        String[] enemyMovement = RunClient.client.getInputStream().split(":");
        String move = enemyMovement[1];
        int xFood = Integer.parseInt(enemyMovement[2]), yFood = Integer.parseInt(enemyMovement[3]);
        if(move.equals("up")){
            multiplayerGamePanel.updateEnemySnakeDirection(Directions.getUp());
        } else if(move.equals("down")){
            multiplayerGamePanel.updateEnemySnakeDirection(Directions.getDown());
        } else if(move.equals("right")){
            multiplayerGamePanel.updateEnemySnakeDirection(Directions.getRight());
        } else if(move.equals("left")){
            multiplayerGamePanel.updateEnemySnakeDirection(Directions.getLeft());
        }


        player.getSnake().move(multiplayerGameWindow.getWidth(), multiplayerGameWindow.getHeight());
        player.getSnake().eatFood(food);
        multiplayerGamePanel.updateSnakeDirection();


        enemySnake.move(multiplayerGameWindow.getWidth(), multiplayerGameWindow.getHeight());
        enemySnake.eatFood(food);


        food.setPos(xFood,yFood);
        multiplayerGamePanel.repaint();
    }

    @Override
    public void run() {
        final int FPS = 3;

        // How long each frame lasts in nanoseconds
        double timePerFrame = 1000000000.0 / FPS;
        long lastFrame = System.nanoTime();

        while (!player.getSnake().isMultiplayerOver(enemySnake) && !enemySnake.isMultiplayerOver(snake)) {
            long now = System.nanoTime();
            player.setScore(player.getSnake().getScore());
            // Check and reset last frame
            if (now - lastFrame >= timePerFrame ) {
                try {
                    updatePanel();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lastFrame = System.nanoTime();
            }
        }


        if(player.getSnake().isMultiplayerOver(enemySnake) || enemySnake.isMultiplayerOver(snake)) {
            try {
                RunClient.client.flushOutputStream("end");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.updateHighScore(player.getScore());
            sqlAccess.updatePlayerToDB(player);
            player.setScore(0);
        }
    }
}