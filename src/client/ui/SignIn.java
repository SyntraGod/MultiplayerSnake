package client.ui;

import client.RunClient;
import player.Player;
import sqlconnection.SqlAccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static client.RunClient.menu;
import static client.RunClient.signInOption;

public class SignIn {
    private final int width = 450;
    private final int height = 200;
    JFrame frame;
    JButton jButton;
    JPanel newPanel;
    JLabel userLabel, passLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JCheckBox checkBox;
    public SignIn(){
        this.frame = new JFrame();
        this.newPanel = new JPanel();
        newPanel.setLayout(null);

        frame = new JFrame("Sign In");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(newPanel);
        frame.setVisible(false);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                SignInOption.setVisible(true);
            }
        });

        //Username
        userLabel = new JLabel();
        userLabel.setText("Username: ");
        userLabel.setBounds(30,20,100,20);
        usernameField = new JTextField(30);
        usernameField.setBounds(120,20,150,20);

        //Password
        passLabel = new JLabel();
        passLabel.setText("Password: ");
        passLabel.setBounds(30,50,100,20);
        passwordField = new JPasswordField(30);
        passwordField.setBounds(120,50,150,20);


        //Checkbox
        checkBox = new JCheckBox("Show password");
        checkBox.setBounds(290,50,200,20);
        checkBox.addActionListener(ae-> {
            JCheckBox c = (JCheckBox) ae.getSource();
            passwordField.setEchoChar(c.isSelected()? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        });
        newPanel.add(checkBox);

        //submit button
        this.jButton = new JButton("Sign In");
        jButton.setBounds(150,80,100,20);

        //Create new Panel to put form element
        newPanel.add(userLabel);
        newPanel.add(usernameField);
        newPanel.add(passLabel);
        newPanel.add(passwordField);
        newPanel.add(jButton);

        jButton.addActionListener(new client.ui.SignIn.Action());
    }

    public void setVisible(boolean visibility) {
        frame.setVisible(visibility);
    }

    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == jButton){
                SqlAccess sqlAccess  = new SqlAccess();
                String userName = usernameField.getText(); //Get user
                String password  = passwordField.getText(); //get password
                try {
                    //Get player by username
                    Player player = sqlAccess.getPlayerByUsername(userName);
                    if (player.getPassword().equals(password)) {
                        if(player.getStatus() == 0) //Not online
                        {
                            signInOption.setVisible(false);
                            RunClient.client.setPlayer(player);
                            menu.setVisible(true);
                            player.setStatus(1); //online
                            sqlAccess.updatePlayerToDB(player);
                            //send sign in package to server
                            String signInpackage = "signin:" + player.getIdPlayer();
                            RunClient.client.flushOutputStream(signInpackage);
                            //response from server
                            String response = RunClient.client.getInputStream();
                            RunClient.client.setIdClient(Integer.parseInt(response));
                            frame.dispose();
                            JOptionPane.showMessageDialog(frame, "Log in successfully!");
                        }
                        else {
                            JOptionPane.showMessageDialog(frame, "Account has been logged in another device");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Wrong password, Please try again!");
                    }
                } catch (Exception noUsernameFound) {
                    JOptionPane.showMessageDialog(frame, "Wrong username, Please try again!");
                    System.out.println(noUsernameFound.toString());
                }
            }
        }
    }
    enum PasswordField {
        SHOW,HIDE;
    }
}