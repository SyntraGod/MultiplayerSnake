package ClientTest;

import ClientTest.ui.*;

/**
 * RunClient class, gather everything needed
 */
public class RunClientTest {
    public static MainMenu menu = new MainMenu();
    public static SignInOption signInOption = new SignInOption();
    public static ClientTest client = new ClientTest();

    public static void main(String args[]) {
        signInOption.setVisible(true);
    }

}
