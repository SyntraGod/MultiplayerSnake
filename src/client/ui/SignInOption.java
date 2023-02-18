package client.ui;

import client.Client;
import client.RunClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SignInOption {
    private final int width = 1000;
    private final int height = 600;
    private static JFrame frame;
    private JPanel panel;
    private JButton signIn;
    private JButton signUp;
    private JButton exit;

    public SignInOption() {
            this.frame = new JFrame();
            this.panel = new JPanel();

            panel.setLayout(null);

            frame = new JFrame("Multiplayer Snake");
            frame.setSize(width, height);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.setVisible(false);

            this.signIn = new JButton("Sign in");
            this.signUp = new JButton("Sign up");
            this.exit = new JButton("Exit");

            signIn.setBounds(420, 100, 160, 50);
            signIn.addActionListener(new client.ui.SignInOption.Action());
            signUp.setBounds(420, 200, 160, 50);
            signUp.addActionListener(new client.ui.SignInOption.Action());
            exit.setBounds(420, 300, 160, 50);
            exit.addActionListener(new client.ui.SignInOption.Action());


            panel.add(signIn);
            panel.add(signUp);
            panel.add(exit);

        }

        public static void setVisible(boolean visibility) {
            frame.setVisible(visibility);
        }

        class Action implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == signIn) {
                    SignIn form = new SignIn();
                    form.setVisible(true);
                } else if(e.getSource() == signUp){
                    SignUp signUpForm = new SignUp();
                    signUpForm.setVisible(true);
                }
                else if (e.getSource() == exit) {
                    int dialogButton = JOptionPane.showConfirmDialog (frame, "Are you sure?","WARNING",JOptionPane.YES_NO_OPTION);
                    if(dialogButton == JOptionPane.YES_OPTION) {
                        try {
                            RunClient.client.flushOutputStream("quit");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.exit(0);
                    }else {

                    }
                }
            }
        }

}
