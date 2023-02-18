package client.ui;

import client.RunClient;
import client.main.SingleplayerGame.Game;
import client.main.MultiplayerGame.MultiplayerGame;
import sqlconnection.SqlAccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static java.awt.AWTEventMulticaster.remove;
import static client.RunClient.*;


public class MainMenu {
    private final int width = 1000;
    private final int height = 600;
    private JFrame frame;
    private final JPanel panel;
    private final JButton singlePlayer;
    private final JButton multiplayer;
    private final JButton highScore;
    private final JButton exit;

    private final JButton signOut;
    private SqlAccess sqlAccess;

    public MainMenu() {
        this.frame = new JFrame();
        this.panel = new JPanel();
        frame.getContentPane();
        panel.setLayout(null);

        frame = new JFrame("Multiplayer Snake");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(false);

        //Sign out
        signOut = new JButton("Sign Out");
        signOut.setBounds(850,20,100,30);
        signOut.addActionListener(new Action());
        panel.add(signOut);

        //single player
        this.singlePlayer = new JButton("Single Player");
        singlePlayer.setBounds(425, 120, 150, 50);
        singlePlayer.addActionListener(new Action());
        panel.add(singlePlayer);

        //multiplayer
        this.multiplayer = new JButton("Multi Player");
        multiplayer.setBounds(425, 200, 150, 50);
        multiplayer.addActionListener(new Action());
        panel.add(multiplayer);

        //high score
        this.highScore = new JButton("High Score");
        highScore.setBounds(425, 280, 150, 50);
        highScore.addActionListener(new Action());
        panel.add(highScore);

        //exit
        this.exit = new JButton("Exit");
        exit.setBounds(425, 360, 150, 50);
        exit.addActionListener(new Action());
        panel.add(exit);

    }

    public void setVisible(boolean visibility) {
        frame.setVisible(visibility);
    }

    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == signOut){
                int dialogButton = JOptionPane.showConfirmDialog (frame, "Are you sure?","WARNING",JOptionPane.YES_NO_OPTION);
                if(dialogButton == JOptionPane.YES_OPTION) {
                    sqlAccess = new SqlAccess();
                    client.getPlayer().setStatus(0); //offline
                    sqlAccess.updatePlayerToDB(client.getPlayer());
                    frame.dispose();
                    signInOption.setVisible(true);
                }else {

                }
            }else if (e.getSource() == singlePlayer) {
                frame.setVisible(false);
                new Game(client.getPlayer());
            }
            else if(e.getSource() == highScore) {
                HighScore highScoreTable = new HighScore();
                highScoreTable.setVisible(true);
            }
            else if (e.getSource() == exit) {
                int dialogButton = JOptionPane.showConfirmDialog (frame, "Are you sure?","WARNING",JOptionPane.YES_NO_OPTION);
                if(dialogButton == JOptionPane.YES_OPTION) {
                    sqlAccess = new SqlAccess();
                    client.getPlayer().setStatus(0); //offline
                    sqlAccess.updatePlayerToDB(client.getPlayer());
                    try {
                        RunClient.client.flushOutputStream("quit");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(0);
                }else {

                }
            } else if(e.getSource() == multiplayer){
                //send package ready to multiplayer game
                String readyMultiplayerPackage = "readyGame:" + client.getPlayer().getIdPlayer();
                try {
                    RunClient.client.flushOutputStream(readyMultiplayerPackage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                WaitingWindow waitingWindow = new WaitingWindow();
                while(true) {
                    try {
                        String response = client.getInputStream();
                        if(response.equals("wait")) {
                            waitingWindow.displayWaitingWindow();
                        } else if(response.equals("noPlayer")){
                            JOptionPane.showMessageDialog(frame, "No player found!");
                            waitingWindow.dispose();
                            break;
                        }
                        else {
                            frame.setVisible(false);
                            waitingWindow.dispose();
                            new MultiplayerGame(client.getPlayer());
                            break;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
