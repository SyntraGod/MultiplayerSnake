package ClientTest.main.MultiplayerGame;

import javax.swing.*;

/**
 * Game Window
 */
public class MultiplayerGameWindow {

    private JFrame jframe;
    private final int width = 1000, height = 600;

    public MultiplayerGameWindow(MultiplayerGamePanel multiplayerGamePanel) {
        jframe = new JFrame("Multiplayer Snake");

        jframe.setSize(width, height);

        jframe.setResizable(false);

        // Kill game when press close button
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add images to game screen
        jframe.add(multiplayerGamePanel);

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
