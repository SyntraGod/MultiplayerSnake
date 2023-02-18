package server;

import directions.Directions;
import player.Player;
import sqlconnection.SqlAccess;

import java.io.*;
import java.net.Socket;

import static server.Server.*;

public class ServiceThread extends Thread {

    private int clientNumber;
    private Socket socketOfServer;
    private BufferedReader is;
    private BufferedWriter os;
    private static void log(String message) {
        System.out.println(message);
    }

    public ServiceThread(Socket socketOfServer, int clientNumber) throws IOException {
        this.clientNumber = clientNumber;
        this.socketOfServer = socketOfServer;

        is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
        os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
        // Log
        log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
    }

    public String getInputStream() throws IOException {
        return is.readLine();
    }

    public void flushOutputStream(String output) throws IOException {
        os.write(output);
        os.newLine();
        os.flush();
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public String getHeader(String s){
        String[] header = s.split(":");
        return header[0];
    }

    public String getContent(String s){
        String[] header = s.split(":");
        return header[1];
    }

    public void closeThread() throws IOException {
        is.close();
        os.close();
        socketOfServer.close();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = null;

                //get package from client
                line = getInputStream();
                //System.out.println(line);

                //Get header of package
                String header = getHeader(line);

                switch (header) {
                    //Check if package is signin
                    case "signin":
                        flushOutputStream(String.valueOf(clientNumber));

                        break;
                    // ready to multiplayer game package
                    case "readyGame":
                        numberPlayer++;
                        String response = null;
                        // players < 2 -> wait for another player
                        if (numberPlayer != 2) {
                            response = "wait";
                            flushOutputStream(response);
                        } else {
                            response = "start";
                            for (int i = 0; i < serviceThreads.size(); ++i) {
                                serviceThreads.get(i).flushOutputStream(response);
                            }
                            Server.server.sendInitMap();
                        }
//                    this.sleep(10000);
//                    //wait for 10 seconds
//                    if (players.size() != 2) {
//                        response = "noPlayer";
//                        flushOutputStream(response);
//                        numberPlayer--;
//                    }
                        break;
                    //Player move in multiplayer game
                    case "move":
                        //update snake move to server
                        String movement = line.split(":")[1];
                        if(movement.equals("up")){
                            snakes.get(clientNumber).changeDirection(Directions.getUp());
                        } else if(movement.equals("down")){
                            snakes.get(clientNumber).changeDirection(Directions.getDown());
                        } else if(movement.equals("right")){
                            snakes.get(clientNumber).changeDirection(Directions.getRight());
                        } else if(movement.equals("left")){
                            snakes.get(clientNumber).changeDirection(Directions.getLeft());
                        }
                        snakes.get(clientNumber).move(1000,600);
                        snakes.get(clientNumber).eatFood(food);

                       //if snake eat food, set random position for food
                        for (objects.Snake snake : snakes) {
                            while (!food.checkPos(snake)) {
                                food.setRandomPos();
                            }
                        }

                        line = line + ":" + food.getXAxis()+":"+food.getYAxis();
                        for (int i = 0; i < serviceThreads.size(); ++i) {
                            if (i != clientNumber) {
                                serviceThreads.get(i).flushOutputStream(line);
                            }
                        }
                        break;
                    case "end":
                        numberPlayer--;
                        snakes.remove(snakes.get(clientNumber));
                        break;
                    case "quit":
                        closeThread();
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}