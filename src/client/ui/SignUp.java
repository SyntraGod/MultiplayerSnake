package client.ui;

import player.Player;
import sqlconnection.SqlAccess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp {
    private final int width = 480;
    private final int height = 300;
    JFrame frame;
    JButton jButton;
    JPanel newPanel;
    JLabel userLabel, passLabel,confirmLabel;
    JTextField usernameField;
    JPasswordField passwordField,confirmPasswordField;
    JCheckBox checkBox1;
    public SignUp(){
        this.frame = new JFrame();
        this.newPanel = new JPanel();
        newPanel.setLayout(null);

        frame = new JFrame("Sign Up");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(newPanel);
        frame.setVisible(false);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //Username
        userLabel = new JLabel();
        userLabel.setText("Username: ");
        userLabel.setBounds(30,20,120,20);
        usernameField = new JTextField(30);
        usernameField.setBounds(160,20,150,20);

        //Password
        passLabel = new JLabel();
        passLabel.setText("Password: ");
        passLabel.setBounds(30,50,120,20);
        passwordField = new JPasswordField(30);
        passwordField.setBounds(160,50,150,20);

        //Checkbox1
        checkBox1 = new JCheckBox("Show password");
        checkBox1.setBounds(320,50,200,20);
        checkBox1.addActionListener(ae-> {
            JCheckBox c = (JCheckBox) ae.getSource();
            passwordField.setEchoChar(c.isSelected()? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
            confirmPasswordField.setEchoChar(c.isSelected()? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        });
        newPanel.add(checkBox1);

        //Confirm password
        confirmLabel = new JLabel();
        confirmLabel.setText("Confirm Password: ");
        confirmLabel.setBounds(30,80,120,20);
        confirmPasswordField = new JPasswordField(30);
        confirmPasswordField.setBounds(160,80,150,20);

        //submit button
        this.jButton = new JButton("Sign Up");
        jButton.setBounds(190,120,100,20);

        //Create new Panel to put form element
        newPanel.add(userLabel);
        newPanel.add(usernameField);
        newPanel.add(passLabel);
        newPanel.add(passwordField);
        newPanel.add(confirmLabel);
        newPanel.add(confirmPasswordField);
        newPanel.add(jButton);

        jButton.addActionListener(new client.ui.SignUp.Action());
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
                String confirmPassword = confirmPasswordField.getText(); //get confirm password
                if(password == null) {
                    JOptionPane.showMessageDialog(frame, "Please enter password!");
                } else if(!password.equals(confirmPassword)){
                    JOptionPane.showMessageDialog(frame, "Your password not match! Please try again!");
                } else {
                    Player player = new Player(userName, password);
                    if(sqlAccess.insertPlayerToDB(player)) {
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame, "Successfully! Please log in!");
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Username already exists or invalid username");
                    }
                }
            }
        }
    }
}