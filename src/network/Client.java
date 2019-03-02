package network;

import game.IPlayer;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class Client extends Observable implements Observer, Runnable {

    private Socket s;
    private IPlayer icebreaker, helper;

    public Client(String ip) throws UnknownHostException {

    }

    @Override
    public void run() {

    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
