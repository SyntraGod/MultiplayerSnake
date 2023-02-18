package client.main.SingleplayerGame;

import javax.swing.*;

/**
 * Game Window
 */
public class GameWindow {

    private JFrame jframe;
    private final int width = 1000, height = 600;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame("Multiplayer Snake");

        jframe.setSize(width, height);

        jframe.setResizable(false);

        // Kill game when press close button
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add images to game screen
        jframe.add(gamePanel);

        // Visualise game screen
        jframe.setVisible(true);

        // Put screen to center when get turned on
        jframe.setLocationRelativeTo(null);

    }

    public void dispose() {
        jframe.dispose();
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
