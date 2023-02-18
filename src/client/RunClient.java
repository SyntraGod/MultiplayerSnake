package client;

import client.ui.*;

/**
 * RunClient class, gather everything needed
 */
public class RunClient {
    public static MainMenu menu = new MainMenu();
    public static SignInOption signInOption = new SignInOption();

    public static Client client = new Client();

    public static void main(String args[]) {
        signInOption.setVisible(true);
    }

}
