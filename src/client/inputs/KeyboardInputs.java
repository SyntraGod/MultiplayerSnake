package client.inputs;

import client.main.SingleplayerGame.GamePanel;
import directions.Directions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key != Directions.getLeft() && key != Directions.getDown() &&
                key != Directions.getRight() && key != Directions.getUp())
            return;
        gamePanel.receiveKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
