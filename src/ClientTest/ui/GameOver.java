package ClientTest.ui;

import ClientTest.main.SingleplayerGame.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ClientTest.RunClientTest.menu;
import static ClientTest.main.SingleplayerGame.Game.gamePanel;
import static ClientTest.main.SingleplayerGame.Game.gameWindow;
import static java.awt.Color.black;
import static java.awt.Color.red;

public class GameOver {

    public GameOver(int score, Graphics g) {

        Letter totalScore = new Letter(black, "Ink Free", Font.BOLD, 40, "Your total score: " + score, 350, 300);
        Letter gameOver = new Letter(red, "Ink Free", Font.BOLD, 90, "Game Over\n", (gameWindow.getWidth() - 400) / 2 , (gameWindow.getHeight()) / 3);

        totalScore.draw(g);
        gameOver.draw(g);

        JButton backBtn = new JButton("Back To Menu");
        backBtn.setBounds(400, 400, 200, 50);
        backBtn.addActionListener(new Action());

        gamePanel.add(backBtn);

    }

    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Game.gameThread.interrupt();
            gameWindow.dispose();
            menu.setVisible(true);
        }
    }

}
