package ClientTest.ui;

import javax.swing.*;

public class Notification {

    public Notification(String name, String message) {
        JFrame frame = new JFrame(name);
        frame.setVisible(true);
        frame.setSize(300, 300);

        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel messageLabel = new JLabel(message);

        frame.add(panel);
        panel.add(messageLabel);
    }

}
