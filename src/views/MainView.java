package views;

import game.render.Render;
import listener.KeyListen;
import network.Client;
import network.Server;

import javax.swing.*;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MainView implements Observer {

    private JFrame frame;
    private Render render;
    private KeyListen listen;

    private int width = 700;
    private int height = 700;

    public MainView(boolean isClient, String ip) throws UnknownHostException {
        render = new Render();
        render.setBounds(0, 0, width, height);

        frame = new JFrame("Cool Boiz");
        frame.setSize(width, height);
        frame.setResizable(false);

        frame.add(render);

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        if(isClient) {
            Client client = new Client(ip);
            client.addObserver(this);
            new Thread(client).start();
        } else {
            Server server = new Server();
            server.addObserver(this);
            new Thread(server).start();
        }

        frame.addKeyListener(listen);
        frame.repaint();
    }

    @Override
    public void update(Observable observable, Object o) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                render.paintComponents(render.getGraphics());
                render.repaint();
            }
        });
    }
}
