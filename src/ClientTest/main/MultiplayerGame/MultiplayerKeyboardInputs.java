package ClientTest.main.MultiplayerGame;

import directions.Directions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MultiplayerKeyboardInputs implements KeyListener {

    private MultiplayerGamePanel multiplayerGamePanel;

    public MultiplayerKeyboardInputs(MultiplayerGamePanel multiplayerGamePanel) {
        this.multiplayerGamePanel = multiplayerGamePanel;
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
        multiplayerGamePanel.receiveKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
