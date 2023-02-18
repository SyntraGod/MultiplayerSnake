package client.main;
import javax.swing.*;

public class GameNotification{
    private JFrame jFrame;
    private final int WIDTH = 200, HEIGHT = 100;
    public GameNotification(){
        jFrame = new JFrame();

        jFrame.setSize(WIDTH,HEIGHT);

        jFrame.setResizable(false);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.setVisible(true);

        jFrame.setLocationRelativeTo(null);
    }

    public JFrame getJframe() {return jFrame;}

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
