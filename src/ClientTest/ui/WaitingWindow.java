package ClientTest.ui;

import javax.swing.*;

public class WaitingWindow {
    private final int width = 500;
    private final int height = 300;
    private JFrame frame;
    private JLabel label;

    public  WaitingWindow(){
        this.frame = new JFrame();
        this.label = new JLabel("Waiting for another player!");

        frame = new JFrame("Waiting for another player");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.setResizable(false);
        frame.add(label);
    }

    public void displayWaitingWindow(){
        this.frame.setVisible(true);
    }

    public void dispose(){
        this.frame.dispose();
    }
}
