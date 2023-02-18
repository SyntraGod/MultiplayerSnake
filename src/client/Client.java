package client;

import player.Player;

import java.io.*;
import java.net.*;

public class Client {
    private static Player player;
    private static BufferedWriter os;
    private  static BufferedReader is;
    private Socket socketOfClient;
    private int idClient;
    public Client(){
        final String serverHost = "localhost";
        os = null;
        is = null;
        socketOfClient = null;

        try {
            // Gửi yêu cầu kết nối tới Server đang lắng nghe
            // trên máy 'localhost' cổng 9999.
            socketOfClient = new Socket(serverHost, 9999);

            // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));

            // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost);
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
            return;
        }
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setIdClient(int idClient){
        this.idClient = idClient;
    }

    public int getIdClient(){
        return this.idClient;
    }

    public Player getPlayer(){
        return this.player;
    }

    public String getInputStream() throws IOException {
        return is.readLine();
    }

    public void flushOutputStream(String output) throws IOException {
        os.write(output);
        os.newLine();
        os.flush();
    }
}