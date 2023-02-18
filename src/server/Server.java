package server;

import directions.Directions;
import objects.Food;
import objects.Snake;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server{
    private static int numberOfClient;
    public static List<ServiceThread> serviceThreads;
    //player's list of server

    public static Food food;
    static int numberPlayer;

    static List<Snake> snakes;

    private void Init(){
        ServerSocket listener = null;
        serviceThreads = new ArrayList<>();
        snakes = new ArrayList<>();
        food = new Food();
        numberPlayer=0;
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;


        // Mở một ServerSocket tại cổng 7777.
        // Chú ý bạn không thể chọn cổng nhỏ hơn 1023 nếu không là người dùng
        // đặc quyền (privileged users (root)).
        try {
            listener = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (numberOfClient < 2) {
                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.

                Socket socketOfServer = listener.accept();
                ServiceThread serviceThread = new ServiceThread(socketOfServer, clientNumber++);
                serviceThread.start();
                serviceThreads.add(serviceThread);
                numberOfClient = serviceThreads.size();
                System.out.println("There are " + numberOfClient + " clients");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * server send init map,snake to player
     */
    public void sendInitMap() throws IOException {


        //add 2 snake to server
        snakes.add(new Snake(0, 0, Directions.getRight(), 1, Color.CYAN));
        snakes.add(new Snake(920,520,Directions.getLeft(),1,Color.GREEN));

        //set random food
        for(int i =0; i<snakes.size();++i) {
            while (!food.checkPos(snakes.get(i)))
                food.setRandomPos();
        }

        //Send map and first food to 2 players
        String mapPlayer1 ,mapPlayer2;
        mapPlayer1 = "initMap:1:"+food.getXAxis()+":"+food.getYAxis();
        mapPlayer2 = "initMap:2:"+food.getXAxis()+":"+food.getYAxis();
        serviceThreads.get(0).flushOutputStream(mapPlayer1);
        serviceThreads.get(1).flushOutputStream(mapPlayer2);

    }

    public static Server server = new Server();
    public static void main(String args[]) throws IOException {
        server.Init();
    }

}
