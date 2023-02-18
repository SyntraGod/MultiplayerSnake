package client.main.MultiplayerGame;

import client.ui.Letter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.black;
import static java.awt.Color.red;
import static client.RunClient.menu;
import static client.main.MultiplayerGame.MultiplayerGame.multiplayerGamePanel;
import static client.main.MultiplayerGame.MultiplayerGame.multiplayerGameWindow;

public class MultiplayerGameOver {
    public MultiplayerGameOver(int score,String result, Graphics g) {
        Letter totalScore = new Letter(black, "Ink Free", Font.BOLD, 40, "Your total score: " + score, 350, 300);
        Letter gameOver = new Letter(red, "Ink Free", Font.BOLD, 90, result+"\n", (multiplayerGameWindow.getWidth() - 400) / 2 , (multiplayerGameWindow.getHeight()) / 3);

        totalScore.draw(g);
        gameOver.draw(g);

        JButton backBtn = new JButton("Back To Menu");
        backBtn.setBounds(400, 400, 200, 50);
        backBtn.addActionListener(new Action());

        multiplayerGamePanel.add(backBtn);
    }

    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MultiplayerGame.gameThread.interrupt();
            multiplayerGameWindow.dispose();
            menu.setVisible(true);
        }
    }

}
