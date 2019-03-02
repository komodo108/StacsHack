package network;

import game.IPlayer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Server extends Observable implements Runnable, Observer {

    private BufferedWriter write;

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(2727);
            Socket s = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
